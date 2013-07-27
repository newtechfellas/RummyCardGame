package com.webi.games.rummy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MAIL_MANAGER_TBL")
public class MailManagerEntity {

	@Id
	@Column(name="USER_NAME")
	private String userId;
	
	@Column(name="PASSWORD")
	private String password;

	
	public MailManagerEntity(String userId, String password) {
		super();
		this.userId = userId;
		this.password = password;
	}

	public MailManagerEntity() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
