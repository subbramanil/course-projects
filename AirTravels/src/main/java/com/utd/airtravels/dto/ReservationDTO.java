package com.utd.airtravels.dto;

public class ReservationDTO {

	private String flightNumber;
	private String travelDate;
	private String seatNumber;
	private String passengerName;
	private String passengerPhone;
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
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getPassengerPhone() {
		return passengerPhone;
	}
	public void setPassengerPhone(String passengerPhone) {
		this.passengerPhone = passengerPhone;
	}
	@Override
	public String toString() {
		return "ReservationDTO [flightNumber=" + flightNumber + ", travelDate="
				+ travelDate + ", seatNumber=" + seatNumber
				+ ", passengerName=" + passengerName + ", passengerPhone="
				+ passengerPhone + "]";
	}
	public ReservationDTO(String flightNumber, String travelDate,
			String seatNumber, String passengerName, String passengerPhone) {
		super();
		this.flightNumber = flightNumber;
		this.travelDate = travelDate;
		this.seatNumber = seatNumber;
		this.passengerName = passengerName;
		this.passengerPhone = passengerPhone;
	}
	public ReservationDTO() {
		super();
	}
	
}
