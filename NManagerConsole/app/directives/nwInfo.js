/**
 * Created by Subbu on 7/22/15.
 */

(function (angular) {
    var module = angular.module("appDirectives");

    module.directive("networkInfo", [
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
                        // $scope.errorTableData = [];
                        $scope.$watch(function () {
                            return FirebaseService.totalNWData;
                        }, function () {
                            $scope.tableData = FirebaseService.totalNWData;                                                        
                        });
                        
                        $scope.viewPath = function (pathData) {
                            console.log("NWinfo Get this path "+pathData.srcIP);
                            FirebaseService.selectedPath = pathData; 
                            console.log("fire base service: ", FirebaseService.selectedPath);                                              
                        }; 
                        
                    }
                ]
            }
        }]);

})(angular);
