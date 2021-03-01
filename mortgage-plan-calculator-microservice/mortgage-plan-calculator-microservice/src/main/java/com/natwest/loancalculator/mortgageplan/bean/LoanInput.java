package com.natwest.loancalculator.mortgageplan.bean;

import java.math.BigDecimal;

/**
 * POJO to hold all input parameters
 *
 */
public class LoanInput {

	private String mortgageType;
	private BigDecimal deposit;
	private BigDecimal propertyValue;
	private BigDecimal loanAmount;
	private Integer repaymentTimeInMonths;
	private Integer repaymentTimeInYears;

	public LoanInput(String mortgageType, BigDecimal deposit, BigDecimal propertyValue, BigDecimal loanAmount,
			Integer repaymentTimeInYears) {
		super();
		this.mortgageType = mortgageType;
		this.deposit = deposit;
		this.propertyValue = propertyValue;
		this.loanAmount = loanAmount;
		if (repaymentTimeInYears != null) {
			this.repaymentTimeInMonths = repaymentTimeInYears * 12;
		}
		this.repaymentTimeInYears = repaymentTimeInYears;
	}

	public String getMortgageType() {
		return mortgageType;
	}

	public void setMortgageType(String mortgageType) {
		this.mortgageType = mortgageType;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public BigDecimal getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(BigDecimal propertyValue) {
		this.propertyValue = propertyValue;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Integer getRepaymentTimeInMonths() {
		return repaymentTimeInMonths;
	}

	public void setRepaymentTimeInMonths(Integer repaymentTimeInMonths) {
		this.repaymentTimeInMonths = repaymentTimeInMonths;
	}

	public Integer getRepaymentTimeInYears() {
		return repaymentTimeInYears;
	}

	public void setRepaymentTimeInYears(Integer repaymentTimeInYears) {
		this.repaymentTimeInYears = repaymentTimeInYears;
	}

}
