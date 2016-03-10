package edu.neu.cs5500.HBU.MODEL;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class USER{
	@Id
	private Integer uId;
	private String username;
	private String password;


	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public USER(Integer uId, String username, String password) {
		super();
		this.uId = uId;
		this.username = username;
		this.password = password;
		
		
	
	 
	}

	public USER() {
		super();
	}

}