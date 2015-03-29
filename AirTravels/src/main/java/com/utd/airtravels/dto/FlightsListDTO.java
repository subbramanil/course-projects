package com.utd.airtravels.dto;

import java.util.List;

public class FlightsListDTO {

	private List<FlightDetailsDTO> flightsList;
	private List<FlightDetailsDTO> flightsWith1HopList;
	private List<FlightDetailsDTO> flightsWith2HopList;
	private List<FlightDetailsDTO> flightsWith3HopList;
	public List<FlightDetailsDTO> getFlightsList() {
		return flightsList;
	}
	public void setFlightsList(List<FlightDetailsDTO> flightsList) {
		this.flightsList = flightsList;
	}
	public List<FlightDetailsDTO> getFlightsWith1HopList() {
		return flightsWith1HopList;
	}
	public void setFlightsWith1HopList(List<FlightDetailsDTO> flightsWith1HopList) {
		this.flightsWith1HopList = flightsWith1HopList;
	}
	public List<FlightDetailsDTO> getFlightsWith2HopList() {
		return flightsWith2HopList;
	}
	public void setFlightsWith2HopList(List<FlightDetailsDTO> flightsWith2HopList) {
		this.flightsWith2HopList = flightsWith2HopList;
	}
	public List<FlightDetailsDTO> getFlightsWith3HopList() {
		return flightsWith3HopList;
	}
	public void setFlightsWith3HopList(List<FlightDetailsDTO> flightsWith3HopList) {
		this.flightsWith3HopList = flightsWith3HopList;
	}
	@Override
	public String toString() {
		return "FlightsListDTO [flightsList=" + flightsList
				+ ", flightsWith1HopList=" + flightsWith1HopList
				+ ", flightsWith2HopList=" + flightsWith2HopList
				+ ", flightsWith3HopList=" + flightsWith3HopList + "]";
	}
}
