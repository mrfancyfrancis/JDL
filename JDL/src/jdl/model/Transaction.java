package jdl.model;

import java.sql.Date;

public class Transaction 
{
	private String passportNo;
	private String tinID;
	private String visaType;
	private Date visaStartDate;
	private Date visaEndDate;
	private String permitType;
	private Date permitStartDate;
	private Date permitEndDate;
	private String aepID;
	private Date aepStartDate;
	private Date aepEndDate;
	private int transID;
	private int client_id;
	private Date transTimestamp;
	private String transAuthor;
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getTinID() {
		return tinID;
	}
	public void setTinID(String tinID) {
		this.tinID = tinID;
	}
	public String getVisaType() {
		return visaType;
	}
	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}
	public Date getVisaStartDate() {
		return visaStartDate;
	}
	public void setVisaStartDate(Date visaStartDate) {
		this.visaStartDate = visaStartDate;
	}
	public Date getVisaEndDate() {
		return visaEndDate;
	}
	public void setVisaEndDate(Date visaEndDate) {
		this.visaEndDate = visaEndDate;
	}
	public String getPermitType() {
		return permitType;
	}
	public void setPermitType(String permitType) {
		this.permitType = permitType;
	}
	public Date getPermitStartDate() {
		return permitStartDate;
	}
	public void setPermitStartDate(Date permitStartDate) {
		this.permitStartDate = permitStartDate;
	}
	public Date getPermitEndDate() {
		return permitEndDate;
	}
	public void setPermitEndDate(Date permitEndDate) {
		this.permitEndDate = permitEndDate;
	}
	public String getAepID() {
		return aepID;
	}
	public void setAepID(String aepID) {
		this.aepID = aepID;
	}
	public Date getAepStartDate() {
		return aepStartDate;
	}
	public void setAepStartDate(Date aepStartDate) {
		this.aepStartDate = aepStartDate;
	}
	public Date getAepEndDate() {
		return aepEndDate;
	}
	public void setAepEndDate(Date aepEndDate) {
		this.aepEndDate = aepEndDate;
	}
	public int getTransID() {
		return transID;
	}
	public void setTransID(int transID) {
		this.transID = transID;
	}
	public int getClient_id() {
		return client_id;
	}
	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
	public Date getTransTimestamp() {
		return transTimestamp;
	}
	public void setTransTimestamp(Date transTimestamp) {
		this.transTimestamp = transTimestamp;
	}
	public String getTransAuthor() {
		return transAuthor;
	}
	public void setTransAuthor(String transAuthor) {
		this.transAuthor = transAuthor;
	}
}
