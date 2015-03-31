<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<!-- <link rel="icon" href="../../favicon.ico"> -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />

<link href="resources/css/carousel.css" rel="stylesheet">

<script>
	
</script>
</head>
<body>
	<div class="navbar-wrapper">
		<div class="container">

			<nav class="navbar navbar-inverse navbar-static-top">
				<div class="container">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse" data-target="#navbar"
							aria-expanded="false" aria-controls="navbar">
							<span class="sr-only">Toggle navigation</span> <span
								class="icon-bar"></span> <span class="icon-bar"></span> <span
								class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="#">Fly-US</a>
					</div>
					<div id="navbar" class="navbar-collapse collapse">
						<ul class="nav navbar-nav">

						</ul>
					</div>
				</div>
			</nav>

		</div>
	</div>
	<div id="myCarousel" class="carousel slide" data-ride="carousel">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
			<li data-target="#myCarousel" data-slide-to="1"></li>
			<li data-target="#myCarousel" data-slide-to="2"></li>
		</ol>
		<div class="carousel-inner" role="listbox">
			<div class="item active">
				<img class="first-slide" src="resources/imgs/flight_1.jpg"
					alt="First slide">
				<div class="container">
					<div class="carousel-caption">
					</div>
				</div>
			</div>
			<div class="item">
				<img class="second-slide" src="resources/imgs/flight_2.jpg"
					alt="Second slide">
				<div class="container">
					<div class="carousel-caption">
					</div>
				</div>
			</div>
			<div class="item">
				<img class="third-slide" src="resources/imgs/flight_3.jpg"
					alt="Third slide">
				<div class="container">
					<div class="carousel-caption">
					</div>
				</div>
			</div>
		</div>
		<a class="left carousel-control" href="#myCarousel" role="button"
			data-slide="prev"> <span class="glyphicon glyphicon-chevron-left"
			aria-hidden="true"></span> <span class="sr-only">Previous</span>
		</a> <a class="right carousel-control" href="#myCarousel" role="button"
			data-slide="next"> <span
			class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			<span class="sr-only">Next</span>
		</a>
	</div>
	<div class="container marketing">

		<!-- Three columns of text below the carousel -->
		<div class="row">
			<div class="col-lg-3">
				<img class="img-circle" src="resources/imgs/flight_1.jpg"
					alt="Generic placeholder image" width="140" height="140">
				<h2>Check for Flights</h2>
					Display the flight numbers and weekdays of all flights
					<ul>
						<li>
							Given a Departure Airport Code and Arrival Airport Code
						</li>
						<li>
							If no direct flight exists, determine multiple connecting flights which
							have compatible scheduled departure and arrival times and weekdays.
						</li>
					</ul>
				<p>
					<a class="btn btn-default" href="flightDetails" role="button">Check flights
						&raquo;</a>
				</p>
			</div>
			<!-- /.col-lg-4 -->
			<div class="col-lg-3">
				<img class="img-circle" src="resources/imgs/flight_3.jpg"
					alt="Generic placeholder image" width="140" height="140">
				<h2>Check Fares</h2>
				Display all Fare information.
				<ul>
						<li>
							Given a flight number
						</li>
				</ul>
				<p>
					<a class="btn btn-default" href="fareDetails" role="button">View details
						&raquo;</a>
				</p>
			</div>
			<!-- /.col-lg-4 -->
			<div class="col-lg-3">
				<img class="img-circle" src="resources/imgs/flight_2.jpg"
					alt="Generic placeholder image" width="140" height="140">
				<h2>Seat Avaialbility</h2>
				<p>Display the number of available seats on a particular flight
					instance.
					<ul>
						<li>
							Given a flight number and Date
						</li>
					</ul>
				<p>
					<a class="btn btn-default" href="seatDetails" role="button">Check Seats
						&raquo;</a>
				</p>
			</div>
			<div class="col-lg-3">
				<img class="img-circle" src="resources/imgs/flight_3.jpg"
					alt="Generic placeholder image" width="140" height="140">
				<h2>Check Passenger Manifest</h2>
				User shall be able to query a passenger manifest
					<ul>
						<li>
							Display
							list of passengers on a given flight instance
						</li>
						<li>
							Display a list of
							flight instances for a particular passenger name
						</li>
					</ul> 
					
				<p>
					<a class="btn btn-default" href="checkManifest" role="button">View details
						&raquo;</a>
				</p>
			</div>
			<!-- /.col-lg-4 -->
		</div>
		<!-- /.row -->

		<!-- FOOTER -->
		<footer>
			<p class="pull-right">
				<a href="#">Back to top</a>
			</p>
			<p>
				&copy; 2014 Company, Inc. &middot; <a href="#">Privacy</a> &middot;
				<a href="#">Terms</a>
			</p>
		</footer>

	</div>
	<!-- /.container -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</body>
</html>