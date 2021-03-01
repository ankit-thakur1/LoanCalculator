package com.natwest.loancalculator.loanterms.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

/**
 * Service class for loan terms calculation microservice
 *
 */
@Service
public class LoanTermsCalculatorService {

	/**
	 * Calculates the total interest given the amount and rate of interest.
	 * 
	 * @param amount the loan amount
	 * @param rate   the interest rate
	 * @return the total interest
	 */
	public BigDecimal getTotalInterest(BigDecimal amount, BigDecimal rate) {
		BigDecimal totalInterest = amount.multiply(rate).divide(BigDecimal.valueOf(100));
		return totalInterest.setScale(2, RoundingMode.HALF_EVEN);
	}

	/**
	 * Calculates the interest per month given the amount, rate of interest and
	 * re-payment period.
	 * 
	 * @param amount     the loan amount
	 * @param rate       the interest rate
	 * @param noOfMonths the re-payment period
	 * @return the interest amount to be paid per month
	 */
	public BigDecimal getInterestPerMonth(BigDecimal amount, BigDecimal rate, Integer noOfMonths) {
		BigDecimal totalInterest = (amount.multiply(rate)).divide(BigDecimal.valueOf(100));
		return totalInterest.divide(BigDecimal.valueOf(noOfMonths), 2, RoundingMode.HALF_EVEN);
	}

	/**
	 * Calculates the re-payment amount per month given the amount and re-payment
	 * period.
	 * 
	 * @param amount     the loan amount
	 * @param noOfMonths the re-payment period
	 * @return the re-payment amount to be paid per month
	 */
	public BigDecimal getRepaymentPerMonth(BigDecimal amount, Integer noOfMonths) {
		return amount.divide(BigDecimal.valueOf(noOfMonths), 2, RoundingMode.HALF_EVEN);
	}
}