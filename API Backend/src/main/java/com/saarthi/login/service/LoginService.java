package com.saarthi.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.saarthi.checker.WarehouseOwner;
import com.saarthi.checker.service.WarehouseService;
import com.saarthi.commons.AppConstants;
import com.saarthi.lendee.Farmer;
import com.saarthi.lendee.service.FarmerService;
import com.saarthi.lender.Bank;
import com.saarthi.lender.service.BankService;
import com.saarthi.lender.service.LoanService;
import com.saarthi.login.BankLoginResponse;
import com.saarthi.login.FarmerLoginResponse;
import com.saarthi.login.LoginRequest;
import com.saarthi.login.LoginType;
import com.saarthi.login.WarehouseOwnerLoginResponse;

@Service
public class LoginService {
	@Autowired
	FarmerService farmerService;
	
	@Autowired
	BankService bankService;
	
	@Autowired
	WarehouseService warehouseOwnerService;
	
	public FarmerLoginResponse farmerLogin(LoginRequest request) throws Exception{
		String loginType = request.getLoginType();
		FarmerLoginResponse response = null;
		// TBD unoptimized implementation of enum
		if(LoginType.EMAIL.name().equals(loginType)) {
			Farmer farmer = farmerService.getFarmerByEmailId(request.getUserId());
			System.out.println("-----------Database null testing-----------");
			System.out.println("Farmer: "+new Gson().toJson(farmer));
			response = getFarmerResponse(farmer);
		} else if(LoginType.MOBILE.name().equals(loginType)) {
			Farmer farmer = farmerService.getFarmerByMobileNo(request.getUserId());
			response = getFarmerResponse(farmer);
		} else {
			throw new Exception(loginType);
		}
		return response;
	}

	private FarmerLoginResponse getFarmerResponse(Farmer farmer) throws Exception {
		FarmerLoginResponse response = new FarmerLoginResponse();
		if(farmer!=null && farmer.getId()!=null) {
			response.setId(farmer.getId());
			response.setName(farmer.getName());
			response.setUpiId(farmer.getUpiId());
		}else {
			throw new Exception(AppConstants.INVALID_USERID);
		}
		return response;
	}

	public WarehouseOwnerLoginResponse warehouseOwnerLogin(LoginRequest request) throws Exception {
		String loginType = request.getLoginType();
		WarehouseOwnerLoginResponse response = null;
		if(LoginType.EMAIL.name().equals(loginType)) {
			WarehouseOwner warehouseOwner = warehouseOwnerService.getOwnerByEmailId(request.getUserId());
			response = getWarehouseOwnerResponse(warehouseOwner);
		} else if(LoginType.MOBILE.name().equals(loginType)) {
			WarehouseOwner warehouseOwner = warehouseOwnerService.getOwnerByMobileNo(request.getUserId());
			response = getWarehouseOwnerResponse(warehouseOwner);
		} else {
			throw new Exception(loginType);
		}
		return response;
	}

	private WarehouseOwnerLoginResponse getWarehouseOwnerResponse(WarehouseOwner warehouseOwner) throws Exception {
		WarehouseOwnerLoginResponse response = new WarehouseOwnerLoginResponse();
		if(warehouseOwner!=null && warehouseOwner.getId()!=null) {
			response.setId(warehouseOwner.getId());
			response.setName(warehouseOwner.getName());
		}else {
			throw new Exception(AppConstants.INVALID_USERID);
		}
		return response;
	}

	public BankLoginResponse bankLogin(LoginRequest request) throws Exception {
		BankLoginResponse response = null;
		if(request.getUserId().matches(AppConstants.EMAIL_REGEX)) {
			Bank bank = bankService.getBankByEmailId(request.getUserId());
			response = getBankResponse(bank);
		} else {
			throw new Exception(AppConstants.INVALID_USERID);
		}
		return response;
	}

	private BankLoginResponse getBankResponse(Bank bank) throws Exception {
		BankLoginResponse response = new BankLoginResponse();
		if(bank!=null && bank.getBankId()!=null) {
			response.setId(bank.getBankId());
			response.setName(bank.getName());
			response.setIfsc(bank.getIfscCode());
		}else {
			throw new Exception(AppConstants.INVALID_USERID);
		}
		return response;
	}
	
}
