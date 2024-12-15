package com.saarthi.lender.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

import com.saarthi.lender.Loan;

public interface LoanDAO extends CrudRepository<Loan, String>{
	// TBD pagination not implemented
	@Query(value = "select * from loan where warehouse_id in (select id from warehouse where owner_id = ?1) order by created_date desc limit ?2 offset ?3", nativeQuery = true)
	List<Loan>findByWarehouseOwnerId(String warehouseOwnerId, int limit, int offset);
	
	List<Loan>findByBankBankIdOrderByCreatedDateDesc(String bankId);
	
	@Query(value = "select * from loan where bank_id = ?1 order by created_date desc limit ?2 offset ?3", nativeQuery = true)
	List<Loan>findByBankId(String bankId, int limit, int offset);
	
	@Query(value = "select * from loan where farmer_id = ?1 order by created_date desc limit ?2 offset ?3", nativeQuery = true)
	List<Loan>findByFarmerIdOrderByCreatedDateDesc(String bankId, int limit, int offset);
}
