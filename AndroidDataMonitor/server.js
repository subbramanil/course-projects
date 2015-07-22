/**
 * Created by Subbu on 7/20/15.
 */

var express = require("express");
var app = express();
var http = require('http').Server(app);

app.set('views',__dirname + '/app/partials');
app.set('view engine', 'ejs');
app.engine('html', require('ejs').renderFile);

var oneDay = 86400000;
//app.use("/static",express.static(__dirname + '/static',{maxAge: oneDay}));
app.use("/lib",express.static(__dirname + '/lib',{maxAge: oneDay}));
app.use("/app",express.static(__dirname + '/app',{maxAge: oneDay}));

require("./router")(app);

var server = http.listen(process.env.PORT || 8080,function(){
    console.log("Server listening on port "+server.address().port);
});