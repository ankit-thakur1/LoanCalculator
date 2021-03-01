package com.natwest.loancalculator.mortgageplan.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.natwest.loancalculator.mortgageplan.bean.LoanInput;
import com.natwest.loancalculator.mortgageplan.bean.LoanOutput;

import io.micrometer.core.instrument.util.StringUtils;

@Service
public class MortgagePlanService {

	@Autowired
	RestTemplate restTemplate;

	/**
	 * Validates the input provided by customer based on the mortgage plan and once
	 * validated calculates and returns the mortgage plan.
	 * 
	 * @param loanInput the input values
	 * @return the mortgage plan
	 */
	public LoanOutput validateAndGetMortgagePlan(LoanInput loanInput) {
		String validationMessage;
		BigDecimal fee = null;
		BigDecimal rate = null;

		if ("first-time-buyer".equalsIgnoreCase(loanInput.getMortgageType())) {
			fee = BigDecimal.valueOf(499.00);
			rate = BigDecimal.valueOf(2.5);
			validationMessage = validateFirstTimeBuyer(loanInput);
		} else if ("re-mortgage".equalsIgnoreCase(loanInput.getMortgageType())) {
			fee = BigDecimal.valueOf(0.00);
			rate = BigDecimal.valueOf(2.3);
			validationMessage = validateReMortgage(loanInput);
		} else if ("buy-to-let".equalsIgnoreCase(loanInput.getMortgageType())) {
			fee = BigDecimal.valueOf(2999.00);
			rate = BigDecimal.valueOf(2.8);
			validationMessage = validateBuyToLet(loanInput);
		} else {
			validationMessage = "Invalid Mortgage Type. We currently support buy-to-let/first-time-buyer/re-mortgage.";
		}
		if (StringUtils.isNotBlank(validationMessage)) {
			return new LoanOutput(null, null, null, null, validationMessage);
		}
		LoanOutput loanOutput = new LoanOutput();
		loanOutput.setFee(fee);
		return getMortgagePlan(rate, loanInput, loanOutput);
	}

	/**
	 * Validates first-time-buyer mortgages.
	 * 
	 * @param loanInput the input values
	 * @return errorMessage if any
	 */
	public String validateFirstTimeBuyer(LoanInput loanInput) {
		String validationMessage = "";
		List<Integer> validRepaymentPeriod = new ArrayList<>(Arrays.asList(10, 15, 20, 25, 30));
		if (loanInput.getRepaymentTimeInYears() == null) {
			validationMessage += "Please provide a repayment period in years.";
		} else if (!validRepaymentPeriod.contains(loanInput.getRepaymentTimeInYears())) {
			validationMessage += "The repayment period must be either 10,15,20,25 or 30 years.";
		}
		BigDecimal depositPercentage = calculateDepositPercentage(loanInput.getPropertyValue(), loanInput.getDeposit());
		if (depositPercentage.compareTo(BigDecimal.valueOf(15.00)) == -1) {
			validationMessage += "The deposit has to be 15% of the property value.";
		}
		return validationMessage;
	}

	/**
	 * Validates re-mortgages.
	 * 
	 * @param loanInput the input values
	 * @return errorMessage if any
	 */
	public String validateReMortgage(LoanInput loanInput) {
		String validationMessage = "";
		if (loanInput.getRepaymentTimeInYears() == null) {
			validationMessage += "Please provide a repayment period in years.";
		} else if (loanInput.getRepaymentTimeInYears() < 1 || loanInput.getRepaymentTimeInYears() > 30) {
			validationMessage += "The repayment period must be between 1 and 30 years.";
		}
		return validationMessage;
	}

	/**
	 * Validates buy-to-let mortgages.
	 * 
	 * @param loanInput the input values
	 * @return errorMessage if any
	 */
	public String validateBuyToLet(LoanInput loanInput) {
		String validationMessage = "";
		BigDecimal depositPercentage = calculateDepositPercentage(loanInput.getPropertyValue(), loanInput.getDeposit());
		if (depositPercentage.compareTo(BigDecimal.valueOf(50.00)) == -1) {
			validationMessage = "The deposit has to be 50% of the property value.";
		}
		return validationMessage;
	}

	/**
	 * Determines and return the mortgage plan based on input parameters.
	 * 
	 * @param rate       the rate of interest
	 * @param loanInput  the input values
	 * @param loanOutput the output values
	 * @return the mortgage plan
	 */
	public LoanOutput getMortgagePlan(BigDecimal rate, LoanInput loanInput, LoanOutput loanOutput) {
		String baseUri = "http://loan-terms-calculator-service";

		if ("buy-to-let".equalsIgnoreCase(loanInput.getMortgageType())) {
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(baseUri + "/getTotalInterest")
					.queryParam("amount", loanInput.getLoanAmount()).queryParam("rate", rate).build();
			BigDecimal totalInterest = restTemplate
					.exchange(builder.toUriString(), HttpMethod.GET, null, BigDecimal.class).getBody();

			loanOutput.setTotalInterest(totalInterest);
			loanOutput.setRepaymentPerMonth(null);
		} else {

			UriComponents builder = UriComponentsBuilder.fromHttpUrl(baseUri + "/getInterestPerMonth")
					.queryParam("amount", loanInput.getLoanAmount()).queryParam("rate", rate)
					.queryParam("noOfMonths", loanInput.getRepaymentTimeInMonths()).build();
			BigDecimal interestPerMonth = restTemplate
					.exchange(builder.toUriString(), HttpMethod.GET, null, BigDecimal.class).getBody();

			builder = UriComponentsBuilder.fromHttpUrl(baseUri + "/getRepaymentPerMonth")
					.queryParam("amount", loanInput.getLoanAmount())
					.queryParam("noOfMonths", loanInput.getRepaymentTimeInMonths()).build();
			BigDecimal repaymentPerMonth = restTemplate
					.exchange(builder.toUriString(), HttpMethod.GET, null, BigDecimal.class).getBody();

			loanOutput.setInterestPerMonth(interestPerMonth);
			loanOutput.setRepaymentPerMonth(repaymentPerMonth);
		}
		return loanOutput;
	}

	/**
	 * Calculates the deposit percentage based on property value and deposit amount
	 * 
	 * @param propertyValue
	 * @param deposit
	 * @return the deposit percentage
	 */
	private BigDecimal calculateDepositPercentage(BigDecimal propertyValue, BigDecimal deposit) {
		BigDecimal percentage = (deposit.divide(propertyValue)).multiply(BigDecimal.valueOf(100));
		return percentage.setScale(2, RoundingMode.HALF_EVEN);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
