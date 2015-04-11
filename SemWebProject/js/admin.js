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
			otherwise({
				redirectTo: '/index'
			});
	}]);

adminApp.controller('DashBoardController', function($scope) {

	$scope.message = 'This is Dashboard screen';

});


adminApp.controller('SurgeryController', ['$scope', function($scope, $http){

	$scope.message = 'This is Surgery screen';

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
        return arrayData ;
    }

    function setupNewData(arrayData, columnName, colNum){
        console.log("setupNewData() entry");
        var data = new google.visualization.arrayToDataTable(arrayData);
        var newData = new google.visualization.DataTable();

        var formatter = new google.visualization.NumberFormat(
            {pattern: '###,###'});
        formatter.format(data, colNum);

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