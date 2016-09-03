package com.me.FinalProject.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PAYEE")
public class Payee {

	@Id
	@Column(name = "PAYEEID")
	private int payeeId;

	@Column(name = "USERNAME")
	private String userName;

	@Column(name = "ACCOUNTNUMBER")
	private int userAccount;

	@Column(name = "PAYEEUSERNAME")
	private String payeeUserName;

	@Column(name = "PAYEEACCOUNTNUMBER")
	private int payeeAccountNumber;

	public int getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(int payeeId) {
		this.payeeId = payeeId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(int userAccount) {
		this.userAccount = userAccount;
	}

	public String getPayeeUserName() {
		return payeeUserName;
	}

	public void setPayeeUserName(String payeeUserName) {
		this.payeeUserName = payeeUserName;
	}

	public int getPayeeAccountNumber() {
		return payeeAccountNumber;
	}

	public void setPayeeAccountNumber(int payeeAccountNumber) {
		this.payeeAccountNumber = payeeAccountNumber;
	}

}
