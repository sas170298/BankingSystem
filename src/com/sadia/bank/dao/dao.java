package com.verdantis.bank.dao;

import java.io.IOException;
import java.sql.SQLException;

import com.verdantis.bank.bo.pojo;
import com.verdantis.bank.exceptionsB.ResourseNotFoundException;

public interface dao {
	public void addCustomer(pojo customer);
	public pojo getDetail(long accNo) ;
	public  long getBalance(long accNo) ;
	public  void updateBalance(long accNo, long balance) ;
	public  void checkAccountNo(long accNo) ;
}
