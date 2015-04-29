var express = require('express');
var app = express();
var http = require('http').Server(app);
var cors = require('cors');

app.set('views',__dirname + '/views');
app.set('view engine', 'ejs');
app.engine('html', require('ejs').renderFile);
var oneDay = 86400000;
app.use(express.static(__dirname + '/static',{maxAge: oneDay}));

require('./router')(app, cors);

var server = http.listen(process.env.PORT || 8080, function(){
	console.log("Server is ready at port "+server.address().port);
});
