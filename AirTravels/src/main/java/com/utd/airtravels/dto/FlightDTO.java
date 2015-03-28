package com.utd.airtravels.dto;


public class FlightDTO {
	
	private String flightNumber;
	private String airline;
	private String weekdays;
	private String depCode;
	private String arrCode;
	private String schedDepTime;
	private String schedArrTime;
	private int maxHop;
	
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getWeekdays() {
		return weekdays;
	}
	public void setWeekdays(String weekdays) {
		this.weekdays = weekdays;
	}
	public String getDepCode() {
		return depCode;
	}
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}
	public String getArrCode() {
		return arrCode;
	}
	public void setArrCode(String arrCode) {
		this.arrCode = arrCode;
	}

	@Override
	public String toString() {
		return "FlightDTO [flightNumber=" + flightNumber + ", airline="
				+ airline + ", weekdays=" + weekdays + ", depCode=" + depCode
				+ ", arrCode=" + arrCode + ", schedDepTime=" + schedDepTime
				+ ", schedArrTime=" + schedArrTime + ", maxHop=" + maxHop + "]";
	}
	public int getMaxHop() {
		return maxHop;
	}
	public void setMaxHop(int maxHop) {
		this.maxHop = maxHop;
	}
/*	public FlightDTO(String flightNumber, String airline, String weekdays,
			String depCode, String arrCode, Date schedDepTime,
			Date schedArrTime, int maxHop) {
		super();
		this.flightNumber = flightNumber;
		this.airline = airline;
		this.weekdays = weekdays;
		this.depCode = depCode;
		this.arrCode = arrCode;
		this.schedDepTime = schedDepTime;
		this.schedArrTime = schedArrTime;
		this.maxHop = maxHop;
	}*/
	public FlightDTO() {
		super();
	}
	public String getSchedDepTime() {
		return schedDepTime;
	}
	public void setSchedDepTime(String schedDepTime) {
		this.schedDepTime = schedDepTime;
	}
	public String getSchedArrTime() {
		return schedArrTime;
	}
	public void setSchedArrTime(String schedArrTime) {
		this.schedArrTime = schedArrTime;
	}
}
