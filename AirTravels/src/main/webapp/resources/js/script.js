

function getFlightsDetails(){
	console.log("getFlightsDetails() entry");
	var depCode = $('#depCode').val();
	var arrCode = $('#arrCode').val();
	var travelDate = $('#travelDate').val();
	console.log(travelDate);
	var values = {"depCode":depCode, "arrCode":arrCode, "maxHop":"3"};
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
					"aoColumns": [{
				    "mData":"flightNumber",
				    "sTitle": "Flight #"
				  	},{
				  		"mData":"airline",
					    "sTitle": "Airline"
				  	},
				  	{
				  		"mData":"weekdays",
					    "sTitle": "weekdays"
				  	},
				  	{
				  		"mData":"depCode",
					    "sTitle": "Departure Code"
				  	},
				  	{
				  		"mData":"schedDepTime",
					    "sTitle": "Scheduled Departure Time"
				  	},
				  	{
				  		"mData":"arrCode",
					    "sTitle": "Arrival Code"
				  	},
				  	{
				  		"mData":"schedArrTime",
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
