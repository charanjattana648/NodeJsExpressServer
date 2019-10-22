// filename: nodeDB1.js

var mysql = require('mysql');

function readDBData(request,response) {

    console.log(request.body.name);
    console.log(request.body.pwd);
    
    var connection = mysql.createPool({
        connectionLimit: 5,
        host     : 'ec2-54-163-223-98.compute-1.amazonaws.com',
        user     : request.body.name,
        password : request.body.pwd,
        database : request.body.db
    });

    if(request.body.type=="login")
    {
        let qString = "SELECT 1 from EMPLOYEE"  
    connection.query(qString, function(err, rows, fields) {
    if (!err) {
       response.send("Welcome");
    }
    else
       response.send("0");
    });
        
    }else{
    connection.query(request.body.query, function(err, rows, fields) {
    if (!err) {

        var data ;
        var max = 5;
        let v = "" ;
        for (i in rows) {
            v += "<tr>";
            v += "<td>" + rows[i]["employeenumber"] + "</td>";
            v += "<td>" + rows[i]["firstname"] + "</td>";
            v += "<td>" + rows[i]["lastname"] + "</td>";
            v += "</tr>";
        } 
        if(request.body.type=="EmployeeSearch")
        {
           response.send(JSON.stringify(rows)); 
        }else{
            response.send(JSON.stringify(v));
        }
        
        
    }
    else
    {
        console.log('Error while performing Query.');
        
    }
    });}

    function formatDate(theDate) {
        return theDate.getFullYear() + "-" + (theDate.getMonth() + 1) + "-" + theDate.getDate();
    }


}

exports.readDB = readDBData

