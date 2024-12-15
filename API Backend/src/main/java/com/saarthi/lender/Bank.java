package com.saarthi.lender;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.saarthi.commons.License;
import com.saarthi.commons.Location;
import com.saarthi.lender.utils.BankName;
import com.saarthi.utils.ListToJsonConverter;

import lombok.Data;

@Entity
@Data
public class Bank {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String bankId;
	private String ifscCode;
	private BankName name;
	private double interestRate;
	@Column(unique = true)
	private String emailId;
	@JsonIgnore
	private String password;
	@Embedded
	private Location location;
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(columnDefinition = "json")
	@Convert(converter = ListToJsonConverter.class)
	private List<Employee> manager;
	@JsonProperty(access = Access.WRITE_ONLY)
	@Embedded
	private License license;
	@JsonIgnore
	@OneToMany(mappedBy="bank", cascade=CascadeType.ALL)
	private List<Loan>loans;
}
