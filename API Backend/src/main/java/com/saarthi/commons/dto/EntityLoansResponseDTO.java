package com.saarthi.commons.dto;

import com.saarthi.commons.FarmerInfo;
import com.saarthi.commons.LoanInfo;
import com.saarthi.commons.WarehouseInfo;

import lombok.Data;

@Data
public class EntityLoansResponseDTO {
	private LoanInfo loan;
	private FarmerInfo farmer;
	private WarehouseInfo warehouse;
}
