/**
 * Created by Subbu on 7/23/15.
 */

(function (angular) {

    var module = angular.module("appControllers");

    module.controller("dashBoardController", [
            '$scope',
            'FirebaseService',            
            function ($scope, FirebaseService) {                                
                console.log("dashboard");
                $scope.errorData = FirebaseService.errorNWDataCount;
                $scope.totalData = FirebaseService.totalNWDataCount;
                console.log("ErrorData", FirebaseService.errorNWDataCount);
                console.log("TotalData", FirebaseService.totalNWDataCount);
                console.log("percent: ", (FirebaseService.errorNWDataCount/FirebaseService.totalNWDataCount));
                $scope.myCircle = Circles.create({
                  id:                  'circles-1',
                  radius:              150,
                  value:               (FirebaseService.errorNWDataCount/FirebaseService.totalNWDataCount)*100,
                  maxValue:            100,
                  width:               30,
                  text:                function(value){return value + '%';},
                  colors:              ['#D3B6C6', '#4B253A'],
                  duration:            800,
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