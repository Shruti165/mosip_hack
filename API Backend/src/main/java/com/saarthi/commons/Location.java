package com.saarthi.commons;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class Location {
	private String street;
	private String city;
}
