package com.saarthi.commons;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class License {
	private String licenseId;
	private String renewalDate;
}
