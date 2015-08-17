/**
 * Created by Subbu on 7/22/15.
 */

(function (angular) {
    var myApp = angular.module("fireApp", [
        "ngRoute",
        "appServices",
        "appControllers",
        "appDirectives"        
    ]);

    myApp.config(
        [
            "$routeProvider",
            function ($routeProvider) {
                $routeProvider
                    .when("/", {
                        templateUrl: "./app/partials/nwDetails",                        
                    })
                    .when('/errorDetails', {
                        templateUrl: "./app/partials/errorDetails"
                    })
                    .when('/nwDetails', {
                        templateUrl: "./app/partials/nwDetails"
                    })
                    .when("/pathInfo", {
                        templateUrl: "./app/partials/completeDetails"                        
                    })
                    .when("/dashBoard", {
                        templateUrl: "./app/partials/dashBoard"                        
                    })
                    .when("/index", {
                        templateUrl: "./app/partials/index"                        
                    })
                    .otherwise("/");
            }
        ]
    );

})(angular);