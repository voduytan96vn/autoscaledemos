var express = require('express'),
    fs = require('fs'),
    app = express(),
    eps = require('ejs'),
    morgan = require('morgan');

var app = express();
var mysql = require('mysql');
var str = "";

var ip = process.env.IP || process.env.OPENSHIFT_NODEJS_IP || '0.0.0.0';
//mongodb configuration
var mongoHost = process.env.MONGODB_SERVICE_HOST || 'localhost';
var mongoPort = process.env.MONGODB_SERVICE_PORT || 27017;
var mongoUser = process.env.MONGODB_USER;
var mongoPass = process.env.MONGODB_PASSWORD;
var mongoDb = process.env.MONGODB_DATABASE;

//mysql configuration
var mysqlHost = process.env.MYSQL_SERVICE_HOST || 'localhost';
var mysqlPort = process.env.MYSQL_SERVICE_PORT || 3306;
var mysqlUser = process.env.MYSQL_USER;
var mysqlPass = process.env.MYSQL_PASSWORD;
var mysqlDb = process.env.MYSQL_PASSWORD;

//connection strings
var mongoString = 'mongodb://' + mongoUser + ':' + mongoPass + '@' + mongoHost + ':' + mongoPort + '/' + mongoDb;
var mysqlString = 'mysql://' + mysqlUser + ':' + mysqlPass + '@' + mysqlHost + ':' + mysqlPort + '/' + mysqlDb;



//connect to mongo
var mongodb = require('mongodb');

// mongodb.connect(mongoString, function(err, conn) {
//    if (err) console.log(err);
//});




//connect to mysql
var mysqlClient = mysql.createConnection(mysqlString);
mysqlClient.connect(function(err){
  if (err) console.log(err);
});


// app is running!
app.get('/', function(req, res) {
    res.send('Hello from NodeJS!!! ');
});


// MongoDB is running!
app.get('/mongo', function(req, res) {

    mongodb.connect(mongoString, function(err, db) {
        db.collection('Employee').insertOne({
            Employeeid: Math.random().toString(36).substring(7),
            EmployeeName: "NewEmployee"
        });
        var cursor = db.collection('Employee').find();

        cursor.each(function(err, item) {

            if (item != null) {
                str = str + "    Employee id  " + item.Employeeid + "</br>";
            }
        });
        res.send(str);

    });

});


//MySQL is running!
app.get('/mysql', function(req, res) {
    mysqlClient.query('SELECT 1 + 1 AS solution', function(err, rows, fields) {
        if (err) {
            res.send('NOT OK' + JSON.stringify(err));
        } else {
            res.send('OK MYSQL! ' + rows[0].solution);
        }
    });
});


app.listen(8080, ip);

console.log('MongoDB running at ' + mongoString);
console.log('MySQL running at ' + mysqlString);


module.exports = app;
