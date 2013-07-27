package com.webi.ent.common.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationVO {
		
	@NotNull
	@Size(min=8,max=300)
	private String emailId;
	
	@NotNull
	@Size(min=4,max=50)
	private String userName;
	
	@NotNull
	@Size(min=5)
	private String password;

	public RegistrationVO(String emailId, String password, String userName ) {
		super();
		this.emailId = emailId;
		this.password = password;
		this.userName = userName;
	}

	public RegistrationVO() { }
	

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}