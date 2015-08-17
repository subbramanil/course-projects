/**
 * Created by Subbu on 7/23/15.
 */

(function (angular) {

    var module = angular.module("appControllers");

    module.controller("FirebaseController", [
            '$scope',
            "$http",
            'FirebaseService',
            function ($scope, $http, FirebaseService) {
                console.log("Firebase Controller"); 
                $scope.userInfo = FirebaseService.user;          
                $scope.authenticateUser = function(){
                    console.log("Auth. user");      
                    FirebaseService.authUser(function(err, data){
                        console.log(err);
                        if(!err){
                            $scope.$apply(function(){
                                $scope.userInfo = data;
                            });
                        }                                                    
                        console.log("user", $scope.userInfo);                            
                    });                                      
                    console.log("user 2", $scope.userInfo);  
                };                               
                                
            }
        ]
    );
})(angular);