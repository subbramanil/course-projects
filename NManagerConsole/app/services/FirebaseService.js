/**
 * Created by Subbu on 7/22/15.
 */

(function (angular) {
    var module = angular.module("appServices");

    module.service("FirebaseService", [
            '$firebaseObject',
            "$log",
            function ($firebaseObject, log) {
                var service = {};
                var ref = new Firebase("https://sweltering-heat-1721.firebaseio.com");
                service.user = {};
                
                service.authUser = function(callback){                  
                    ref.authWithOAuthPopup("google", function(error, authData) {
                      if (error) {
                        console.log("Login Failed!", error);
                        return callback(error, null);
                      } else {
                        console.log("Authenticated successfully with payload:", authData);
                        service.user = authData.google;
                        ref.set({"user":authData});
                        return callback(null, authData.google);
                      }
                    });  
                };
                
                service.logoutUser = function(callback){
                    
                }
                
                service.selectedPath = {};
                service.getTotalNWData = function () {
                    service.totalNWData = $firebaseObject(ref.child('TotalNWDataList'));
                    service.user = $firebaseObject(ref.child('user'));
                    service.staticData = {};
                    service.totalNWDataCount = 0;
                    service.errorNWDataCount = 0;
                    // this waits for the data to load and then logs the output. Therefore,
                    // data from the server will now appear in the logged output. Use this with care!
                    service.totalNWData .$loaded()
                        .then(function () {
                            service.staticData = service.totalNWData[0]; 
                            console.log("Total: ", service.totalNWData.length);
                            log.debug("service: ",service.staticData);
                            
                            angular.forEach(service.totalNWData, function(value, key){
                                value.packetStaticData.isFlag = "check";                                
                                service.totalNWDataCount++;
                                angular.forEach(value.packetLossList, function(val, k){
                                    if(val>0){
                                        value.packetStaticData.isFlag = "report";
                                    }
                                });
                            });
                            console.log("total NW data size:"+service.totalNWDataCount);
                        })
                        .catch(function (err) {
                            log.error(err);
                        });
                };
                
                service.getErrorNWData = function () {
                    service.errorNWData = $firebaseObject(ref.child('ErrorNWDataList'));                    
                    // this waits for the data to load and then logs the output. Therefore,
                    // data from the server will now appear in the logged output. Use this with care!
                    service.errorNWData .$loaded()
                        .then(function () {                             
                            log.debug("service: ",service.errorNWData[0]);
                            console.log("Error: ", service.errorNWData);
                            angular.forEach(service.errorNWData, function(value, key){
                                service.errorNWDataCount++;                             
                                value.packetStaticData.isFlag = "report";                                
                            });
                            log.info("Error Data Count", service.errorNWDataCount);
                        })
                        .catch(function (err) {
                            log.error(err);
                        });
                };

                // service.getUserInfo();
                service.getTotalNWData();
                service.getErrorNWData();
                                    
                return service;
            }
        ]
    );
})(angular);
