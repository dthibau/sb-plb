package org.formation.controller;

import java.util.Date;

public class ErrorDto {

	private String msg;
	private String severity;
	private Date date;
	
	
	public ErrorDto(String msg, String severity, Date date) {
		super();
		this.msg = msg;
		this.severity = severity;
		this.date = date;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
