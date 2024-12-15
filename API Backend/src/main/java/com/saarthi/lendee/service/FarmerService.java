package com.saarthi.lendee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.saarthi.checker.Warehouse;
import com.saarthi.checker.service.WarehouseService;
import com.saarthi.commons.WarehouseSummary;
import com.saarthi.lendee.Crop;
import com.saarthi.lendee.Farmer;
import com.saarthi.lendee.dao.FarmerDAO;

@Service
public class FarmerService {
	@Autowired
	private FarmerDAO farmerDao;
	
	@Autowired
	private WarehouseService warehouseService;

	public List<Farmer> getAllFarmers() {
		List<Farmer> farmers = new ArrayList<>();
		farmerDao.findAll().forEach(farmers::add);
		return farmers;
	}

	public Farmer getFarmerByEmailId(String id) {
		return farmerDao.findOneByEmailId(id);
	}

	public Farmer getFarmerByMobileNo(String number) {
		return farmerDao.findOneByMobileNo(number);
	}
	
	public Crop getCropByWarehouse(String farmerId, String warehouseId) {
		System.out.println("pehle");
		List<Crop> crops = farmerDao.findCropByFarmerId(farmerId).getCrops();
		System.out.println("farmerId id: " + farmerId + ", warehouseId: " + warehouseId);
		System.out.println(new Gson().toJson(crops));
		if (crops == null)
			return null;
		// TBD can this be done via mysql, it says of an implicit queryable mechanism
		// for json
		for (Crop crop : crops) {
			if (crop.getWarehouseId().equals(warehouseId)) {
				return crop;
			}
		}
		return null;
	}

	public List<Crop> getCropByFarmerId(String id) {
		return farmerDao.findCropByFarmerId(id).getCrops();
	}

	public List<WarehouseSummary> getFarmerWarehouses(String id) {
		List<WarehouseSummary> warehouses = new ArrayList<>();
		List<Crop> crops = getCropByFarmerId(id);
		//for (Crop crop : crops) {
		crops.parallelStream().forEach((crop)-> {
			WarehouseSummary warehouseSummary = new WarehouseSummary();
			Warehouse warehouse = warehouseService.getWarehouse(crop.getWarehouseId());
			warehouseSummary.setId(crop.getWarehouseId());
			warehouseSummary.setName(warehouse.getName());
			warehouses.add(warehouseSummary);
		});
		//}
		return warehouses;
	}
	
	public Farmer getFarmer(String id) {
		return farmerDao.findOne(id);
	}

	public void addFarmer(Farmer farmer) {
		farmerDao.save(farmer);
	}

	public void updateFarmer(String id, Farmer farmer) {
		farmerDao.save(farmer);
	}

	public void deleteFarmer(String id) {
		farmerDao.delete(id);
	}

}
