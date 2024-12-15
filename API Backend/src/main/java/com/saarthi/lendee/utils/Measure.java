package com.saarthi.lendee.utils;

public enum Measure {
	KG(1), QUINTAL(100), TON(1000);

	private int value;
	Measure(int i) {
		value = i;
	}
	public int getValue() {
		return value;
	}
}
