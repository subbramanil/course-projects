var express = require('express');
var app = express();
var http = require('http').Server(app);

app.set('views',__dirname + '/views');
app.set('view engine', 'ejs');
app.engine('html', require('ejs').renderFile);
var oneDay = 86400000;
app.use(express.static(__dirname + '/static',{maxAge: oneDay}));

require('./router')(app);

var server = http.listen(process.env.PORT || 3032, function(){
	console.log("Server is ready at port "+server.address().port);
});
