package com.saarthi.lendee.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.saarthi.lendee.Crop;

import lombok.Data;
@Data
public class CropListAlias{
	private List<Crop>crops;
	public CropListAlias(List<Object>crops) {
		Gson gson = new Gson();
		this.crops = new ArrayList<>();
		for(Object obj: crops) {
			Crop crop = gson.fromJson(gson.toJson(obj),Crop.class);
			this.crops.add(crop);
		}
	}
}
