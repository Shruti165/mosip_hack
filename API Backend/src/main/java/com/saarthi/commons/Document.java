package com.saarthi.commons;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
public class Document {
	private String imageUrl;
	private DocType docType;
}
