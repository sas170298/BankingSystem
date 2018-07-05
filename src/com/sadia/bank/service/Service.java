package com.verdantis.bank.service;

public interface Service {
	public void deposit(long accNo,long money);
	public void withdraw(long accNo,long money) ;
	public void transact(long accNo1,long accNo2,long money);
}
