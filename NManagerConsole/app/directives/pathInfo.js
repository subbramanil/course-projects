/**
 * Created by Subbu on 7/22/15.
 */

(function (angular) {
    var module = angular.module("appDirectives");

    module.directive("pathInfo", [
        '$log',
        "FirebaseService",
        function (log, FirebaseService) {
            return {
                templateUrl: "./app/partials/pathInfo.html",
                restrict: "E",
                scope: {
                    pathData: "=data"
                },
                controller: [
                    "$scope",
                    function ($scope) {
                        FirebaseService.selectedPath = $scope.pathData;
                        $scope.viewPath = function (pathData) {
                            console.log("PathInfo Get this path "+pathData.srcIP);
                            FirebaseService.selectedPath = pathData; 
                            console.log("fire base service: ", FirebaseService.selectedPath);                                              
                        };                      
                    }
                ]
            }
        }]);

})(angular);
