package com.verdantis.bank.bo;

import java.util.ArrayList;

public class BranchPojo {
	long branch_id;
	String branch_location;
	String branch_manager;
	int branch_no_of_emps;
	ArrayList<pojo> accounts;
	int bankId;
	String bankName;
public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	/*	public BranchPojo(long branch_id,String branch_location,String branch_manager,int branch_no_of_emps,ArrayList<pojo> accounts)
	{
		this.branch_id=branch_id;
		this.branch_location=branch_location;
		this.branch_manager=branch_manager;
		this.branch_no_of_emps=branch_no_of_emps;
		this.accounts=accounts;
	}*/
	public int getBankId() {
		return bankId;
	}
	public void setBankId(int bankId) {
		this.bankId = bankId;
	}
	public BranchPojo(int branchId, String branchLoc, String branchMan, int noOfEmps) {
		// TODO Auto-generated constructor stub
		this.branch_id=branchId;
		this.branch_location=branchLoc;
		this.branch_manager=branchMan;
		this.branch_no_of_emps=noOfEmps;

	}
	public BranchPojo(int branchId, String branchLoc, String branchMan, int noOfEmps, int bankId) {
		// TODO Auto-generated constructor stub
		this.branch_id=branchId;
		this.branch_location=branchLoc;
		this.branch_manager=branchMan;
		this.branch_no_of_emps=noOfEmps;
		this.bankId=bankId;
	}
	
	
	//CONTAINS EVERYTHING
	public BranchPojo(int branchId, String branchLocation, String branch_manager, int branch_no_of_emps,ArrayList<pojo> list, int bankId,String bankName) {
		// TODO Auto-generated constructor stub
		this.branch_id=branchId;
		this.branch_location=branchLocation;
		this.branch_manager=branch_manager;
		this.branch_no_of_emps=branch_no_of_emps;
		this.bankId=bankId;
		this.bankName=bankName;
		this.accounts=list;
		
	}

	public BranchPojo(int branchId, String branchLoc, String branchMan, int noOfEmps, ArrayList<pojo> list) {
		// TODO Auto-generated constructor stub
		this.branch_id=branchId;
		this.branch_location=branchLoc;
		this.branch_manager=branchMan;
		this.branch_no_of_emps=noOfEmps;
		this.accounts=list;
	}
	public ArrayList<pojo> getAccounts() {
		return accounts;
	}
	public void setAccounts(ArrayList<pojo> accounts) {
		this.accounts = accounts;
	}
	public long getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(long branch_id) {
		this.branch_id = branch_id;
	}
	public String getBranch_location() {
		return branch_location;
	}
	public void setBranch_location(String branch_location) {
		this.branch_location = branch_location;
	}
	public String getBranch_manager() {
		return branch_manager;
	}
	public void setBranch_manager(String branch_manager) {
		this.branch_manager = branch_manager;
	}
	public int getBranch_no_of_emps() {
		return branch_no_of_emps;
	}
	public void setBranch_no_of_emps(int branch_no_of_emps) {
		this.branch_no_of_emps = branch_no_of_emps;
	}
}
