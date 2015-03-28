package com.utd.airtravels.dto;

public class FlightInstanceDTO {
	
	private String flightNumber;
	private String travelDate;
	private int numSeats;
	private int airplaneID;
	private String departureTime;
	private String arrivalTime;
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getTravelDate() {
		return travelDate;
	}
	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}
	public int getNumSeats() {
		return numSeats;
	}
	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}
	public int getAirplaneID() {
		return airplaneID;
	}
	public void setAirplaneID(int airplaneID) {
		this.airplaneID = airplaneID;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	@Override
	public String toString() {
		return "FlightInstanceDTO [flightNumber=" + flightNumber
				+ ", travelDate=" + travelDate + ", numSeats=" + numSeats
				+ ", airplaneID=" + airplaneID + ", departureTime="
				+ departureTime + ", arrivalTime=" + arrivalTime + "]";
	}
	public FlightInstanceDTO() {
		super();
	}
	
}
