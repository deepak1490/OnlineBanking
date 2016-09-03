package com.me.FinalProject.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTION")
public class Transaction {

	@Id
	@Column(name = "TRANSACTIONID")
	private int transactionId;

	@Column(name = "USERNAME")
	private String userName;

	@Column(name = "ACCOUNTNUMBER")
	private int accountNumber;

	@Column(name = "AMOUNT")
	private double amount;

	@Column(name = "BALANCE")
	private double balance;

	@Column(name = "TRANSACTIONDATE")
	private String date;

	@Column(name = "TRANSACTIONTYPE")
	private String transactionType;

	@Column(name = "TRANSACTIONDESCRIPTION")
	private String description;

	@Column(name = "CHECKNAME")
	private String check;

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date2) {
		this.date = date2;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
