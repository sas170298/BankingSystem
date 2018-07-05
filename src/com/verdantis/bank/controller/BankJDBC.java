package com.verdantis.bank.controller;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.verdantis.bank.bo.*;
//import com.verdantis.bank.controller.*;
import com.verdantis.bank.dao.daoImpl;
import com.verdantis.bank.service.serviceImpl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;

import javax.swing.text.StyleConstants;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

public class BankJDBC {		
public static	Logger log=Logger.getLogger(BankJDBC.class);
private static Scanner sc;

	public static void main(String args[]){ 
		 
		int op=0;
		sc = new Scanner(System.in);
		do{
			System.out.println("press\n 1.ADD CUSTOMER\n 2.DEPOSIT CASH\n 3.WITHDRAW CASH\n 4.TRANSACT\n 5.LIST CUSTOMER DETAILS\n 6.ADD BRANCH\n 7.ADD BANK\n 8.LIST BRANCH DETAILS\n 9.LIST BANK DETAILS\n 10.EXIT");
			op=sc.nextInt();
			switch(op)
			{
			case 1:
			{
				//add customer				
				addCust();
				break;
			}
			case 2:
			{
				//DEPOSIT
				depositMoney();				
				break;
			}
			case 3:
			{
				//WITHDRAW
				withdrawMoney();				
				break;
			}
			case 4:
			{
				//TRANSACT
				transactMoney();		
				break;
			}
			case 5:
			{
				//GET CUSTOMER DETAILS
				getCustDetails();				
				break;
			}
			case 6:
			{
				addBranch();
				break;
			}
			case 7:
			{
				addBank();
				break;
			}
			case 8:
			{
				getBranchDetails();
				break;
			}
			case 9:
			{
				getBankDetails();
				break;
			}
			case 10:
			{
				try{				
					 String FILE = "C:/Users/sadia.siddiqui/Downloads/temp/ThirdPdf.pdf";
					 Document document=new Document();
					 PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
				  table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER); 
				  table.addCell("Name");
			      table.addCell("Age");
			      table.addCell("Location");
				  table.setHeaderRows(1);
				  
				  PdfPCell[] cells = table.getRow(0).getCells(); 
				  
				  
				  for (int j=0;j<cells.length;j++){
				     cells[j].setBackgroundColor(BaseColor.PINK);
				  }
			          for (int i=1;i<5;i++){
			    	     table.addCell("Name:"+i);
			             table.addCell("Age:"+i);
			             table.addCell("Location:"+i);
			          }
			      
			          
				  PdfWriter.getInstance(document, new FileOutputStream(FILE));
				  document.open();
				  
				  
				  PdfPCell cell=new PdfPCell(new Paragraph("hello"));
		//ColumnText text= cell.getColumn();
				  PdfReader reader =new PdfReader(FILE);
				  String text=PdfTextExtractor.getTextFromPage(reader,1);
				  System.out.println(text);
				  
				  
				  document.add(new Paragraph("my timestamp"));
				  	 table.addCell("Name: yo");
		             table.addCell("Age: 20");
		             table.addCell("Location: Andheri");
			      document.add(table);
			      document.add(cell);
				  document.close();
				}catch(Exception e)
				{
					System.err.println(e);
				}
				break;
			}
			
			case 11:
			{
				try{
					 String FILE = "C:/Users/sadia.siddiqui/Downloads/temp/SecondPdf.pdf";
					 Document document=new Document();
					 PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
				PdfWriter.getInstance(document, new FileOutputStream(FILE));
				document.open();
				table.addCell("Name: sadia");
	             table.addCell("Age: 20");
	             table.addCell("Location: Andheri");
				document.close();
				}catch(Exception e)
				{
					System.err.println(e);
				}
			}
			
			case 12:
			{
				try{
					 String FILE = "C:/Users/sadia.siddiqui/Downloads/temp/SecondPdf.pdf";
					 Document document=new Document();
					 PdfWriter.getInstance(document, new FileOutputStream(FILE));
					 document.open();
					PdfReader reader = new PdfReader(FILE); 
					
document.close();
	/*	 int n = reader.getNumberOfPages();
System.out.println(n);		
	 *     StringWriter output = new StringWriter();  
				    output.append(PdfTextExtractor.getTextFromPage(reader, 1, new SimpleTextExtractionStrategy()));
				   System.out.println(output.toString()); 
		*/		
				}catch(Exception e)
							{ e.printStackTrace();}
			}
			
			}
			
			}while(op!=20);
		
			
	}
	
	private static void addBank() {
System.out.println("ENTER BANK NAME, BANK ID(one after the other inorder)");				
		
		String bankName=sc.next();		
		int bankId=sc.nextInt();
		
		BankPojo bank=new BankPojo(bankName,bankId);
                                                          				
		serviceImpl s=new serviceImpl();
		s.addBank(bank);
		
	}

	private static void addBranch() {
System.out.println("ENTER BRANCH ID, BRANCH LOCATION, BRANCH MANAGER, NO.OF EMPLOYEES, BANK ID(one after the other inorder)");		
		
		int branchId=sc.nextInt();
		String branchLoc=sc.next();
		String branchMan=sc.next();
		
		int noOfEmps=sc.nextInt();
		//float balance=sc.nextFloat();
		int bankId=sc.nextInt();
		BranchPojo branch=new BranchPojo(branchId,branchLoc, branchMan, noOfEmps, bankId);
		
		
		serviceImpl s=new serviceImpl();
		s.addBranch(branch);
		
	}

	private static void getBankDetails() {
		System.out.println("ENTER BANK CODE");
		int bankCode=sc.nextInt();
		try{
			
			serviceImpl s=new serviceImpl();
			s.checkBankExists(bankCode);
			BankPojo bankObj=s.getBankDetails(bankCode);
			
			String bankName=bankObj.getBankName();
			
			System.out.println("BANK NAME "+bankName);
			
			
			ArrayList<BranchPojo> list=bankObj.getBranchList();
			
			Iterator<BranchPojo> it=list.iterator();
			System.out.println("\n\n LIST OF BRANCHES!");
			System.out.println("Branch ID \t\t name \t\t email \t\t address \t\t");
			while(it.hasNext())
			{
				BranchPojo branchObj= it.next();
		//took items from branchObj	
				long branch_id=branchObj.getBranch_id();
				String branch_location=branchObj.getBranch_location();
				String branch_manager=branchObj.getBranch_manager();
				int branch_no_of_emp=branchObj.getBranch_no_of_emps();
				ArrayList<pojo> customerList=branchObj.getAccounts();
		//finished taking items from branchObj	
		/*print*/		System.out.println(branch_id+"\t\t"+branch_location+"\t\t"+branch_manager+"\t\t"+branch_no_of_emp+"");
				
				Iterator<pojo> it2=customerList.iterator();  //iterate customer list
				System.out.println("----------------------------------------------");  
				while(it2.hasNext()) //print customers for each branch
				{
				pojo customer=it2.next();
				
				long accNo=customer.getAccountNo();
				String name=customer.getName();
				String email=customer.getEmail();
				String address=customer.getAddress();
				long phone=customer.getPhone();
				float balance=customer.getBalance();
				
				System.out.println(accNo+"\t\t"+name+"\t\t"+email+"\t\t"+address+"\t\t"+phone+"\t\t"+balance);
				
				}
				System.out.println();
				
			}
			
/*			
			System.out.println("ACCOUNT NO\t\t"+"CUSTOMER NAME\t\t"+"BALANCE");
			ArrayList<BranchPojo> list2=bankObj.getBranchList();
			Iterator<BranchPojo> it2=list.iterator();
			while(it2.hasNext())
			{
				BranchPojo branchObj= it2.next();
				long branchId=branchObj.getBranch_id();
				
				pojo cust=s.getCustomerList(branchId);
				long accNo=cust.getAccountNo();
				String custName=cust.getName();
				float balance=cust.getBalance();
				System.out.println(accNo+"\t\t"+custName+"\t\t"+balance);
				
			}
*/			
			
		}catch(Exception e)
		{
			System.out.println("NO SUCH BANK EXISTS");
			log.error(e);
		}
		
	}

	private static void getBranchDetails() {
		System.out.println("ENTER BRANCH ID");
		int branchId=sc.nextInt();
		try{
		
		serviceImpl s=new serviceImpl();
		s.checkBranchExists(branchId);
				
		BranchPojo branchObj=s.getBranchDetails(branchId);
			
		String branch_location=branchObj.getBranch_location();
		String branch_manager=branchObj.getBranch_manager();
		int branch_no_of_emps=branchObj.getBranch_no_of_emps();
		int bankId=branchObj.getBankId();
		String bankName=branchObj.getBankName();
		System.out.println("BRANCH LOCATION "+branch_location);
		System.out.println("BRANCH MANAGER "+branch_manager);
		System.out.println("NO. OF EMPLOYEES"+branch_no_of_emps);
		System.out.println("BANK ID "+bankId);
		System.out.println("BANK NAME "+bankName);
		
		ArrayList<pojo> list=branchObj.getAccounts();
		Iterator<pojo> it=list.iterator();
		System.out.println("\n\n LIST OF CUSTOMER DETAILS!");
		System.out.println("acctNo \t\t name \t\t email \t\t address \t\t phone \t\t balance \t\t");
		while(it.hasNext())
		{
			pojo cust= it.next();
			long accountNo=cust.getAccountNo();
			String name=cust.getName();
			String email=cust.getEmail();
			String address=cust.getAddress();
			long phone=cust.getPhone();
			float balance=cust.getBalance();			
			System.out.println(accountNo+"\t\t"+name+"\t\t"+email+"\t\t"+address+"\t\t"+phone+"\t\t"+balance+"\t\t");
			
		}
		
		}catch(Exception e)
		{
			System.out.println("NO SUCH BRANCH EXISTS");
			log.error(e);
		}
		
	}
	
	private static void getCustDetails() {
		System.out.println("ENTER YOUR ACCOUNT NO");
		long accNo=sc.nextLong();
		try{	
			serviceImpl s=new serviceImpl();
		
			pojo customer=s.getDetail(accNo);
			
			String name=customer.getName();
			String email=customer.getEmail();
			String address=customer.getAddress();
			long phone=customer.getPhone();
			float balance=customer.getBalance();
			BranchPojo branch=customer.getBranch();
			System.out.println("NAME IS "+name);
			System.out.println("EMAIL ID IS "+email);
			System.out.println("ADDRESS IS "+address);
			System.out.println("PHONE NO IS "+phone);
			System.out.println("BALANCE IS "+balance);
			
			//REMOVE DETAILS FROM BRANCH OBJECT
			String location=branch.getBranch_location();
			String manager=branch.getBranch_manager();
			int noOfEmps=branch.getBranch_no_of_emps();
			int bankId=branch.getBankId();
			System.out.println("BRANCH LOCATION IS "+location);
			System.out.println("BRANCH MANAGER IS "+manager);
			System.out.println("NO.OF EMPLOYEES IS "+noOfEmps);
		System.out.println("BANK ID "+bankId);
		}catch(Exception e)
		{
			log.error(e);
		}
		
	}
	private static void transactMoney() {
		System.out.println("ENTER YOUR ACCOUNT NO");
		long accNo1=sc.nextLong();				
		System.out.println("ENTER OTHER ACCOUNT NO");
		long accNo2=sc.nextLong();
		try{		
		System.out.println("ENTER AMOUNT TO DEPOSIT IN "+accNo2);
		long money=sc.nextLong();
			try{
				serviceImpl s=new serviceImpl();
				s.transact(accNo1,accNo2,money);		
				}catch(Exception e)
						{
							System.out.println("YOU HAVE LOW BALANCE");
							log.error(e);
						}
	}catch(Exception e)
	{
		log.error(e);
	}
}	
	private static void withdrawMoney() {
		System.out.println("ENTER ACCOUNT NO");
		long accNo=sc.nextLong();
		try{		
		System.out.println("ENTER AMOUNT TO WITHDRAW");
		long money=sc.nextLong();
		try{
			serviceImpl s=new serviceImpl();
		s.withdraw(accNo,money);
		}catch(Exception e)
		{
			System.out.println("INVALID BALANCE");
			log.error(e);
		}
		}catch(Exception e)
		{
			log.error(e);
		}
	
		
	}
	private static void depositMoney() {
		System.out.println("ENTER ACCOUNT NO");
		long accNo=sc.nextLong();
		//CHECK ACCOUNT NO EXISTS OR NOT
		
		try{		
			System.out.println("ENTER AMOUNT TO DEPOSIT");
			long money=sc.nextLong();
			serviceImpl s=new serviceImpl();
			s.deposit(accNo,money);
		}catch(Exception e)
		{
			log.error(e);
		}
		
	}
	private static void addCust() {
		System.out.println("ENTER ACCOUNT NO, NAME,EMAIL ID,ADDRESS,PHONE NUMBER, BALANCE, BRANCH ID(one after the other inorder)");		
		
		int accountNo=sc.nextInt();
		String name=sc.next();
		String email=sc.next();
		String address=sc.next();
		int phone=sc.nextInt();
		float balance=sc.nextFloat();
		int branchId=sc.nextInt();
		pojo customer=new pojo(accountNo, name, email, address, phone, balance,branchId);
				
		serviceImpl s=new serviceImpl();
		s.addCust(customer);
			
	}
}
