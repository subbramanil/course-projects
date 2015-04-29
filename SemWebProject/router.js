module.exports = function(app, cors)
{
	var corsOptions = {
  		origin: 'http://localhost:3030'
	};	
	app.get('/',function(req,res){
		console.log('server is running');
		res.redirect('/home');
	});

	app.get('/home', cors(corsOptions), function(req, res){
		console.log("Loading Home Page..");
		res.render('index.html');
	});

	app.get('/views/dashboard', function(req, res){
		console.log("Loading Dashboard Page..");
		res.render('dashboard.html');
	});

	app.get('/views/surgeryTypes', function(req, res){
		console.log("Loading surgery Page..");
		res.render('surgeryTypes.html');
	});

	app.get('/views/infrastructure', function(req, res){
		console.log("Loading infrastructure Page..");
		res.render('infrastructure.html');
	});

	app.get('/views/patients', function(req, res){
		console.log("Loading patients Page..");
		res.render('patient.html');
	});

	app.get('/views/aboutUs', function(req, res){
		console.log("Loading aboutUs Page..");
		res.render('aboutUs.html');
	});
}
