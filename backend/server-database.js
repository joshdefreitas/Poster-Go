var express = require('express');
const mongoclient = require('mongodb').MongoClient

var app = express();
app.use(express.json());

mongoclient.connect("mongodb://127.0.0.1:27017",
        (err, client) => {db = client.db('local')})

app.get('/get', function (req, res) {
        res.send('Hello World! GET\n');
})

app.get('/get/find', function (req, res) {
        db.collection("list").find().toArray((err,result) => {res.send(result);})
})

app.post('/post/create', function (req, res) {
        //res.send(req.body.text);
        db.collection("list").insertOne(req.body, (err, result) => {res.send("saved\n");})
})

app.post('/post/update', function (req, res) {
        res.send(req.body.text);
        db.collection("list").updateOne(queryJSON, {$set:UPdateJSON}, (err, result) => {res.send("updated\n");})
})

var server = app.listen(8081, function () {
        var host = server.address().address
        var port = server.address().port

        console.log("Example app listening at http://%s:%s", host, port)
})
