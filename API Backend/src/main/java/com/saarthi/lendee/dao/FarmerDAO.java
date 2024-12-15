package com.saarthi.lendee.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.saarthi.lendee.Farmer;
import com.saarthi.lendee.utils.CropListAlias;

public interface FarmerDAO extends CrudRepository<Farmer, String>{
	@Query("select new com.saarthi.lendee.utils.CropListAlias(crops) from Farmer where id = ?1")
	CropListAlias findCropByFarmerId(String farmerId);
	
	Farmer findOneByEmailId(String emailId);

	Farmer findOneByMobileNo(String mobileNo);
}
