package com.verdantis.bank.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.verdantis.bank.bo.*;
import com.verdantis.bank.controller.*;
import com.verdantis.bank.dao.*;
import com.verdantis.bank.exceptionsB.*;
import com.verdantis.bank.resourses.*;

public class serviceImpl implements Service {

	daoImpl d=new daoImpl();
	
	
public void deposit(long accNo,long money) 
{
	try{
		//daoImpl d=new daoImpl();
		d.checkAccountNo(accNo);
	 
	long balance=d.getBalance(accNo);
	balance+=money;
	d.updateBalance(accNo,balance);
	}catch(Exception e)
	{
		BankJDBC.log.error(e);
	}
}

public void withdraw(long accNo,long money) 
{
	try{

	//daoImpl d=new daoImpl();
	d.checkAccountNo(accNo);	
	long balance=d.getBalance(accNo);
	if(balance<money)
	{		
			throw new InvalidBalanceException("INVALID BALANCE");		
	}
	else
	{
	balance-=money;
	d.updateBalance(accNo,balance);
	}
	}catch(Exception e)
	{
		BankJDBC.log.error(e);
	}
}

public void transact(long accNo1,long accNo2,long money) 
{
	try{
//	daoImpl d=new daoImpl();
	
	d.checkAccountNo(accNo1);
	d.checkAccountNo(accNo2);
	long balance1=d.getBalance(accNo1);
	long balance2=d.getBalance(accNo2);
	if(balance1<money)
		
			throw new InvalidBalanceException("INVALID BALANCE");

	else{
		balance1-=money;
		balance2+=money;
		d.transact(accNo1,balance1,accNo2,balance2);
		
	}
	}catch(Exception e)
	{
		BankJDBC.log.error(e);
	}
}

public void addCust(pojo customer)
{
	//daoImpl d=new daoImpl();
	d.addCustomer(customer);
}

public pojo getDetail(long accNo) {
	// TODO Auto-generated method stub
	//daoImpl d=new daoImpl();
	d.checkAccountNo(accNo);
	pojo cust=d.getDetail(accNo);
	return cust;
}


public BranchPojo getBranchDetails(int branchId) {
	//daoImpl d=new daoImpl();
	d.checkBranchExists(branchId);
	BranchPojo branchObj=d.getBranchDetails(branchId);
	return branchObj;
}



public BankPojo getBankDetails(int bankCode) {
//	daoImpl d=new daoImpl();	
	BankPojo bankObj=d.getBankDetails(bankCode);
	return bankObj;
}

public void addBranch(BranchPojo branch) {
	//daoImpl d=new daoImpl();
	d.addBranch(branch);
}

public void addBank(BankPojo bank) {
	//daoImpl d=new daoImpl();
	d.addBank(bank);
}

public pojo getCustomerList(long branchId) {
//	daoImpl d=new daoImpl();
	pojo cust=d.getCustomerList(branchId);
	return cust;
}

public void checkBranchExists(long branchId) throws ResourseNotFoundException {
	// TODO Auto-generated method stub
	if(d.checkBranchExists(branchId)){
		
	}
	else
		throw new ResourseNotFoundException("NO SUCH BRANCH EXISTS");
}

public void checkBankExists(int bankCode) throws ResourseNotFoundException {
	// TODO Auto-generated method stub
	if(d.checkBankExists(bankCode))
	{
		
	}
	else
		throw new ResourseNotFoundException("NO SUCH BANK EXISTS");
}




}