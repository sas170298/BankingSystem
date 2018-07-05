package com.verdantis.bank.bo;

import java.util.ArrayList;

public class pojo{
	long accountNo;
	String name;
	String email;
	String address;
	long phone;
	float balance;
	long branchId;
	BranchPojo branch;
public pojo(long accNo,String name,String email,String address,long phone,float balance,BranchPojo branch)
{
 this.accountNo=accNo;
		this.name=name;
		this.email=email;
		this.address=address;
		this.phone=phone;
		this.balance=balance;
		this.branch=branch;
		
}

public pojo(long accountNo, String name, String email, String address, long phone, float balance, long branchId) {
	// TODO Auto-generated constructor stub
	this.accountNo=accountNo;
	this.name=name;
	this.email=email;
	this.address=address;
	this.phone=phone;
	this.balance=balance;
	this.branchId=branchId;
}

/*public pojo(long accNo, String name, String email, String address, long phone, float balance, long branchId) {
	// TODO Auto-generated constructor stub
	this.accountNo=accountNo;
	this.name=name;
	this.email=email;
	this.address=address;
	this.phone=phone;
	this.balance=balance;
	this.branchId=branchId;
}
*/
public BranchPojo getBranch() {
	return branch;
}

public void setBranch(BranchPojo branch) {
	this.branch = branch;
}

public long getAccountNo() {
	return accountNo;
}
public void setAccountNo(long accountNo) {
	this.accountNo = accountNo;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public long getPhone() {
	return phone;
}
public void setPhone(long phone) {
	this.phone = phone;
}
public float getBalance() {
	return balance;
}
public void setBalance(float balance) {
	this.balance = balance;
}

public long getBranchId() {
	return branchId;
}

public void setBranchId(int branchId) {
	this.branchId = branchId;
}


}
