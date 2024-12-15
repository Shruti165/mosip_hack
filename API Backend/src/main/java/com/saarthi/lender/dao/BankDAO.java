package com.saarthi.lender.dao;

import org.springframework.data.repository.CrudRepository;

import com.saarthi.lender.Bank;

public interface BankDAO extends CrudRepository<Bank, String>{

	Bank findOneByEmailId(String id);

}