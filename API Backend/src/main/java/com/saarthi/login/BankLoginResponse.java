package com.saarthi.login;

import com.saarthi.lender.utils.BankName;

import lombok.Data;

@Data
public class BankLoginResponse {
	private String id;
	private String ifsc;
	private BankName name;
}
