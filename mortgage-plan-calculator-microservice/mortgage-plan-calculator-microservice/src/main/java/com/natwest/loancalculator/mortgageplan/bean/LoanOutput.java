package com.natwest.loancalculator.mortgageplan.bean;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * POJO to hold all output parameters
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanOutput {

	private BigDecimal interestPerMonth;
	private BigDecimal totalInterest;
	private BigDecimal repaymentPerMonth;
	private BigDecimal fee;
	private String message;

	public LoanOutput() {
		super();
	}

	public LoanOutput(BigDecimal interestPerMonth, BigDecimal totalInterest, BigDecimal repaymentPerMonth,
			BigDecimal fee, String message) {
		super();
		this.interestPerMonth = interestPerMonth;
		this.totalInterest = totalInterest;
		this.repaymentPerMonth = repaymentPerMonth;
		this.fee = fee;
		this.message = message;
	}

	public BigDecimal getInterestPerMonth() {
		return interestPerMonth;
	}

	public void setInterestPerMonth(BigDecimal interestPerMonth) {
		this.interestPerMonth = interestPerMonth;
	}

	public BigDecimal getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(BigDecimal totalInterest) {
		this.totalInterest = totalInterest;
	}

	public BigDecimal getRepaymentPerMonth() {
		return repaymentPerMonth;
	}

	public void setRepaymentPerMonth(BigDecimal repaymentPerMonth) {
		this.repaymentPerMonth = repaymentPerMonth;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
