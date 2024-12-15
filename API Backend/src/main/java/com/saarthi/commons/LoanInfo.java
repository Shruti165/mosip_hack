package com.saarthi.commons;

import java.util.List;

import com.saarthi.lender.utils.LoanStatus;
import com.saarthi.lender.utils.LoanType;

import lombok.Data;

@Data
public class LoanInfo {
	private Double rate;
	private LoanType loanType;
	private String applicationType;
	private LoanStatus loanStatus;
	private List<Document> cropTxnDoc;
}
