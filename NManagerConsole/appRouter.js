/**
 * Created by Subbu on 7/6/15.
 */

module.exports = function (app) {

    app.get("/", function (req, res) {
        console.log("server is running successfully");
        res.redirect("home");
    });

    app.get("/home", function (req, res) {
        console.log();
        // res.render("login.html");
        res.render("index.html");
    });
    
    app.get("/index", function (req, res) {
        console.log("loading index page");
        res.render("index.html");
    });    

    app.get("/app/partials/dashBoard", function (req, res) {
        console.log("Loading dashBoard");
        res.render("dashBoard.html");
    });
    
    app.get("/app/partials/nwDetails", function (req, res) {
        console.log("Loading nwDetails");
        res.render("nwDetails.html");
    });
    
    app.get("/app/partials/errorDetails", function (req, res) {
        console.log("Loading errorDetails");
        res.render("errorDetails.html");
    });
    
    app.get("/app/partials/dataTable", function (req, res) {
        console.log("Loading dataTable");
        res.render("dataTable.html");
    });
    
    app.get("/app/partials/row", function (req, res) {
        console.log("Loading row");
        res.render("row.html");
    });
    
    app.get("/app/partials/completeDetails", function (req, res) {
        console.log("Loading path data");
        res.render("completeDetails.html");
    });

};