package com.saarthi.lendee;

import com.saarthi.lendee.utils.CropQuality;
import com.saarthi.lendee.utils.Measure;

import lombok.Data;

@Data
public class Crop {
	private String warehouseId;
	private int quantity;
	private Measure measure;
	private CropQuality quality;
}
