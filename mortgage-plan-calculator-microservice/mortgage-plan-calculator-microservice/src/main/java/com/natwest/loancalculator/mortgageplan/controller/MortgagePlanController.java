package com.natwest.loancalculator.mortgageplan.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.natwest.loancalculator.mortgageplan.bean.LoanInput;
import com.natwest.loancalculator.mortgageplan.bean.LoanOutput;
import com.natwest.loancalculator.mortgageplan.service.MortgagePlanService;

@RestController
public class MortgagePlanController {

	@Autowired
	MortgagePlanService mortgagePlanService;

	/**
	 * Given the mortgage type and input values this method returns the mortgage plan for the customer
	 * 
	 * @param mortgageType the mortgage type
	 * @param deposit the total deposit
	 * @param propertyValue the value of the property
	 * @param loanAmount the loan amount
	 * @param repaymentTimeInYears the re-payment period
	 * @return the mortgage plan
	 */
	@GetMapping(path = "/getMortgagePlan")
	public LoanOutput getLoanTerms(@RequestParam(name = "mortgageType") String mortgageType,
			@RequestParam(name = "deposit") BigDecimal deposit,
			@RequestParam(name = "propertyValue") BigDecimal propertyValue,
			@RequestParam(name = "loanAmount") BigDecimal loanAmount,
			@RequestParam(name = "repaymentTimeInYears", required = false) Integer repaymentTimeInYears) {
		LoanInput loanInput = new LoanInput(mortgageType, deposit, propertyValue, loanAmount, repaymentTimeInYears);
		return mortgagePlanService.validateAndGetMortgagePlan(loanInput);
	}
}