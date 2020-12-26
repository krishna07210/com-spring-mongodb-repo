package com.springboot.mongodb.model;

import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

	@Id
	private String userid;
	private String name;
	private Date creatonDate = new Date();
	private Map<String, String> userSettings = new HashMap<>();

	public User(String userid, String name, Date creatonDate, Map<String, String> userSettings) {
		super();
		this.userid = userid;
		this.name = name;
		this.creatonDate = creatonDate;
		this.userSettings = userSettings;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatonDate() {
		return creatonDate;
	}

	public void setCreatonDate(Date creatonDate) {
		this.creatonDate = creatonDate;
	}

	public Map<String, String> getUserSettings() {
		return userSettings;
	}

	public void setUserSettings(Map<String, String> userSettings) {
		this.userSettings = userSettings;
	}

}
