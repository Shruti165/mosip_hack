package com.saarthi.checker.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.saarthi.checker.Warehouse;
import com.saarthi.checker.WarehouseOwner;
import com.saarthi.checker.service.WarehouseService;
import com.saarthi.lendee.Farmer;
import com.saarthi.lendee.service.FarmerService;

@RestController
@RequestMapping("/checker")
public class WarehouseController {
	@Autowired
    private WarehouseService warehouseService;

    @RequestMapping("/warehouses")
    public List<Warehouse> getAllWarehouses() {
        return warehouseService.getAllWarehouses();
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/warehouse")
    public void addWarehouse(@RequestBody Warehouse warehouse) {
    	warehouseService.addWarehouse(warehouse);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/warehouseOwner")
    public void addWarehouseOwner(@RequestBody WarehouseOwner warehouseOwner) {
    	warehouseService.addWarehouseOwner(warehouseOwner);
    }
    
    @RequestMapping("/warehouseOwners")
    public List<WarehouseOwner> getAllWarehouseOwners() {
        return warehouseService.getAllWarehouseOwners();
    }
}
