package com.me.FinalProject.Model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class CheckDeposit {
	private CommonsMultipartFile check;

	public CommonsMultipartFile getCheck() {
		return check;
	}

	public void setCheck(CommonsMultipartFile check) {
		this.check = check;
	}

}
