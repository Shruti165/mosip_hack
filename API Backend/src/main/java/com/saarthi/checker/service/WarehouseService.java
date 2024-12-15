package com.saarthi.checker.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saarthi.checker.Warehouse;
import com.saarthi.checker.WarehouseOwner;
import com.saarthi.checker.dao.WarehouseDAO;
import com.saarthi.checker.dao.WarehouseOwnerDAO;

@Service
public class WarehouseService {
	@Autowired
	private WarehouseDAO warehouseDAO;
	
	@Autowired
	private WarehouseOwnerDAO warehouseOwnerDAO;
	
	public List<Warehouse> getAllWarehouses() {
        List<Warehouse> warehouses = new ArrayList<>();
        warehouseDAO.findAll().forEach(warehouses::add);
        return warehouses;
    }
	
	public void addWarehouse(Warehouse warehouse) {
    	warehouseDAO.save(warehouse);
    }
	
	public void addWarehouseOwner(WarehouseOwner warehouseOwner) {
    	warehouseOwnerDAO.save(warehouseOwner);
    }
	
	public List<WarehouseOwner> getAllWarehouseOwners() {
        List<WarehouseOwner> warehouseOwners = new ArrayList<>();
        warehouseOwnerDAO.findAll().forEach(warehouseOwners::add);
        return warehouseOwners;
    }
	
	public Warehouse getWarehouse(String id) {
        return warehouseDAO.findOne(id);
    }
	
	public WarehouseOwner getWarehouseOwnerByWarehouseId(String id) {
    	return warehouseDAO.findWarehouseOwnerByWarehouseId(id);
    }

	public WarehouseOwner getOwnerByEmailId(String id) {
		return warehouseOwnerDAO.findOneByEmailId(id);
	}
	
	public WarehouseOwner getOwnerByMobileNo(String number) {
		return warehouseOwnerDAO.findOneByMobileNo(number);
	}
}
