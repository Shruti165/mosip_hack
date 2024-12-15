package com.saarthi.commons;

import lombok.Data;

@Data
public class WarehouseInfo {
	private String name;
	private Location location;
	private int packets;
	private String cropQuality;
}
