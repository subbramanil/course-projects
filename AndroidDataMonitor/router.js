/**
 * Created by Subbu on 7/20/15.
 */

module.exports = function (app) {

    app.get("/", function (req, res) {
        console.log("application loaded");
        res.redirect("/home");
    });

    app.get("/home", function (req, res) {
        console.log("loading home page");
        res.render("index.html");
    });
};