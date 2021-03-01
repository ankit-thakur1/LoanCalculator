package com.natwest.loancalculator.loanterms.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.natwest.loancalculator.loanterms.service.LoanTermsCalculatorService;

/**
 * Controller class for loan terms calculation microservice
 *
 */
@RestController
public class LoanTermsCalculatorController {

	@Autowired
	LoanTermsCalculatorService loanTermsService;

	/**
	 * Calculates the total interest given the amount and rate of interest.
	 * 
	 * @param amount the loan amount
	 * @param rate the interest rate
	 * @return the total interest
	 */
	@GetMapping(path = "/getTotalInterest")
	public BigDecimal getInterestPerMonth(@RequestParam(name = "amount") BigDecimal amount,
			@RequestParam(name = "rate") BigDecimal rate) {
		return loanTermsService.getTotalInterest(amount, rate);
	}

	/**
	 * Calculates the interest per month given the amount, rate of interest and re-payment period.
	 * 
	 * @param amount the loan amount
	 * @param rate the interest rate
	 * @param noOfMonths the re-payment period
	 * @return the interest amount to be paid per month
	 */
	@GetMapping(path = "/getInterestPerMonth")
	public BigDecimal getInterestPerMonth(@RequestParam(name = "amount") BigDecimal amount,
			@RequestParam(name = "rate") BigDecimal rate, @RequestParam(name = "noOfMonths") Integer noOfMonths) {
		return loanTermsService.getInterestPerMonth(amount, rate, noOfMonths);
	}

	/**
	 * Calculates the re-payment amount per month given the amount and re-payment period.
	 * 
	 * @param amount the loan amount
	 * @param noOfMonths the re-payment period
	 * @return the re-payment amount to be paid per month
	 */
	@GetMapping(path = "/getRepaymentPerMonth")
	public BigDecimal getRepaymentPerMonth(@RequestParam(name = "amount") BigDecimal amount,
			@RequestParam(name = "noOfMonths") Integer noOfMonths) {
		return loanTermsService.getRepaymentPerMonth(amount, noOfMonths);
	}
}