package com.utd.airtravels.dto;

public class FareDTO {
	
	private String flightNumber;
	private String fareCode;
	private float fareAmount;
	private String restrictions;
	private String airline;
	private String deptCode;
	private String arrCode;
	
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getFareCode() {
		return fareCode;
	}
	public void setFareCode(String fareCode) {
		this.fareCode = fareCode;
	}
	public float getFareAmount() {
		return fareAmount;
	}
	public void setFareAmount(float fareAmount) {
		this.fareAmount = fareAmount;
	}
	public String getRestrictions() {
		return restrictions;
	}
	public void setRestrictions(String restrictions) {
		this.restrictions = restrictions;
	}
	@Override
	public String toString() {
		return "FareDTO [flightNumber=" + flightNumber + ", fareCode="
				+ fareCode + ", fareAmount=" + fareAmount + ", restrictions="
				+ restrictions + ", airline=" + airline + ", deptCode="
				+ deptCode + ", arrCode=" + arrCode + "]";
	}
	public FareDTO() {
		super();
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getArrCode() {
		return arrCode;
	}
	public void setArrCode(String arrCode) {
		this.arrCode = arrCode;
	}
	
	
}
