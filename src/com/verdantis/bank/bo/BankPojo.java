package com.verdantis.bank.bo;

import java.util.ArrayList;

public class BankPojo {
	String bankName;
	int bankCode;
	ArrayList<BranchPojo> branchList;
	public BankPojo(String bankName,int bankCode,ArrayList<BranchPojo> branchList)
	{
		this.bankName=bankName;
		this.bankCode=bankCode;
		this.branchList=branchList;
	}
	public BankPojo(String bankName, int bankId) {
		// TODO Auto-generated constructor stub
		this.bankName=bankName;
		this.bankCode=bankId;
		
		
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public int getBankCode() {
		return bankCode;
	}
	public void setBankCode(int bankCode) {
		this.bankCode = bankCode;
	}
	public ArrayList<BranchPojo> getBranchList() {
		return branchList;
	}
	public void setBranchList(ArrayList<BranchPojo> branchList) {
		this.branchList = branchList;
	}
	
}
