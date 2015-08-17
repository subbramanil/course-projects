(function (angular) {
    var module = angular.module("appControllers");

    module.controller("pathController", [
            "$scope",
            "FirebaseService",            
            function ($scope, FirebaseService) {
                console.log("User Path", FirebaseService.selectedPath.packetStaticData);
                $scope.selectedPath = FirebaseService.selectedPath;
                
                $scope.dataLost = 0;
                angular.forEach($scope.selectedPath.packetLossList, function(val, key){
                    if(val>0){
                        console.log(key+ " : "+ val);
                        $scope.dataLost +=val;   
                    }
                });  
                
                $scope.myCircle = Circles.create({
                  id:                  'circles-2',
                  radius:              75,
                  value:               ($scope.dataLost/200)*100,
                  maxValue:            100,
                  width:               30,
                  text:                function(value){return value + '%';},
                  colors:              ['#D3B6C6', '#4B253A'],
                  duration:            500,
                  wrpClass:            'circles-wrp',
                  textClass:           'circles-text',
                  valueStrokeClass:    'circles-valueStroke',
                  maxValueStrokeClass: 'circles-maxValueStroke',
                  styleWrapper:        true,
                  styleText:           true
                });                               
            }
        ]
    );
})(angular);