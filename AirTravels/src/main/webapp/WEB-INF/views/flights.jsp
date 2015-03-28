<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Flights</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<!-- <link rel="icon" href="../../favicon.ico"> -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
<link href="resources/css/carousel.css" rel="stylesheet"/>
<link href="resources/css/jquery.timepicker.css" rel="stylesheet"/>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.5/css/jquery.dataTables.min.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.5/js/jquery.dataTables.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script type="text/javascript" src="resources/js/jquery.timepicker.js"></script>
<script>
	$(document).ready(function() {
		console.log("ready");
		$('#submitBtn').click(getFlightsDetails);
		$( "#travelDate" ).datepicker(); 
		$('#travelTime').timepicker({ 'scrollDefault': 'now' });
	});

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
				}
			});	 
		console.log("getFlightsDetails() exit");
	}
</script>


</head>
<body>
	
	<div class="container">
		<div class="row panel panel-primary">
			<div class="panel-heading">
				Check Flights
			</div>
			<div class="panel-body">
				<form action="">
						<span class="label label-default">Departure Code</span>
						<input class="" id="depCode" type="text" placeholder="eg., PHL"/>
						<span class="label label-default">Arrival Code</span>
						<input class="" id="arrCode" type="text" placeholder="eg., PHL"/>
						<span class="label label-default">No of Hops</span>
						<select id="nHops">
							<option>1</option>
							<option>2</option>
						</select>
						<span class="label label-dafault">Travel Date</span>
						<input class="" id="travelDate" type="text">
						<input class="" id="travelTime" type="text">
						<button type="button" id="submitBtn" class="btn btn-primary"> Go! </button>
					</form>
			</div>
		</div>
		<div class="row panel panel-success">
			<div class="panel-heading">
				Flights Available
			</div>
			<div class="panel-body">
				<table id="flightsDetails" class="display" cellspacing="0" width="100%">
			    </table>
			</div>
		</div>

	<!-- FOOTER -->
	<footer>
		<p>
			&copy; 2015 Company, Inc. &middot; <a href="#">Privacy</a> &middot; <a
				href="#">Terms</a>
		</p>
	</footer>

	</div>
	<!-- /.container -->
</body>
</html>