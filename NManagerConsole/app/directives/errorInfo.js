/**
 * Created by Subbu on 7/22/15.
 */

(function (angular) {
    var module = angular.module("appDirectives");

    module.directive("errorInfo", [
        '$log',
        "FirebaseService",
        function (log, FirebaseService) {
            return {
                templateUrl: "./app/partials/nwInfo.html",
                restrict: "E",
                scope: {
                    nwInfo:"="
                },
                controller: [
                    "$scope",
                    function ($scope) {
                        console.log("nw info!!!");
                        $scope.tableData = [];                       
                        $scope.$watch(function () {
                            return FirebaseService.errorNWData;
                        }, function () {
                            $scope.tableData = FirebaseService.errorNWData;                            
                            log.info("Error data:.. ", $scope.errorTableData);
                        });
                    }
                ]
            }
        }]);

})(angular);
