package com.saarthi.lender;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import com.saarthi.commons.DigitalContract;
import com.saarthi.commons.Document;
import com.saarthi.lender.utils.LoanStatus;
import com.saarthi.lender.utils.LoanType;
import com.saarthi.utils.ListToJsonConverter;

import lombok.Data;

@Entity
@Data
public class Loan {
	@Id 
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String farmerId;
	private String warehouseId;
	private String reasonForLoan;
	private String installments;
	private String additionalDetails;
	@Column(columnDefinition = "json")
	@Convert(converter = ListToJsonConverter.class)
	private List<DigitalContract> docs;
	private int principal;
	private int tenure;
	private Date createdDate;
	private Date updatedDate;
	private LoanStatus status;
	private LoanType type;
	@ManyToOne
	@JoinColumn(name="bankId", referencedColumnName = "bankId")
	private Bank bank;
}
