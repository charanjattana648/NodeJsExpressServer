// filename : serverDB.js
// install  : npm install mysql

var fs = require("fs");
var express = require('express')
var mymodule = require("./nodeDB2");
var bodyParser = require('body-parser');


var app = express();
var port = 8000;

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(express.static(__dirname));

var server = app.listen(port, () => {
    console.log('server is listening on port', server.address().port)
})

app.post('/', function (req, res) {
    mymodule.readDB(req, res);
});



app.get('/', (req, res) =>{
    res.send('Not implemented');
})