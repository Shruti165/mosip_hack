package com.saarthi.commons;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
public class DigitalContract {
	@Id 
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String farmerId;
	private String storageOwnerId;
	private String cropId;
	@Embedded
	private Document doc;
	private ContractStatus status;
	@Embedded
	@AttributeOverrides({@AttributeOverride(name="storageOwnerSign", column = @Column)})
	public Signature storageOwnerSign;
	@Embedded
	public Signature farmerSign;

}
