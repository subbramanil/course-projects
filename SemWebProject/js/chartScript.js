var adminApp = angular.module('adminApp', ['ngRoute']);

adminApp.config(['$routeProvider',
	function($routeProvider) {
		$routeProvider.
			when('/Dashboard', {
				templateUrl: 'pages/dashboard.html',
				controller: 'DashBoardController'
			}).
			when('/Surgery', {
				templateUrl: 'pages/surgeryTypes.html',
				controller: 'SurgeryController'
			}).
            when('/Infrastructure', {
                templateUrl: 'pages/infrastructure.html',
                controller: 'InfraController'
            }).
			otherwise({
				redirectTo: '/index'
			});
	}]);

adminApp.controller('DashBoardController', function($scope) {

	$scope.message = 'This is Dashboard screen';

});

function prepareData(fileName){
    console.log("prepareData() entry");
    var arrayData = null;

    $.ajax({
        url: fileName,
        type: 'get',
        async: false,
        success: function(csvString) {
            arrayData  = $.csv.toArrays(csvString, {onParseValue: $.csv.hooks.castToScalar});;
        },
        error: function(){
            alert("Error in Ajax call");
        }
    });
    console.log("prepareData() exit");
    return arrayData ;
}

function setupNewData(arrayData, columnName, colNum){
    console.log("setupNewData() entry");
    var data = new google.visualization.arrayToDataTable(arrayData);
    var newData = new google.visualization.DataTable();

    newData.addColumn('string', 'State', 'state');
    newData.addColumn('number', columnName,'count');
    var rows = data.getNumberOfRows();
    for (var i = 0; i < rows; i++ )
    {
        var state = 'US-' + stateLookup(i);
        var value = data.getValue(i, colNum);
        newData.addRow([state, value]);
    }
    console.log("setupNewData() exit");
    return newData;
}


adminApp.controller('SurgeryController', ['$scope', function($scope, $http){

    $scope.choices = [
        {name:'General Surgery', col:1},
        {name:'Vision Surgery', col:2},
        {name:'Cardiac Surgery', col:3},
        {name:'OrthoPedic Surgery', col:4},
        {name:'Vascular Surgery', col:5},
        {name:'other Surgery', col:6}
    ];

    // load google visualization packages
    google.load('visualization', '0', {'packages': ['geomap','corechart','geochart']});

	$scope.change = function($http) {
		console.log("admin.change() entry");
		console.log($scope.userChoice);
        drawSurgeryMap($scope.userChoice);
		console.log("admin.change() exit");
	};

    function drawSurgeryMap(choice){
        console.log("drawSurgeryMap() entry");
        var arrayData  = prepareData("data/surgeryCountPerState.csv");
        var newData = setupNewData(arrayData , choice.name, choice.col);

        var options = {};
        options['region'] = 'US';   // show US map
        options['dataMode'] = 'regions';
        options['width'] = '100%';
        options['height'] = 600;
        options['colors'] = [0xADEBAD, 0x5C85FF, 0xFF1919];

        var divId = document.getElementById('chart');
        var chart = new google.visualization.GeoMap(divId);
        chart.draw(newData, options );
        console.log("drawSurgeryMap() exit");
    }

}]);


adminApp.controller('InfraController', ['$scope', function($scope, $http){

    // load google visualization packages
    google.load('visualization', '0', {'packages': ['geomap']});

    drawGeoMap();

    function drawGeoMap(){
        console.log("chartScript.drawGeoMap() entry");
        var arrayData  = prepareData("data/emergBeds.csv");
        var newData = setupNewData(arrayData , "Emerg. Beds", 1);
        $scope.regionName = "";
        var options = {};
        options['region'] = 'US';   // show US map
        options['dataMode'] = 'regions';
        options['width'] = '100%';
        options['height'] = 400;
        options['colors'] = [0xADEBAD, 0x5C85FF, 0xFF1919];

        var geochart = new google.visualization.GeoMap(document.getElementById('geoChart'));

        google.visualization.events.addListener(geochart, 'regionClick', function (eventData) {
            var region = eventData.region.substr(3);
            console.log(region);
            $scope.regionName = region;
            var newData = [];
            var rows = stateData.length;
            for (var i = 1; i < rows; i++ )
            {
                if(stateData[i][0] == region){
                    newData.push(stateData[i]);
                }
            }
            console.log("Total records in "+region+" is "+newData.length);
            var tableTemplate = " <table id='stateTable' class='table table-hover table-striped'>" +
                "<tr> " +
                "<th>City</th> " +
                "<th>Facility Name</th> " +
                "<th>Emerg. Rooms</th> " +
                "<th>Speciality Services</th> " +
                "<th>Intensive Care Unit</th> " +
                "<th>Intensive Care Unit class</th> " +
                "<th>Maternitiy Care</th> " +
                "</tr>";

            $.each(newData, function(index) {
                var obj = newData[index];
                var row = "<tr>"+
                    "<td>"+obj[1]+"</td>"+
                    "<td>"+obj[2]+"</td>"+
                    "<td>"+obj[3]+"</td>"+
                    "<td>"+obj[4]+"</td>"+
                    "<td>"+obj[5]+"</td>"+
                    "<td>"+obj[6]+"</td>"+
                    "<td>"+obj[7]+"</td>"+
                    "</tr>"
                tableTemplate+=row;
            });
            tableTemplate+="</table>"
            $('#stateTable').html(tableTemplate);
        });

        geochart.draw(newData, options );

        console.log("chartScript.drawGeoMap() exit");
    }

}]);

function setupGeoMap(){
    console.log("setupGeoMap() entry");
    var newData = new google.visualization.DataTable();

    newData.addColumn('string', 'State', 'state');
    for (var i = 0; i < state_code_arr.length; i++ )
    {
        var state = 'US-' + stateLookup(i);
        newData.addRow([state]);
    }
    console.log("setupGeoMap() exit");
    return newData;
}

var stateData = prepareData("data/infraData.csv");