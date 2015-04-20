var adminApp = angular.module('adminApp', ['ngRoute']);
var serverURL = "http://localhost:3030/vethealth/query?query=";

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
            when('/Patients', {
                templateUrl: 'pages/patient.html',
                controller: 'PatientController'
            }).
            otherwise({
                redirectTo: '/Dashboard'
            });
    }]);

// load google visualization packages
google.load('visualization', '0', {'packages': ['geomap', 'corechart', 'geochart']});

adminApp.controller('DashBoardController', function ($scope) {

    $scope.tabClick = function ($event) {
        $event.preventDefault();
    };

});

adminApp.controller('SurgeryController', ['$scope', '$http', function ($scope, $http) {

    $scope.choices = [
        {name: 'General Surgery', col: 1},
        {name: 'Cardiac Surgery', col: 2},
        {name: 'OrthoPedic Surgery', col: 3},
        {name: 'Vascular Surgery', col: 4},
        {name: 'Other Surgery', col: 5}
    ];
    $scope.userChoice = $scope.choices[0];

    var surgeryData = null;
    function prepareSurgeryData(){
        console.log("SurgeryController.prepareSurgeryData() entry");
        var url = serverURL + encodeURIComponent(query_surgeryTypesByState) + "&output=csv";
        console.log(url);
        $http.get(url).
            success(function (csvData, status, headers, config) {
                surgeryData = $.csv.toArrays(csvData, {onParseValue: $.csv.hooks.castToScalar});
                for(var i=0;i<surgeryData.length;i++){
                    for(var j=0;j<surgeryData[i].length;j++){
                        if(surgeryData[i][j]==null){
                            surgeryData[i][j] = 0;
                        }
                    }
                }
            }).
            error(function (data, status, headers, config) {
                console.log("error in ajax call:SurgeryController.prepareStateData()");
            });
        console.log("SurgeryController.prepareSurgeryData() exit");
    }

    $scope.change = function () {
        console.log("SurgeryController.change() entry");
        console.log($scope.userChoice);
        prepareSurgeryData();
        var url = serverURL + encodeURIComponent(query_surgeryTypes) + "&output=csv";
        console.log(url);
        $http.get(url).
            success(function (csvData, status, headers, config) {
                var data = $.csv.toArrays(csvData, {onParseValue: $.csv.hooks.castToScalar});
                console.log(data);
                for(var i=0;i<data.length;i++){
                    for(var j=0;j<data[i].length;j++){
                        if(data[i][j]==null){
                            data[i][j] = 0;
                        }
                    }
                }
                console.log($scope.userChoice);
                var newData = setupNewData(data, $scope.userChoice.name, $scope.userChoice.col);

                var options = {};
                options['region'] = 'US';   // show US map
                options['dataMode'] = 'regions';
                options['width'] = '100%';
                options['height'] = 600;
                options['colors'] = [0xADEBAD, 0x5C85FF, 0xFF1919];

                var geoChart = new google.visualization.GeoMap(document.getElementById('geoChart'));
                google.visualization.events.addListener(geoChart , 'regionClick', function (eventData) {
                    var region = eventData.region.substr(3);
                    console.log(region);
                    $scope.regionName = region;

                    var cityData = [];
                    var rows = surgeryData.length;
                    console.log(rows);
                    for (var i = 1; i < rows; i++ )
                    {
                        if(surgeryData[i][0] == region){
                            cityData.push(surgeryData[i]);
                        }
                    }
                    console.log("No of Cities with Surgery Count:"+cityData.length);
                    console.log(cityData);

                    var chartData = [];
                    var barChartHeader = ['City', '# of Surgery'];
                    chartData.push(barChartHeader);
                    for(var i=0;i<cityData.length;i++){
                        var val = cityData[i][$scope.userChoice.col+1];
                        if(val>0){
                            chartData.push([cityData[i][1], val]);
                        }
                    }
                    console.log(chartData);
                    if(chartData.length>1){
                        $scope.noData = false;
                        var data = google.visualization.arrayToDataTable(chartData);
                        console.log(data);

                        var options = {
                            title: $scope.userChoice.name,
                            chartArea: {width: '100%'},

                            hAxis: {
                                title: '# of Surgery',
                                minValue: 0
                            },
                            vAxis: {
                                title: 'City'
                            }
                        };
                        var barChart = new google.visualization.BarChart(document.getElementById('barChart'));
                        barChart.draw(data, options);
                    }else{
                        $scope.noData = true;
                    }

                });

                geoChart.draw(newData, options);
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

    google.setOnLoadCallback($scope.loadMap());
}]);

adminApp.controller('AccredController', ['$scope', '$http', function ($scope, $http) {

    $scope.message = 'This is Accredition screen';

}]);

adminApp.controller('PatientController', ['$scope', '$http', function ($scope, $http) {

    $scope.tabClick = function ($event) {
        $event.preventDefault();
    };

    var patientsTypeData = null;

    function preparePatientsTypeData(){
        console.log("PatientController.preparePatientsTypeData() entry");
        var url = serverURL + encodeURIComponent(query_loadPatientsByType) + "&output=csv";
        console.log(url);
        $http.get(url).
            success(function (csvData, status, headers, config) {
                patientsTypeData = $.csv.toArrays(csvData, {onParseValue: $.csv.hooks.castToScalar});
                console.log(patientsTypeData);
                for(var i=0;i<patientsTypeData.length;i++){
                    for(var j=0;j<patientsTypeData[i].length;j++){
                        if(patientsTypeData[i][j]==null){
                            patientsTypeData[i][j] = 0;
                        }
                    }
                }
            }).
            error(function (data, status, headers, config) {
                console.log("error in ajax call:PatientController.preparePatientsTypeData()");
            });
        console.log("PatientController.preparePatientsTypeData() exit");
    }

    $scope.drawGeoMap = function () {
        console.log("PatientController.drawGeoMap() entry");
        preparePatientsTypeData();
        var url = serverURL + encodeURIComponent(query_loadPatientsByRace) + "&output=csv";
        console.log(url);
        $http.get(url).
            success(function (csvData, status, headers, config) {
                var patientsData = $.csv.toArrays(csvData, {onParseValue: $.csv.hooks.castToScalar});
                console.log(patientsData);
                var newData = [];
                var rows = patientsData.length;
                newData.push(["State", "In Patients Count", "Out Patients Count"]);
                for (var i = 1; i < rows; i++ )
                {
                    newData.push([patientsData[i][0], (patientsData[i][1]+patientsData[i][3]+patientsData[i][5]), (patientsData[i][2]+patientsData[i][4]+patientsData[i][6])])
                }
                console.log("Total records: "+newData.length);
                console.log(newData);
                var inPatientsData = setupNewData(newData , "Total In Patients", 1);
                var outPatientsData = setupNewData(newData , "Total Out Patients", 2);

                var options = {};
                options['region'] = 'US';   // show US map
                options['dataMode'] = 'regions';
                options['width'] = '100%';
                options['height'] = 600;
                options['colors'] = [0xADEBAD, 0x5C85FF, 0xFF1919];
                var geoInChart = new google.visualization.GeoMap(document.getElementById('geoInChart'));
                geoInChart .draw(inPatientsData, options );

                google.visualization.events.addListener(geoInChart , 'regionClick', function (eventData) {
                    var region = eventData.region.substr(3);
                    console.log(region);
                    $scope.regionName = region;
                    var pieData = [];
                    console.log(patientsData);
                    var rows = patientsData.length;
                    for (var i = 1; i < rows; i++) {
                        if (patientsData[i][0] == region) {
                            pieData.push(patientsData[i]);
                        }
                    }
                    console.log(pieData);

                    var data = google.visualization.arrayToDataTable([
                        ['Race', 'Count'],
                        ['African American', pieData[0][1]],
                        ['White', pieData[0][3]],
                        ['Other', pieData[0][5]]
                    ]);

                    var options = {
                        title: 'Inpatients'
                    };

                    var pieChart = new google.visualization.PieChart(document.getElementById('pieInChart'));
                    pieChart.draw(data, options);

                    var barData = [];
                    console.log(patientsTypeData);
                    var rows = patientsTypeData.length;
                    for (var i = 1; i < rows; i++) {
                        if (patientsData[i][0] == region) {
                            barData.push(patientsTypeData[i]);
                        }
                    }
                    console.log(barData);

                    var chartData = [];
                    var barChartHeader = ['Patient Type', '# of patients'];
                    chartData.push(barChartHeader);
                    for(var i=0;i<barData.length;i++){
                        var val1 = barData[i][1];
                        var val2 = barData[i][2];
                        chartData.push(["Disabled inpatient", val1]);
                        chartData.push(["Geriatric 65 year old inpatient", val2]);
                    }
                    console.log(chartData);
                    var data = google.visualization.arrayToDataTable(chartData);
                    console.log(data);

                    var options = {
                        title: "Patients Type",
                        chartArea: {width: '100%'},
                        hAxis: {
                            title: '# of Surgery',
                            minValue: 0
                        },
                        vAxis: {
                            title: 'Patients Type'
                        }
                    };
                    var barChart = new google.visualization.BarChart(document.getElementById('inPatientTypeBarChart'));
                    barChart.draw(data, options);

                });


                var geoOutChart = new google.visualization.GeoMap(document.getElementById('geoOutChart'));
                geoOutChart .draw(outPatientsData, options );

                google.visualization.events.addListener(geoOutChart , 'regionClick', function (eventData) {
                    var region = eventData.region.substr(3);
                    console.log(region);
                    $scope.regionName = region;
                    var pieData = [];
                    console.log(patientsData);
                    var rows = patientsData.length;
                    for (var i = 1; i < rows; i++) {
                        if (patientsData[i][0] == region) {
                            pieData.push(patientsData[i]);
                        }
                    }
                    console.log(pieData);

                    var data = google.visualization.arrayToDataTable([
                        ['Race', 'Count'],
                        ['African American', pieData[0][2]],
                        ['White', pieData[0][4]],
                        ['Other', pieData[0][6]]
                    ]);

                    var options = {
                        title: 'Outpatients'
                    };

                    var chart = new google.visualization.PieChart(document.getElementById('pieOutChart'));
                    chart.draw(data, options);

                    var barData = [];
                    console.log(patientsTypeData);
                    var rows = patientsTypeData.length;
                    for (var i = 1; i < rows; i++) {
                        if (patientsData[i][0] == region) {
                            barData.push(patientsTypeData[i]);
                        }
                    }
                    console.log(barData);

                    var chartData = [];
                    var barChartHeader = ['Patient Type', '# of patients'];
                    chartData.push(barChartHeader);
                    for(var i=0;i<barData.length;i++){
                        chartData.push(["Disabled outpatient", barData[i][3]]);
                        chartData.push(["Geriatric 65 year old outpatient", barData[i][4]]);
                        chartData.push(["Homeless outpatient", barData[i][5]]);
                        chartData.push(["Mental Health outpatient", barData[i][6]]);
                        chartData.push(["Non Mental Health outpatient", barData[i][7]]);
                    }
                    console.log(chartData);
                    var data = google.visualization.arrayToDataTable(chartData);
                    console.log(data);

                    var options = {
                        title: "Patients Type",
                        chartArea: {width: '100%'},
                        hAxis: {
                            title: '# of Surgery',
                            minValue: 0
                        },
                        vAxis: {
                            title: 'Patients Type'
                        }
                    };
                    var barChart = new google.visualization.BarChart(document.getElementById('outPatientTypeBarChart'));
                    barChart.draw(data, options);
                });

            }).
            error(function (data, status, headers, config) {
                console.log("error in ajax call:PatientController.drawGeoMap()");
            });

        console.log("PatientController.drawGeoMap() exit");
    };
    google.setOnLoadCallback($scope.drawGeoMap());
}]);
