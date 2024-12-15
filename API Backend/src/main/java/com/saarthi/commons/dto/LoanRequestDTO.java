package com.saarthi.commons.dto;

import java.util.List;

import com.saarthi.commons.Document;
import com.saarthi.lender.Bank;
import lombok.Data;

@Data
public class LoanRequestDTO {
	private int amount;
	private int timeframe;
	private String warehouseId;
	private String reasonForLoan;
	private String installments;
	private String additionalDetails;
	private List<Document> docs;
	private String bankId;
	private String farmerId;
	private String cropId;
}
