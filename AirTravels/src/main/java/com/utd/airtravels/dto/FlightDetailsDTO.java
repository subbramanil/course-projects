package com.utd.airtravels.dto;

public class FlightDetailsDTO {
	
	private FlightDTO flight1;
	private FlightDTO flight2;
	private FlightDTO flight3;
	private FlightDTO flight4;

	private int numHops;

	public FlightDTO getFlight1() {
		return flight1;
	}

	public void setFlight1(FlightDTO flight1) {
		this.flight1 = flight1;
	}

	public FlightDTO getFlight2() {
		return flight2;
	}

	public void setFlight2(FlightDTO flight2) {
		this.flight2 = flight2;
	}

	public FlightDTO getFlight3() {
		return flight3;
	}

	public void setFlight3(FlightDTO flight3) {
		this.flight3 = flight3;
	}

	public FlightDTO getFlight4() {
		return flight4;
	}

	public void setFlight4(FlightDTO flight4) {
		this.flight4 = flight4;
	}

	public int getNumHops() {
		return numHops;
	}

	public void setNumHops(int numHops) {
		this.numHops = numHops;
	}

	@Override
	public String toString() {
		return "FlightDetailsDTO [flight1=" + flight1 + ", flight2=" + flight2
				+ ", flight3=" + flight3 + ", flight4=" + flight4
				+ ", numHops=" + numHops + "]";
	}	
}
