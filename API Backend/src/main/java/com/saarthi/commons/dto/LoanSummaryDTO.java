package com.saarthi.commons.dto;

import com.saarthi.commons.Location;

import lombok.Data;

@Data
public class LoanSummaryDTO {
	private String farmerName;
	private String farmerMobileNo;
	private String farmerCreditScore;
	private String warehouseName;
	private String warehouseCity;
	private String loanId;
}
