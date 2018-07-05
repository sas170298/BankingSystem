package com.verdantis.bank.dao;
import com.verdantis.bank.bo.*;
import com.verdantis.bank.controller.*;
import com.verdantis.bank.exceptionsB.*;
import com.verdantis.bank.resourses.*;
import com.verdantis.bank.service.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;

import com.verdantis.bank.resourses.resourseFactory;
import com.verdantis.bank.exceptionsB.*;

public class daoImpl implements dao{
	
	private ArrayList<pojo> list;

	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Connection con;			
		String host=resourseFactory.getDbDetails("dbHost");
		String dbname=resourseFactory.getDbDetails("dbName");
		String dbuser=resourseFactory.getDbDetails("dbUser");
		String dbpass=resourseFactory.getDbDetails("dbPass");
	   					
		Class.forName("com.mysql.jdbc.Driver"); 
		con=DriverManager.getConnection("jdbc:mysql://"+host+"/"+dbname+"",""+dbuser +"",""+dbpass+"");
		//con.setAutoCommit(true);
	return con;
	}
	
	public void addCustomer(pojo customer)
	{
		try{ 
		
			Connection con=getConnection();	
			long accountNo=customer.getAccountNo();
			String name=customer.getName();
			String email=customer.getEmail();
			String address=customer.getAddress();
			long phone=customer.getPhone();
			float balance=customer.getBalance();
			long branchId=customer.getBranchId();
			
			String sql = "INSERT INTO sadia_customers VALUES (?, ?, ?,?,?,?,?)";
		
			PreparedStatement stmt=con.prepareStatement(sql);
			
			stmt.setLong(1, accountNo);
			stmt.setString(2, name);
			stmt.setString(3, email);
			stmt.setString(4, address);
			stmt.setLong(5, phone);
			stmt.setFloat(6, balance);		
			stmt.setLong(7,branchId);
			stmt.executeUpdate();
			if(con!=null)
			con.close();  
			}catch(Exception e){ 
				
				BankJDBC.log.error(e);
			}
			}  	
	
	
public pojo getDetail(long accNo) 			//METHOD TO GET CUSTOMER DETAILS
{
	try{
	Connection con=getConnection();
	
	String query="select * from sadia_customers where account_no=?";
	PreparedStatement stmt = con.prepareStatement(query);
	stmt.setLong(1, accNo);
	
	ResultSet rs=stmt.executeQuery();  
	while(rs.next())  
	{
		String name=rs.getString("cust_name");
		String email=rs.getString("cust_email");
		String address=rs.getString("cust_address");
		long phone=rs.getLong("cust_phoneno");
		float balance=rs.getFloat("cust_balance");
		int branchId=rs.getInt("branch_id");
		
		BranchPojo bpojo=getBranchDetails(branchId);
		pojo customer =new pojo(accNo,name,email,address,phone,balance,bpojo );
		if(con!=null)
		con.close();
		return customer;
	}
	
	return null;
	}catch(Exception e)
	{
		BankJDBC.log.error(e);
	}
	return null;
}

public  long getBalance(long accNo) 			//METHOD TO GET BALANCE
{
	
try{
	Connection con=getConnection();	
	long result = 0;
	
String sql = "SELECT cust_balance FROM sadia_customers" +
        " WHERE account_no = "+accNo +" ";
Statement stmt = con.createStatement();

ResultSet rs = stmt.executeQuery(sql);

while(rs.next()){
result  = rs.getLong("cust_balance");
}
if(con!=null)	
con.close();
return result;
}catch(Exception e)
{
BankJDBC.log.error(e);	
}
return 0;
}

public  void updateBalance(long accNo, long balance) {
try{
	Connection con=getConnection();	
	con.setAutoCommit(false);
	String sql = "UPDATE sadia_customers"+" SET cust_balance=? WHERE account_no = ? ";
	PreparedStatement stmt = con.prepareStatement(sql);
	
	stmt.setFloat(1, balance);
	stmt.setLong(2, accNo);
	stmt.executeUpdate();
	con.commit();
	if(con!=null)
	con.close();
}catch(Exception e)
{}
}

public  void checkAccountNo(long accNo) 
{
	int count=0;
	try{
	Connection con=getConnection();
	String sql="select * from sadia_customers where account_no= ? ";
	PreparedStatement stmt=con.prepareStatement(sql);
	
	stmt.setLong(1, accNo);
	ResultSet rs=stmt.executeQuery();
	while(rs.next())
	{
		count++;
	}
	if(con!=null)
	con.close();
	if(count==1)
	{
		
	}	
	else 
		throw new ResourseNotFoundException("NO SUCH ACCOUNT NO");	
	
	
	}catch(Exception e){}
}

public boolean checkBranchExists(long branchId) {			//METHOD TO CHECK BRANCH EXISTENCE
	int count=0;
	try{
	Connection con=getConnection();
	String sql="select * from sadia_branches where branch_id= ? ";
	PreparedStatement stmt=con.prepareStatement(sql);
	
	stmt.setLong(1, branchId);
	ResultSet rs=stmt.executeQuery();
	while(rs.next())
	{
		count++;
	}
	if(con!=null)
	con.close();
	if(count==1)
	{
		return true;
	}	
	else 
		return false;	
	
	
	}catch(Exception e){BankJDBC.log.error(e);}
	return false;
}

public BranchPojo getBranchDetails(int branchId)			//main method for branch details
{
	//LIST TO STORE CUSTOMERS
	ArrayList<pojo> list=new ArrayList<>();
	try{
		int count=0;
		Connection con=getConnection();
		//TO GET CUSTOMER LIST FOR THE REQUIRED BRANCH ID
		String sql="select * from sadia_customers where branch_id= ? ";
		PreparedStatement stmt=con.prepareStatement(sql);	
		stmt.setInt(1, branchId);
		
		ResultSet rs=stmt.executeQuery();
		while(rs.next())
		{
			count++;
			long accNo=rs.getLong("account_no");
			String name=rs.getString("cust_name");
			String email=rs.getString("cust_email");
			String address=rs.getString("cust_address");
			long phone=rs.getLong("cust_phoneno");
			float balance=rs.getFloat("cust_balance");
			
			pojo cust=new pojo(accNo,name,email,address, phone, balance,branchId);
			list.add(cust);
		}
		if(count==0)throw new ResourseNotFoundException("NO SUCH BRANCH EXISTS");
		//TO GET BRANCH DETAILS
		String sql2="select * from sadia_branches where branch_id= ? ";
		PreparedStatement stmt2=con.prepareStatement(sql2);		
		stmt2.setInt(1, branchId);
		
		ResultSet rs2=stmt2.executeQuery();
		String branch_manager = null;
		String branchLocation = null;
		int branch_no_of_emps = 0;
		int bankId = 0;
				
		while(rs2.next())
		{
			branchLocation=rs2.getString("branch_location");
			 branch_manager=rs2.getString("branch_manager");
			 branch_no_of_emps=rs2.getInt("branch_no_of_emps");	
			 bankId=rs2.getInt("bank_code");
		}	
		
		//TO GET BANK NAME
		String query="select * from sadia_banks where bank_code=?";
		PreparedStatement stmt3=con.prepareStatement(query);
		stmt3.setInt(1, bankId);
		String bankName=null;
		ResultSet rs3=stmt3.executeQuery();
		while(rs3.next())
		{
		 bankName = rs3.getString("bank_name");
		}
		
		//PUT IN BRANCH POJO AND RETURN
		BranchPojo branchDetail=new BranchPojo( branchId,branchLocation,branch_manager,branch_no_of_emps, list,bankId,bankName);
		//public BranchPojo(int branchId, String branchLocation, String branch_manager, int branch_no_of_emps,ArrayList<pojo> list, int bankId,String bankName)	
		if(con!=null)con.close();
		return branchDetail ;
		
		}catch(Exception e){
			
			BankJDBC.log.error(e);
			}
	
//	if(con!=null)con.close();
	return null;
}

/*
public ArrayList<BranchPojo> getBranchList(ArrayList<Integer> list) {
	// TODO Auto-generated method stub
	try{
	Connection con=getConnection();
	ArrayList<BranchPojo> outputList=new ArrayList<>();
	Iterator<BranchPojo> it=outputList.iterator();
	while(it.hasNext())
	{
		
		BranchPojo branchObj=it.next();
		String sql="select * from sadia_branches where branch_id= ? ";
		PreparedStatement stmt=con.prepareStatement(sql);	
		stmt.setInt(1, branchId);
		
		ResultSet rs=stmt.executeQuery();
		while(rs.next())
		{
			long branch_id=rs.getLong("branch_id");
			String branch_location=rs.getString("branch_location");
			String branch_manager=rs.getString("branch_manager");
			int branch_no_of_emps=rs.getInt("branch_no_of_emps");
		
			BranchPojo branch=new BranchPojo( branch_id,branch_location, branch_manager,branch_no_of_emps, null);
			outputList.add(branch);
		}
		
	}
	con.close();
	return outputList;
	}catch(Exception e){
		BankJDBC.log.error(e);
	}
return null;
}
*/

public BankPojo getBankDetails(int bankCode) {				//MAIN METHOD FOR BANK DETAILS
	// TODO Auto-generated method stub
	try{
		String bankName = null;
		Connection con=getConnection();
		String query="select * from sadia_banks where bank_code=?";
		PreparedStatement stmt=con.prepareStatement(query);
		stmt.setInt(1, bankCode);
		ResultSet rs=stmt.executeQuery();
		while(rs.next())
		{
			 bankName=rs.getString("bank_name");		//GOT BANK NAME HERE
		}
		
		ArrayList<BranchPojo> list=getBranchForBank(bankCode);		//GOT BRANCH LIST HERE(CALLS getBranchForBank)
		
		BankPojo bankObj=new BankPojo(bankName,bankCode,list);		//MADE BANK OBJECT HERE
		if(con!=null)con.close();
		return bankObj;												//RETURNED BANK OBJECT
		}catch(Exception e)
		{
			BankJDBC.log.error(e);
		}
	
	return null;
	
}




public ArrayList<BranchPojo> getBranchForBank(int bankCode) {		//for bank details
	// TODO Auto-generated method stub
	ArrayList<BranchPojo> branchList=new ArrayList<>();	
	try{
	Connection con=getConnection();
	String query="select * from sadia_branches where bank_code=?";  //took all branches
	PreparedStatement stmt=con.prepareStatement(query);
	stmt.setInt(1, bankCode);
	ResultSet rs=stmt.executeQuery();
	while(rs.next())
	{
		int branchId=rs.getInt("branch_id");
		String branchLoc=rs.getString("branch_location");
		String branchMan=rs.getString("branch_manager");
		int NoOfEmps=rs.getInt("branch_no_of_emps");
		
		ArrayList<pojo> custList=getCustomerPojoList(branchId);		//for each branch,make customer list,calls getcustomerpojolist
				
		BranchPojo branchObj=new BranchPojo(branchId,branchLoc,branchMan,NoOfEmps,custList);

		branchList.add(branchObj);
		
	}
	if(con!=null)con.close();
	}catch(Exception e)
	{
		BankJDBC.log.error(e);
	}
	
	return branchList;
}

public ArrayList<pojo> getCustomerPojoList(long branchId) {
	// TODO Auto-generated method stub
	try
	{
	Connection con=getConnection();
	
	String sql="select * from sadia_customers where branch_id= ? ";
	PreparedStatement stmt=con.prepareStatement(sql);	
	stmt.setLong(1, branchId);
	
	ResultSet rs=stmt.executeQuery();
	ArrayList<pojo> custList = new ArrayList<>();
	while(rs.next())
	{
		long accNo=rs.getLong("account_no");
		String name=rs.getString("cust_name");
		String email=rs.getString("cust_email");
		String address=rs.getString("cust_address");
		long phone=rs.getLong("cust_phoneno");
		float balance=rs.getFloat("cust_balance");
		
		pojo cust=new pojo(accNo,name,email,address, phone, balance,branchId);
		custList.add(cust);
	}
	return custList;
	}catch(Exception e)
	{
		BankJDBC.log.error(e);
	}
	return null;
}

public void addBranch(BranchPojo branch) {
	// TODO Auto-generated method stub
	try{ 
		
		Connection con=getConnection();	
		long branch_id=branch.getBranch_id();
		String branch_location=branch.getBranch_location();
		String branch_manager=branch.getBranch_manager();
		int branch_no_of_emps=branch.getBranch_no_of_emps();
		//ArrayList<pojo> accounts;
		int bankId=branch.getBankId();
		
		String sql = "INSERT INTO sadia_branches VALUES (?, ?, ?,?,?)";
	
		PreparedStatement stmt=con.prepareStatement(sql);
		
		stmt.setLong(1, branch_id);
		stmt.setString(2, branch_location);
		stmt.setString(3, branch_manager);
		
		stmt.setLong(4, branch_no_of_emps);
			
		stmt.setInt(5,bankId);
		stmt.executeUpdate();

		if(con!=null)con.close();
		}catch(Exception e){ 
			
			BankJDBC.log.error(e);
		}
}

public void addBank(BankPojo bank) {
try{ 
		
		Connection con=getConnection();	
		
		String bankName=bank.getBankName();
		
		int bankId=bank.getBankCode();
		
		String sql = "INSERT INTO sadia_banks VALUES (?, ?)";
	
		PreparedStatement stmt=con.prepareStatement(sql);
		
		stmt.setString(1, bankName);
		stmt.setLong(2, bankId);
		
		stmt.executeUpdate();

		if(con!=null)con.close(); 
		}catch(Exception e){ 
			
			BankJDBC.log.error(e);
		}
	
	
}

public pojo getCustomerList(long branchId) {
	// TODO Auto-generated method stub
	try{
	Connection con=getConnection();
	//TO GET CUSTOMER LIST FOR THE REQUIRED BRANCH ID
	String sql="select * from sadia_customers where branch_id= ? ";
	PreparedStatement stmt=con.prepareStatement(sql);	
	stmt.setLong(1, branchId);
	
	ResultSet rs=stmt.executeQuery();
	while(rs.next())
	{
		long accNo=rs.getLong("account_no");
		String name=rs.getString("cust_name");
		String email=rs.getString("cust_email");
		String address=rs.getString("cust_address");
		long phone=rs.getLong("cust_phoneno");
		float balance=rs.getFloat("cust_balance");
		
		pojo cust=new pojo(accNo,name,email,address, phone, balance,branchId);
		//list.add(cust);
		if(con!=null)con.close();
		return cust;
	}
	}catch(Exception e)
	{
		BankJDBC.log.error(e);
	}
	
	return null;
}

public boolean checkBankExists(int bankCode) {
	int count=0;
	try{
	Connection con=getConnection();
	String sql="select * from sadia_banks where bank_code= ? ";
	PreparedStatement stmt=con.prepareStatement(sql);
	
	stmt.setLong(1, bankCode);
	ResultSet rs=stmt.executeQuery();
	while(rs.next())
	{
		count++;
	}
	if(con!=null)
	con.close();
	if(count==1)
	{
		return true;
	}	
	else 
		return false;	
	
	
	}catch(Exception e){BankJDBC.log.error(e);}
	return false;
	
}

public void transact(long accNo1, long balance1, long accNo2, long balance2) {
	try{
	Connection con=getConnection();
	con.setAutoCommit(false);
	updateBalanceForTran(accNo1,balance1,con);	
	
	
	updateBalanceForTran(accNo2,balance2,con);
	con.commit();
	}catch(Exception e)
	{
		BankJDBC.log.error(e);
	}
}
public  void updateBalanceForTran(long accNo, long balance,Connection con) {
try{	
	String sql = "UPDATE sadia_customers"+" SET cust_balance=? WHERE account_no = ? ";
	PreparedStatement stmt = con.prepareStatement(sql);	
	stmt.setFloat(1, balance);
	stmt.setLong(2, accNo);
	stmt.executeUpdate();
}catch(Exception e)
{}
}



}