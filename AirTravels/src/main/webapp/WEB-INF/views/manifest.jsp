<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Manifest</title>
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
<script type="text/javascript" src="resources/js/script.js"></script>
<script>
	$(document).ready(function() {
		console.log("ready");
		$('#getPassengerListBtn').click(getPassengersList);
		$('#getFlightsBtn').click(getFlightsList);
		$( "#travelDate" ).datepicker({dateFormat: "dd-mm-yy"}); 
	});
</script>

</head>
<body>
	
	<div class="container">
		<div class="row panel panel-primary">
			<div class="panel-heading">
				Passenger Manifest
			</div>
			<div class="panel-body">
				<form action="">
					<div class="form-group col-md-6">
					    <label for="flightNumber">Flight #</label>
					    <input type="number" class="form-control" id="flightNumber" placeholder="eg., 20">
					</div>
					<div class="form-group col-md-6">
						<label for="travelDate">Travel Date</label>
						<input class="form-control"  id="travelDate">
					</div>
					<button type="button" id="getPassengerListBtn" class="btn btn-primary"> Check Passenger Details </button>
				</form>
			</div>
		</div>
		<div class="row panel panel-success">
			<div class="panel-heading">
				List of Passengers
			</div>
			<div class="panel-body">
				<table id="passengersList" class="display">
			    </table>
			</div>
		</div>
		
		<div class="row panel panel-primary">
			<div class="panel-heading">
				Passenger Manifest
			</div>
			<div class="panel-body">
				<form action="">
					<div class="form-group">
					    <label for="passengerName">Flight #</label>
					    <input type="text" class="form-control" id="passengerName" placeholder="subbu">
					</div>
					<button type="button" id="getFlightsBtn" class="btn btn-primary"> Check Flights </button>
				</form>
			</div>
		</div>
		<div class="row panel panel-success">
			<div class="panel-heading">
				List of Flights
			</div>
			<div class="panel-body">
				<table id="flightsList" class="display">
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