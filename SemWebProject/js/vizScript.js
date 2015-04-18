var adminApp = angular.module('adminApp', ['ngRoute']);
var serverURL = "http://localhost:3030/mytest/query?query=";


adminApp.config(['$routeProvider',
    function ($routeProvider) {
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
            when('/Accredition', {
                templateUrl: 'pages/accreditionStatus.html',
                controller: 'AccredController'
            }).
            otherwise({
                redirectTo: '/index'
            });
    }]);

// load google visualization packages
google.load('visualization', '0', {'packages': ['geomap', 'corechart', 'geochart']});

adminApp.controller('DashBoardController', function ($scope) {

    $scope.message = 'This is Dashboard screen';

});

adminApp.controller('SurgeryController', ['$scope', '$http', function ($scope, $http) {

    $scope.choices = [
        {name: 'General Surgery', col: 1},
        {name: 'Cardiac Surgery', col: 2},
        {name: 'OrthoPedic Surgery', col: 3},
        {name: 'Vascular Surgery', col: 4},
        {name: 'Other Surgery', col: 5}
    ];

    $scope.change = function () {
        console.log("SurgeryController.change() entry");
        console.log($scope.userChoice);
        var url = serverURL + encodeURIComponent(query_surgeryTypes) + "&output=csv";
        console.log(url);
        $http.get(url).
            success(function (csvData, status, headers, config) {
                var data = $.csv.toArrays(csvData, {onParseValue: $.csv.hooks.castToScalar});
                var newData = setupNewData(data, "General Surgery Count", 1);

                var options = {};
                options['region'] = 'US';   // show US map
                options['dataMode'] = 'regions';
                options['width'] = '100%';
                options['height'] = 600;
                options['colors'] = [0xADEBAD, 0x5C85FF, 0xFF1919];

                var divId = document.getElementById('chart');
                var chart = new google.visualization.GeoMap(divId);
                chart.draw(newData, options );
            }).
            error(function (data, status, headers, config) {
                console.log("error in ajax call:SurgeryController.change()");
            });

        console.log("SurgeryController.change() exit");
    };

}]);

adminApp.controller('InfraController', ['$scope', '$http', function ($scope, $http) {
    var stateData = null;
    function prepareStateData(){
        console.log("InfraController.prepareStateData() entry");
        var url = serverURL + encodeURIComponent(query_loadInfraData) + "&output=csv";
        console.log(url);
        $http.get(url).
            success(function (csvData, status, headers, config) {
                stateData = $.csv.toArrays(csvData, {onParseValue: $.csv.hooks.castToScalar});
            }).
            error(function (data, status, headers, config) {
                console.log("error in ajax call:InfraController.prepareStateData()");
            });
        console.log("InfraController.prepareStateData() exit");
    }

    $scope.loadMap = function () {
        console.log("InfraController.loadMaps() entry");
        var url = serverURL + encodeURIComponent(query_emergBeds) + "&output=csv";
        console.log(url);
        prepareStateData();
        console.log(stateData);
        $http.get(url).
            success(function (csvData, status, headers, config) {
                var data = $.csv.toArrays(csvData, {onParseValue: $.csv.hooks.castToScalar});
                var newData = setupNewData(data, "Emerg. Beds", 1);

                var options = {};
                options['region'] = 'US';   // show US map
                options['dataMode'] = 'regions';
                options['width'] = '100%';
                options['height'] = 600;
                options['colors'] = [0xADEBAD, 0x5C85FF, 0xFF1919];

                var divElement = document.getElementById('geoChart');
                var geoChart = new google.visualization.GeoMap(divElement);

                google.visualization.events.addListener(geoChart, 'regionClick', function (eventData) {
                    var region = eventData.region.substr(3);
                    console.log(region);
                    $scope.regionName = region;
                    var newData = [];
                    var rows = stateData.length;
                    console.log(rows);
                    for (var i = 1; i < rows; i++ )
                    {
                        if(stateData[i][0] == region){
                            newData.push(stateData[i]);
                        }
                    }
                    console.log("Total records in "+region+" is "+newData.length);
                    console.log(newData);
                    var tableTemplate = " <table id='stateTable' class='table table-hover table-striped'>" +
                        "<tr> " +
                        "<th>City</th> " +
                        "<th>Facility Name</th> " +
                        "<th>Emerg. Rooms</th> " +
                        "<th>Speciality Services</th> " +
                        "<th>Intensive Care Unit</th> " +
                        "<th>Intensive Care Unit class</th> " +
                        "<th>Maternity Care</th> " +
                        "<th>CARF Accreditation</th> " +
                        "<th>Joint Commission Accreditation</th> " +
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
                            "<td>"+obj[8]+"</td>"+
                            "<td>"+obj[9]+"</td>"+
                            "</tr>"
                        tableTemplate+=row;
                    });
                    tableTemplate+="</table>"
                    $('#stateDiv').html(tableTemplate);
                });


                geoChart.draw(newData, options );
            }).
            error(function (data, status, headers, config) {
                console.log("error in ajax call:InfraController.loadMaps()");
            });

        console.log("InfraController.loadMaps() exit");
    };

    google.setOnLoadCallback($scope.loadMap);
    //$scope.loadMap();

}]);

adminApp.controller('AccredController', ['$scope', '$http', function ($scope, $http) {

    $scope.message = 'This is Accredition screen';

}]);
