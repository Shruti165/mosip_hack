package com.saarthi.lendee;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saarthi.commons.DigitalContract;
import com.saarthi.commons.Document;
import com.saarthi.commons.Location;
import com.saarthi.commons.Person;
import com.saarthi.utils.ListToJsonConverter;

import lombok.Data;

@Entity
@Data
public class Farmer {
	@Id 
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	@Column(unique = true)
	private String mobileNo;
	private String name;
	private String upiId;
	private String creditScore;
	@Column(unique = true)
	private String emailId;
	@JsonIgnore
	private String password;
	@Embedded
	private Location location;
	@Column(columnDefinition = "json")
	@Convert(converter = ListToJsonConverter.class)
	private List<Document> docs;
	@Column(columnDefinition = "json")
	@Convert(converter = ListToJsonConverter.class)
	private List<DigitalContract> contracts;
	@Column(columnDefinition = "json")
	@Convert(converter = ListToJsonConverter.class)
	private List<Person> family;
	@Column(columnDefinition = "json")
	@Convert(converter = ListToJsonConverter.class)
	private List<Crop> crops;
	@Column(name = "is_active", columnDefinition = "boolean default true")
	private boolean isActive;
}
