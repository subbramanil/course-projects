(function (angular) {

    var module = angular.module("appControllers");

    module.controller("DummyController", [
    "$scope",
    "$http",
    'FirebaseService',
    function($scope, $http, FirebaseService) {
      $scope.totalCount = FirebaseService.totalNWDataCount;
      $scope.errorCount = FirebaseService.errorNWDataCount;
      

    }
  ]);
}(angular));