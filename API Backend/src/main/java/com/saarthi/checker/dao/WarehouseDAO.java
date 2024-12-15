package com.saarthi.checker.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.saarthi.checker.Warehouse;
import com.saarthi.checker.WarehouseOwner;

public interface WarehouseDAO extends CrudRepository<Warehouse, String>{
	@Query("select owner from Warehouse where id = ?1")
	WarehouseOwner findWarehouseOwnerByWarehouseId(String id);
}
