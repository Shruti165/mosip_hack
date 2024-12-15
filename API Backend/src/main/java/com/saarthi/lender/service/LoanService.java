package com.saarthi.lender.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.saarthi.checker.Warehouse;
import com.saarthi.checker.WarehouseOwner;
import com.saarthi.checker.service.WarehouseService;
import com.saarthi.commons.ContractStatus;
import com.saarthi.commons.DigitalContract;
import com.saarthi.commons.Document;
import com.saarthi.commons.FarmerInfo;
import com.saarthi.commons.LoanInfo;
import com.saarthi.commons.Signature;
import com.saarthi.commons.WarehouseInfo;
import com.saarthi.commons.dto.EntityLoansResponseDTO;
import com.saarthi.commons.dto.LoanRequestDTO;
import com.saarthi.commons.dto.LoanSummaryDTO;
import com.saarthi.lendee.Crop;
import com.saarthi.lendee.Farmer;
import com.saarthi.lendee.service.FarmerService;
import com.saarthi.lender.Bank;
import com.saarthi.lender.Loan;
import com.saarthi.lender.dao.LoanDAO;
import com.saarthi.lender.utils.LenderConstants;
import com.saarthi.lender.utils.LoanStatus;
import com.saarthi.lender.utils.PreLoanResponse;

@Service
public class LoanService {
	@Autowired
    private LoanDAO loanDao;
	
	@Autowired
	private WarehouseService warehouseService;
	
	@Autowired
	private FarmerService farmerService;
	
	@Autowired
    private BankService bankService;
	
	private ExecutorService pool = Executors.newFixedThreadPool(20);
	
	public PreLoanResponse preApplyLoan(String id) {
		PreLoanResponse response = new PreLoanResponse();
		response.setTimeframes(Arrays.asList(LenderConstants.TIME_FRAMES));
		response.setWarehouses(farmerService.getFarmerWarehouses(id));
		response.setReasonsForLoan(Arrays.asList(LenderConstants.LOAN_REASONS));
		response.setInstallments(Arrays.asList(LenderConstants.INSTALLMENTS));
		response.setBanks(bankService.getAllBanks());
		return response;
	}

    public List<EntityLoansResponseDTO> getAllLoansByWarehouseOwner(String warehouseOwnerId, int limit, int pageNo){
    	// delegating to controller
    	//if(limit==0||pageNo==0) {pageNo=1;limit=5;}
    	int offset = (pageNo-1)*limit;
    	List<Loan>loans = loanDao.findByWarehouseOwnerId(warehouseOwnerId, limit, offset);
    	List<EntityLoansResponseDTO>response = new ArrayList<EntityLoansResponseDTO>();
    	if(loans==null)return response;
    	
    	//for(Loan loan: loans) {
		loans.parallelStream().forEach((loan) -> {
			EntityLoansResponseDTO info = new EntityLoansResponseDTO();
			
			info.setLoan(fetchLoanInfoS(loan));
			info.setFarmer(fetchFarmerInfoS(loan));
			info.setWarehouse(fetchWarehouseInfoS(loan));
			
			/*
			CompletableFuture<LoanInfo> loanInfo = fetchLoanInfo(loan);
			CompletableFuture<FarmerInfo> farmerInfo = fetchFarmerInfo(loan);
			CompletableFuture<WarehouseInfo> warehouseInfo = fetchWarehouseInfo(loan);
			CompletableFuture<Void> all = CompletableFuture.allOf(loanInfo, farmerInfo, warehouseInfo);
			try {
				all.get();
				info.setLoan(loanInfo.get());
				info.setFarmer(farmerInfo.get());
				info.setWarehouse(warehouseInfo.get());
			} catch (InterruptedException | ExecutionException e) {
				System.out.println("Error: Interruptions in executing completable future");
			}
			*/
    		response.add(info);
    	});
    	//}
    	return response;
    }
    
    private WarehouseInfo fetchWarehouseInfoS(Loan loan) {
    	WarehouseInfo warehouseInfo = new WarehouseInfo();
		Warehouse warehouse = warehouseService.getWarehouse(loan.getWarehouseId());
		System.out.println("Warehouse id: " + loan.getWarehouseId());
		System.out.println(new Gson().toJson(warehouse));
		Crop crop = farmerService.getCropByWarehouse(loan.getFarmerId(), loan.getWarehouseId());
		warehouseInfo.setCropQuality(crop == null ? null : crop.getQuality().name());
		System.out.println("crop: " + crop);
		warehouseInfo.setPackets(
				crop == null || crop.getMeasure() == null ? 0 : crop.getQuantity() * crop.getMeasure().getValue());

		warehouseInfo.setLocation(warehouse.getLocation());
		warehouseInfo.setName(warehouse.getName());
		return warehouseInfo;
    }

    private FarmerInfo fetchFarmerInfoS(Loan loan) {
    	FarmerInfo farmerInfo = new FarmerInfo();
		Farmer farmer = farmerService.getFarmer(loan.getFarmerId());
		farmerInfo.setName(farmer.getName());
		farmerInfo.setMobileNo(farmer.getMobileNo());
		farmerInfo.setCreditScore(farmer.getCreditScore());
		return farmerInfo;
    }
    
    private LoanInfo fetchLoanInfoS(Loan loan) {
    	LoanInfo loanInfo = new LoanInfo();
		loanInfo.setRate(loan.getBank().getInterestRate());
		loanInfo.setLoanStatus(loan.getStatus());
		loanInfo.setLoanType(loan.getType());
		loanInfo.setApplicationType(loan.getReasonForLoan());

		List<Document> cropTxnDocs = new ArrayList<>();
		List<DigitalContract> dcDocs = loan.getDocs();
		// TBD only get null dcDocs
		if (dcDocs != null) {
			for (DigitalContract dc : dcDocs)
				cropTxnDocs.add(dc.getDoc());
		}
		loanInfo.setCropTxnDoc(cropTxnDocs);

		return loanInfo;
    }
    
	private CompletableFuture<WarehouseInfo> fetchWarehouseInfo(Loan loan) {
		return CompletableFuture.supplyAsync(() -> {
			WarehouseInfo warehouseInfo = new WarehouseInfo();
			Warehouse warehouse = warehouseService.getWarehouse(loan.getWarehouseId());
			//System.out.println("Warehouse id: " + loan.getWarehouseId());
			//System.out.println(new Gson().toJson(warehouse));
			Crop crop = farmerService.getCropByWarehouse(loan.getFarmerId(), loan.getWarehouseId());
			warehouseInfo.setCropQuality(crop == null ? null : crop.getQuality().name());
			//System.out.println("crop: " + crop);
			warehouseInfo.setPackets(
					crop == null || crop.getMeasure() == null ? 0 : crop.getQuantity() * crop.getMeasure().getValue());

			warehouseInfo.setLocation(warehouse.getLocation());
			warehouseInfo.setName(warehouse.getName());
			return warehouseInfo;
		},pool);
	}

	private CompletableFuture<FarmerInfo> fetchFarmerInfo(Loan loan) {
		return CompletableFuture.supplyAsync(() -> {
			FarmerInfo farmerInfo = new FarmerInfo();
			Farmer farmer = farmerService.getFarmer(loan.getFarmerId());
			farmerInfo.setName(farmer.getName());
			farmerInfo.setMobileNo(farmer.getMobileNo());
			farmerInfo.setCreditScore(farmer.getCreditScore());
			return farmerInfo;
		}, pool);
	}

	private CompletableFuture<LoanInfo> fetchLoanInfo(Loan loan) {
		return CompletableFuture.supplyAsync(() -> {
			LoanInfo loanInfo = new LoanInfo();
			loanInfo.setRate(loan.getBank().getInterestRate());
			loanInfo.setLoanStatus(loan.getStatus());
			loanInfo.setLoanType(loan.getType());
			loanInfo.setApplicationType(loan.getReasonForLoan());

			List<Document> cropTxnDocs = new ArrayList<>();
			List<DigitalContract> dcDocs = loan.getDocs();
			// TBD only get null dcDocs
			if (dcDocs != null) {
				for (DigitalContract dc : dcDocs)
					cropTxnDocs.add(dc.getDoc());
			}
			loanInfo.setCropTxnDoc(cropTxnDocs);

			return loanInfo;
		}, pool);
	}

	public List<Loan> getAllLoansByBank(String bankId) {
    	return loanDao.findByBankBankIdOrderByCreatedDateDesc(bankId);
    }
	
	public List<Loan> getAllLoansByFarmer(String farmerId, int limit, int pageNo) {
		int offset = (pageNo-1)*limit;
    	return loanDao.findByFarmerIdOrderByCreatedDateDesc(farmerId, limit, offset);
    }
	
    public Loan getLoan(String id) {
        return loanDao.findOne(id);
    }

    public String addLoan(LoanRequestDTO loanRequest) throws Exception{
    	try {
    		Loan loan = generateLoanRequest(loanRequest);
    		loanDao.save(loan);
    		return "Loan applied successfully. <br> Waiting for digital contract approval. You will be notified soon";
    	}catch(Exception e) {
    		System.out.println("Error while adding loan: "+e.getStackTrace());
    		throw new Exception(e);
    	}
    }

    private Loan generateLoanRequest(LoanRequestDTO loanRequest) {
    	Loan loan = new Loan();
    	loan.setPrincipal(loanRequest.getAmount());
		loan.setTenure(loanRequest.getTimeframe());
		loan.setWarehouseId(loanRequest.getWarehouseId());
		loan.setReasonForLoan(loanRequest.getReasonForLoan());
		loan.setInstallments(loanRequest.getInstallments());
		loan.setAdditionalDetails(loanRequest.getAdditionalDetails());
		// TBD main reason of taking a request dto instead of loan
		loan.setDocs(fetchDCfromDocs(loanRequest.getDocs(), loanRequest));
		processLoanDocs(loanRequest.getDocs());
		loan.setStatus(LoanStatus.NEW);
		Bank bank = bankService.getBank(loanRequest.getBankId());
		loan.setBank(bank);
		loan.setFarmerId(loanRequest.getFarmerId());
		loan.setCreatedDate(new Date());
		return loan;
	}

    //TBD Now
	private List<DigitalContract> fetchDCfromDocs(List<Document> docs, LoanRequestDTO loanRequest) {
		List<DigitalContract> digitalContracts = new ArrayList<DigitalContract>();
		if(docs==null)return null;
		for(Document doc : docs) {
			DigitalContract dc = new DigitalContract();
			dc.setDoc(doc);
			dc.setFarmerId(loanRequest.getFarmerId());
			dc.setCropId(loanRequest.getCropId());
			
			WarehouseOwner owner = warehouseService.getWarehouseOwnerByWarehouseId(loanRequest.getWarehouseId());
			dc.setStorageOwnerId(owner.getId());
			
			Signature farmerSign = new Signature();
			Farmer farmer = farmerService.getFarmer(loanRequest.getFarmerId());
			farmerSign.setName(farmer.getName());
			farmerSign.setPlace(farmer.getLocation().getCity());
			farmerSign.setDate(new Date());
			dc.setFarmerSign(farmerSign);
			dc.setStatus(ContractStatus.APPROVED_BY_FARMER);
		}
		return digitalContracts;
	}

	private void processLoanDocs(List<Document> doc) {
		
		
	}

	public void updateLoan(String id, Loan farmer) {
    	loanDao.save(farmer);
    }

    public void deleteLoan(String id) {
    	loanDao.delete(id);
    }
    
    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();
        loanDao.findAll().forEach(loans::add);
        return loans;
    }

	public List<LoanSummaryDTO> getLoanSummaryBank(String id, int limit, int pageNo) {
		int offset = (pageNo-1)*limit;
    	List<Loan>loans = loanDao.findByBankId(id, limit, offset);
    	if(loans==null)return null; 	//need to check whether there is a scenario where database given null instead of empty for null
    	List<LoanSummaryDTO> loansSummary = new ArrayList<>(); 
    	//for(Loan loan: loans) {
    	loans.parallelStream().forEach((loan) -> {
    		Warehouse warehouse = warehouseService.getWarehouse(loan.getWarehouseId());
    		Farmer farmer = farmerService.getFarmer(loan.getFarmerId());
    		LoanSummaryDTO loanSummary = new LoanSummaryDTO();
    		loanSummary.setFarmerCreditScore(farmer.getCreditScore());
    		loanSummary.setFarmerMobileNo(farmer.getMobileNo());
    		loanSummary.setFarmerName(farmer.getName());
    		//setting only city as reqd by api
    		loanSummary.setWarehouseCity(warehouse.getLocation().getCity());
    		loanSummary.setWarehouseName(warehouse.getName());
    		loanSummary.setLoanId(loan.getId());
    		loansSummary.add(loanSummary);
    	});
    	//}
		return loansSummary;
	}

	public List<LoanSummaryDTO> getLoanSummaryWarehouse(String id, int limit, int pageNo) {
		int offset = (pageNo-1)*limit;
    	List<Loan>loans = loanDao.findByWarehouseOwnerId(id, limit, offset);
    	if(loans==null)return null; 	//need to check whether there is a scenario where database given null instead of empty for null
    	List<LoanSummaryDTO> loansSummary = new ArrayList<>(); 
    	loans.parallelStream().forEach((loan) -> {
    	//for(Loan loan: loans) {
    		Warehouse warehouse = warehouseService.getWarehouse(loan.getWarehouseId());
    		Farmer farmer = farmerService.getFarmer(loan.getFarmerId());
    		LoanSummaryDTO loanSummary = new LoanSummaryDTO();
    		loanSummary.setFarmerCreditScore(farmer.getCreditScore());
    		loanSummary.setFarmerMobileNo(farmer.getMobileNo());
    		loanSummary.setFarmerName(farmer.getName());
    		//setting only city as reqd by api
    		loanSummary.setWarehouseCity(warehouse.getLocation().getCity());
    		loanSummary.setWarehouseName(warehouse.getName());
    		loanSummary.setLoanId(loan.getId());
    		loansSummary.add(loanSummary);
    	//}
    	});
		return loansSummary;
	}
	
	public EntityLoansResponseDTO getLoanDetails(String id) {
		Loan loan = getLoan(id);
		EntityLoansResponseDTO loanDetails = new EntityLoansResponseDTO();
		/*
		// avg of 5 response time: 8:10 sec
		loanDetails.setLoan(fetchLoanInfoS(loan));
		loanDetails.setFarmer(fetchFarmerInfoS(loan));
		loanDetails.setWarehouse(fetchWarehouseInfoS(loan));
		*/
		
		CompletableFuture<LoanInfo> loanInfo = fetchLoanInfo(loan);
		CompletableFuture<FarmerInfo> farmerInfo = fetchFarmerInfo(loan);
		CompletableFuture<WarehouseInfo> warehouseInfo = fetchWarehouseInfo(loan);
		CompletableFuture<Void> all = CompletableFuture.allOf(loanInfo, farmerInfo, warehouseInfo);
		try {
			// avg of 5 response time: 9 sec
			//all.get();
			// avg of 5 response time: 6:10 sec
			loanDetails.setLoan(loanInfo.get());
			loanDetails.setFarmer(farmerInfo.get());
			loanDetails.setWarehouse(warehouseInfo.get());
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Error: Interruptions in executing completable future");
		}
		
		return loanDetails;
	}

}
