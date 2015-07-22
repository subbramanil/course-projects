/**
 * Created by Subbu on 7/20/15.
 */

(function (angular) {
    var myApp = angular.module("fireApp", [
        /*"fireServices",
        "fireControllers",
        "fireDirectives",*/
        "firebase"
    ]);

    myApp.controller("SampleCtrl", function($scope, $firebaseObject, $firebaseAuth) {
        var ref = new Firebase("https://sweltering-heat-1721.firebaseio.com");

        // download the data into a local object
        var syncObject = $firebaseObject(ref);

        // putting a console.log here won't work, see below
        console.log(syncObject);

        syncObject.$bindTo($scope, "data");

        /*// create an instance of the authentication service
        var auth = $firebaseAuth(ref);
        // login with Facebook
        auth.$authWithOAuthPopup("facebook").then(function(authData) {
            console.log("Logged in as:", authData.uid);
        }).catch(function(error) {
            console.log("Authentication failed:", error);
        });*/
    });
})(angular);