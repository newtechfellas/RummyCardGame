package com.webi.ent.registration;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table (name="USER_REGISTRY_TBL")
public class UserRegistryEntity implements Serializable {
	
	private static final long serialVersionUID = 8228606672371237183L;

	@Id
	@Column ( name="user_id")
	private String userId;
	
	@NotNull
	@Column ( name="password")
	private String password;
	
	@NotNull
	@Column ( name="user_name")
	private String userName;
	
	@NotNull
	@Column ( name="startDate")
	private Date registerDate;
	
	@Column ( name="endDate")
	private Date unRegisterDate;

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
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getUnRegisterDate() {
		return unRegisterDate;
	}

	public void setUnRegisterDate(Date unRegisterDate) {
		this.unRegisterDate = unRegisterDate;
	}
}
