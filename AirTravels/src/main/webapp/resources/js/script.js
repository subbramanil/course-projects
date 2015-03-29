

function getFlightsDetails(){
	console.log("getFlightsDetails() entry");
	var depCode = $('#depCode').val();
	var arrCode = $('#arrCode').val();
	var travelDate = $('#travelDate').val();
	var maxHop = $('#nHops').val();
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
				if ( $.fn.dataTable.isDataTable( '#flightsDetails' ) ) {
					console.log("Deleting existing table");
					$("#flightsDetails").dataTable().fnDestroy();
				}
				
				$('#flightsDetails').dataTable( {
					"data": data,
					"aaSorting": [],
					"aoColumns": [
					{
						"mData":"flightNumber",
						"sTitle": "Flight #",
						"orderable": false
				  	},
				  	{
				  		"mData":"airline",
					    "sTitle": "Airline",
					    "orderable": false
				  	},
				  	{
				  		"mData":"weekdays",
					    "sTitle": "weekdays",
					    "orderable": false
				  	},
				  	{
				  		"mData":"depCode",
					    "sTitle": "Departure Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"schedDepTime",
					    "sTitle": "Scheduled Departure Time",
					    "orderable": false
				  	},
				  	{
				  		"mData":"arrCode",
					    "sTitle": "Arrival Code",
					    "orderable": false
				  	},
				  	{
				  		"mData":"schedArrTime",
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
				  	},{
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
