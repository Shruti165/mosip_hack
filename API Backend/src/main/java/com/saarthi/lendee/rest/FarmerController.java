package com.saarthi.lendee.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.saarthi.lendee.Crop;
import com.saarthi.lendee.Farmer;
import com.saarthi.lendee.service.FarmerService;

@RestController
@RequestMapping("/lendee")
public class FarmerController {
	@Autowired
    private FarmerService farmerService;

    @RequestMapping("/farmers")
    public List<Farmer> getAllFarmers() {
        return farmerService.getAllFarmers();
    }

    @RequestMapping("/farmer/{id}")
    public Farmer getFarmer(@PathVariable String id) {
        return farmerService.getFarmer(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/farmer")
    public void addFarmer(@RequestBody Farmer farmer) {
    	farmerService.addFarmer(farmer);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/farmer/{id}")
    public void updateFarmer(@RequestBody Farmer farmer,@PathVariable String id) {
    	farmerService.updateFarmer(id, farmer);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/farmer/{id}")
    public void deleteFarmer(@PathVariable String id) {
    	farmerService.deleteFarmer(id);
    }
    
    @RequestMapping("/farmer/{id}/crops")
    public List<Crop> getAllCropsByFarmer(@PathVariable String id) {
        return farmerService.getCropByFarmerId(id);
    }

}
