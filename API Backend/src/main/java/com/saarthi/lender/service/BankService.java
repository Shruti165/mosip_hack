package com.saarthi.lender.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saarthi.lender.Bank;
import com.saarthi.lender.dao.BankDAO;

@Service
public class BankService {
	@Autowired
	BankDAO bankDao;
	
	public void addBank(Bank bank) {
    	bankDao.save(bank);
    }
	
	public List<Bank> getAllBanks() {
        List<Bank> banks = new ArrayList<>();
        bankDao.findAll().forEach(banks::add);
        return banks;
    }
	
	public Bank getBank(String id) {
        return bankDao.findOne(id);
    }

	public Bank getBankByEmailId(String id) {
		return bankDao.findOneByEmailId(id);
	}

}
