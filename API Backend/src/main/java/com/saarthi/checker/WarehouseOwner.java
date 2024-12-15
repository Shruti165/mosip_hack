package com.saarthi.checker;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saarthi.commons.Document;
import com.saarthi.utils.ListToJsonConverter;

import lombok.Data;

@Entity
@Data
public class WarehouseOwner {
	@Id 
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String name;
	private String credibilityScore;
	private String gstNumber;
	@Column(columnDefinition = "json")
	@Convert(converter = ListToJsonConverter.class)
	private List<Document> docs;
	@Column(unique = true)
	private String mobileNo;
	@Column(unique = true)
	private String emailId;
	@JsonIgnore
	private String password;
}
