package com.saarthi.checker.dao;

import org.springframework.data.repository.CrudRepository;

import com.saarthi.checker.WarehouseOwner;

public interface WarehouseOwnerDAO extends CrudRepository<WarehouseOwner, String>{

	WarehouseOwner findOneByEmailId(String id);

	WarehouseOwner findOneByMobileNo(String mobileNo);

}