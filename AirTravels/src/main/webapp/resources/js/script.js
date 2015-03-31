

function getFlightsDetails(){
	console.log("getFlightsDetails() entry");
	var depCode = $('#depCode').val();
	var arrCode = $('#arrCode').val();
//	var travelDate = $('#travelDate').val();
	var maxHop = $('#nHops').val();
	if(!depCode || !arrCode ){
		alert("Enter all details");
		return "";
	}
	var values = {"depCode":depCode, "arrCode":arrCode, "maxHop":maxHop };
	console.log(values);
	$.ajax({
		   url: "getFlights",
		   crossDomain:true,
		   data: JSON.stringify(values),
		   type: "POST",
		   contentType: "application/json; charset=utf-8",
		   datatype:"json",
		   success: function(data) {
			  	console.log(data);
				console.log(JSON.stringify(data));
				
				console.log("FlightsList:"+data.flightsList);
				
				if ( $.fn.dataTable.isDataTable( '#flightsDetails' ) ) {
					console.log("Deleting existing table");
					$("#flightsDetails").dataTable().fnDestroy();
				}
				
				$('#flightsDetails').dataTable( {
					"data": data.flightsList,
					"aaSorting": [],
					"aoColumns": [
					{
						"mData":"flight1.flightNumber",
						"sTitle": "Flight #",
						"orderable": false
				  	},
				  	{
				  		"mData":"flight1.airline",
					    "sTitle": "Airline",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.weekdays",
					    "sTitle": "Service Days",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.depCode",
					    "sTitle": "Departure Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.schedDepTime",
					    "sTitle": "Scheduled Departure Time",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.arrCode",
					    "sTitle": "Arrival Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.schedArrTime",
					    "sTitle": "Scheduled Arrival Time",
					    "orderable": false
				  	}
				  ]
		   		});
				
				console.log("FlightsList:"+data.flightsWith1HopList);
				
				if ( $.fn.dataTable.isDataTable( '#flightsWith1HopDetails' ) ) {
					console.log("Deleting existing table");
					$("#flightsWith1HopDetails").dataTable().fnDestroy();
				}
				
				
				$('#flightsWith1HopDetails').dataTable( {
					"data": data.flightsWith1HopList,
					"aaSorting": [],
					"scrollX": true,
					"aoColumns": [
					{
						"mData":"flight1.flightNumber",
						"sTitle": "Flight #",
						"orderable": false
				  	},
				  	{
				  		"mData":"flight1.airline",
					    "sTitle": "Airline",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.weekdays",
					    "sTitle": "weekdays",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.depCode",
					    "sTitle": "Departure Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.schedDepTime",
					    "sTitle": "Scheduled Departure Time",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.arrCode",
					    "sTitle": "Arrival Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.schedArrTime",
					    "sTitle": "Scheduled Arrival Time",
					    "orderable": false
				  	},
				  	{
						"mData":"flight2.flightNumber",
						"sTitle": "Flight #2",
						"orderable": false
				  	},
				  	{
				  		"mData":"flight2.airline",
					    "sTitle": "Airline",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight2.depCode",
					    "sTitle": "Departure Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight2.schedDepTime",
					    "sTitle": "Scheduled Departure Time",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight2.arrCode",
					    "sTitle": "Arrival Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight2.schedArrTime",
					    "sTitle": "Scheduled Arrival Time",
					    "orderable": false
				  	}
				  ]
		   		});
				
				console.log("FlightsList:"+data.flightsWith2HopList);
				
				if ( $.fn.dataTable.isDataTable( '#flightsWith2HopDetails' ) ) {
					console.log("Deleting existing table");
					$("#flightsWith2HopDetails").dataTable().fnDestroy();
				}
				
				$('#flightsWith2HopDetails').dataTable( {
					"data": data.flightsWith2HopList,
					"aaSorting": [],
					"scrollX": true,
					"aoColumns": [
					{
						"mData":"flight1.flightNumber",
						"sTitle": "Flight #",
						"orderable": false
				  	},
				  	{
				  		"mData":"flight1.airline",
					    "sTitle": "Airline",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.weekdays",
					    "sTitle": "weekdays",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.depCode",
					    "sTitle": "Departure Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.schedDepTime",
					    "sTitle": "Scheduled Departure Time",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.arrCode",
					    "sTitle": "Arrival Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.schedArrTime",
					    "sTitle": "Scheduled Arrival Time",
					    "orderable": false
				  	},
				  	{
						"mData":"flight2.flightNumber",
						"sTitle": "Flight #2",
						"orderable": false
				  	},
				  	{
				  		"mData":"flight2.airline",
					    "sTitle": "Airline",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight2.depCode",
					    "sTitle": "Departure Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight2.schedDepTime",
					    "sTitle": "Scheduled Departure Time",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight2.arrCode",
					    "sTitle": "Arrival Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight2.schedArrTime",
					    "sTitle": "Scheduled Arrival Time",
					    "orderable": false
				  	},
				  	{
						"mData":"flight3.flightNumber",
						"sTitle": "Flight #3",
						"orderable": false
				  	},
				  	{
				  		"mData":"flight3.airline",
					    "sTitle": "Airline",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight3.depCode",
					    "sTitle": "Departure Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight3.schedDepTime",
					    "sTitle": "Scheduled Departure Time",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight3.arrCode",
					    "sTitle": "Arrival Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight3.schedArrTime",
					    "sTitle": "Scheduled Arrival Time",
					    "orderable": false
				  	},
				  ]
		   		});
				
				console.log("FlightsList:"+data.flightsWith3HopList);
				
				if ( $.fn.dataTable.isDataTable( '#flightsWith3HopDetails' ) ) {
					console.log("Deleting existing table");
					$("#flightsWith3HopDetails").dataTable().fnDestroy();
				}
				
				$('#flightsWith3HopDetails').dataTable( {
					"data": data,
					"aaSorting": [],
					"scrollX": true,
					"aoColumns": [
					{
						"mData":"flight1.flightNumber",
						"sTitle": "Flight #1",
						"orderable": false
				  	},
				  	{
				  		"mData":"flight1.airline",
					    "sTitle": "Airline",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.weekdays",
					    "sTitle": "weekdays",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.depCode",
					    "sTitle": "Departure Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.schedDepTime",
					    "sTitle": "Scheduled Departure Time",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.arrCode",
					    "sTitle": "Arrival Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight1.schedArrTime",
					    "sTitle": "Scheduled Arrival Time",
					    "orderable": false
				  	},
				  	{
						"mData":"flight2.flightNumber",
						"sTitle": "Flight #2",
						"orderable": false
				  	},
				  	{
				  		"mData":"flight2.airline",
					    "sTitle": "Airline",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight2.depCode",
					    "sTitle": "Departure Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight2.schedDepTime",
					    "sTitle": "Scheduled Departure Time",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight2.arrCode",
					    "sTitle": "Arrival Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight2.schedArrTime",
					    "sTitle": "Scheduled Arrival Time",
					    "orderable": false
				  	},
				  	{
						"mData":"flight3.flightNumber",
						"sTitle": "Flight #3",
						"orderable": false
				  	},
				  	{
				  		"mData":"flight3.airline",
					    "sTitle": "Airline",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight3.depCode",
					    "sTitle": "Departure Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight3.schedDepTime",
					    "sTitle": "Scheduled Departure Time",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight3.arrCode",
					    "sTitle": "Arrival Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight3.schedArrTime",
					    "sTitle": "Scheduled Arrival Time",
					    "orderable": false
				  	},
				  	{
						"mData":"flight4.flightNumber",
						"sTitle": "Flight #4",
						"orderable": false
				  	},
				  	{
				  		"mData":"flight4.airline",
					    "sTitle": "Airline",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight4.depCode",
					    "sTitle": "Departure Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight4.schedDepTime",
					    "sTitle": "Scheduled Departure Time",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight4.arrCode",
					    "sTitle": "Arrival Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"flight4.schedArrTime",
					    "sTitle": "Scheduled Arrival Time",
					    "orderable": false
				  	}
				  ]
		   		});
		   },
		   error: function() {
			     console.log("Error in accessing flight details");
			     alert("Error in accessing flight details");
			}
		});	 
	console.log("getFlightsDetails() exit");
}


function getFareDetails(){
	console.log("getFareDetails() entry");
	var flightNumber = $('#flightNumber').val();
	console.log(flightNumber);
	var values = {"flightNumber":flightNumber};
	console.log(values);
	if(!flightNumber){
		alert("Enter flight number");
		return "";
	}
	$.ajax({
		   url: "getFlightFares",
		   crossDomain:true,
		   data: JSON.stringify(values),
		   type: "POST",
		   contentType: "application/json; charset=utf-8",
		   datatype:"json",
		   success: function(data) {
			  	console.log(data);
				console.log(JSON.stringify(data));
				if ( $.fn.dataTable.isDataTable( '#fareDetails' ) ) {
					console.log("Deleting existing table");
					$("#fareDetails").dataTable().fnDestroy();
				}
				
				$('#fareDetails').dataTable( {
					"data": data,
					"aoColumns": [
					{
						"mData":"flightNumber",
						"sTitle": "Flight #"
				  	},
				  	{
				  		"mData":"airline",
					    "sTitle": "Airline"
				  	},
				  	{
				  		"mData":"deptCode",
					    "sTitle": "Departure Code"
				  	},
				  	{
				  		"mData":"arrCode",
					    "sTitle": "Arrival Code"
				  	},
				  	{
				  		"mData":"fareCode",
					    "sTitle": "Fare Code"
				  	},
				  	{
				  		"mData":"fareAmount",
					    "sTitle": "Fare Amount"
				  	},
				  	{
				  		"mData":"restrictions",
					    "sTitle": "Restrictions"
				  	}
				  ]
		   		});
		   },
		   error: function() {
			     console.log("Error in accessing fare details");
			     alert("Error in accessing fare details");
			}
		});	 
	console.log("getFareDetails() exit");
}

function getSeatsAvailability(){
	console.log("getFlightsDetails() entry");
	var flightNumber = $('#flightNumber').val();
	var travelDate = $('#travelDate').val();
	if(!flightNumber || !travelDate){
		alert("Enter all details");
		return "";
	}
	var values = {"flightNumber":flightNumber, "travelDate":travelDate};
	console.log(values);
	$.ajax({
		   url: "getSeatAvailable",
		   crossDomain:true,
		   data: JSON.stringify(values),
		   type: "POST",
		   contentType: "application/json; charset=utf-8",
		   datatype:"json",
		   success: function(data) {
			  	console.log(data);
				console.log(JSON.stringify(data));
				if ( $.fn.dataTable.isDataTable( '#seatDetails' ) ) {
					console.log("Deleting existing table");
					$("#seatDetails").dataTable().fnDestroy();
				}
				
				$('#seatDetails').dataTable( {
					"data": data,
					"aoColumns": [
					{
					    "mData":"flightNumber",
					    "sTitle": "Flight #"
				  	},{
				  		"mData":"travelDate",
					    "sTitle": "Travel Date"
				  	},
				  	{
				  		"mData":"numSeats",
					    "sTitle": "Available Seats"
				  	},
				  	{
				  		"mData":"airplaneID",
					    "sTitle": "Airplane ID"
				  	},
				  	{
				  		"mData":"departureTime",
					    "sTitle": "Scheduled Departure Time"
				  	},
				  	{
				  		"mData":"arrivalTime",
					    "sTitle": "Scheduled Arrival Time"
				  	}
				  ]
		   		});
		   },
		   error: function() {
			     console.log("Error in accessing flight details");
			     alert("Error in accessing flight details");
			}
		});	 
	console.log("getFlightsDetails() exit");
}



function getPassengersList(){
	console.log("getPassengersList() entry");
	var flightNumber = $('#flightNumber').val();
	var travelDate = $('#travelDate').val();
	if(!flightNumber || !travelDate){
		alert("Enter all details");
		return "";
	}
	var values = {"flightNumber":flightNumber, "travelDate":travelDate};
	console.log(values);
	$.ajax({
		   url: "getPassengersList",
		   crossDomain:true,
		   data: JSON.stringify(values),
		   type: "POST",
		   contentType: "application/json; charset=utf-8",
		   datatype:"json",
		   success: function(data) {
			  	console.log(data);
				console.log(JSON.stringify(data));
				if ( $.fn.dataTable.isDataTable( '#passengersList' ) ) {
					console.log("Deleting existing table");
					$("#passengersList").dataTable().fnDestroy();
				}
				
				$('#passengersList').dataTable( {
					"data": data,
					"aoColumns": [
					{
					    "mData":"flightNumber",
					    "sTitle": "Flight #"
				  	},{
				  		"mData":"travelDate",
					    "sTitle": "Travel Date"
				  	},
				  	{
				  		"mData":"seatNumber",
					    "sTitle": "Seat Number"
				  	},
				  	{
				  		"mData":"passengerName",
					    "sTitle": "Passenger Name"
				  	},
				  	{
				  		"mData":"passengerPhone",
					    "sTitle": "Passenger Phone"
				  	}
				  ]
		   		});
		   },
		   error: function() {
			     console.log("Error in accessing flight details");
			     alert("Error in accessing flight details");
			}
		});	 
	console.log("getPassengersList() exit");
}


function getFlightsList(){
	console.log("getFlightsList() entry");
	var passengerName = $('#passengerName').val();
	var values = {"passengerName":passengerName};
	if(!passengerName){
		alert("Enter Passenger Name");
		return "";
	}
	console.log(values);
	$.ajax({
		   url: "getFlightsList",
		   crossDomain:true,
		   data: JSON.stringify(values),
		   type: "POST",
		   contentType: "application/json; charset=utf-8",
		   datatype:"json",
		   success: function(data) {
			  	console.log(data);
				console.log(JSON.stringify(data));
				if ( $.fn.dataTable.isDataTable( '#flightsList' ) ) {
					console.log("Deleting existing table");
					$("#flightsList").dataTable().fnDestroy();
				}
				
				$('#flightsList').dataTable( {
					"data": data,
					"aoColumns": [
					{
					    "mData":"flightNumber",
					    "sTitle": "Flight #"
				  	},{
				  		"mData":"travelDate",
					    "sTitle": "Travel Date"
				  	},
				  	{
				  		"mData":"seatNumber",
					    "sTitle": "Seat Number"
				  	},
				  	{
				  		"mData":"passengerName",
					    "sTitle": "Passenger Name"
				  	},
				  	{
				  		"mData":"passengerPhone",
					    "sTitle": "Passenger Phone"
				  	}
				  ]
		   		});
		   },
		   error: function() {
			     console.log("Error in accessing flight details");
			     alert("Error in accessing flight details");
			}
		});	 
	console.log("getFlightsList() exit");
}
