var express = require('express');
const mongoclient = require('mongodb').MongoClient;

var app = express();
app.use(express.json());

mongoclient.connect("mongodb://127.0.0.1:27017",
	(err, client) => {db = client.db('local')})

app.get('/get', function (req, res) {
        res.send('Hello!');
})

app.post('/post/download', function (req, res) {
        db.collection("poster").find({poster_id: req.body.poster_id}).toArray(
		(err,result) => {
			//console.log(req.body);
			res.send(result[0]);
		})
})
/*
app.get('/get/downloadPoster', function (req, res) {
        res.download('/home/CPEN321/poster/1.jpg');
})
*/
app.get('/get/downloadPoster/:file', function (req, res, next) {
	var file = req.params.file

	res.sendFile('/home/CPEN321/poster/'+file);
})
/*
app.get('/get/findAll', function (req, res) {
        db.collection("chatroom").find().toArray((err,result) => {res.send(result);})
})
*/
app.get('/get/findAllChatByRoomNumber/:num&:time', function (req, res) {
        const num = Number(req.params.num)
	const time = Number(req.params.time)
	db.collection("chatroom").find({room_number: num, time: {$gt: time}}).toArray((err,result) => {res.send(result);})
})

app.post('/post/updateChat', function (req, res) {
	//console.log("POST received");
	db.collection("chatroom").insertOne(req.body, (err, result) => {res.send(req.body);})
})

app.get('/post/test', function (req, res) {
	//console.log("GET sent back");
	db.collection("chatroom").find({room_number: 1}).toArray((err,result) => {res.send(result);})
})

app.get('/get/recommandations', function (req, res) {
        db.collection("poster").find(req.body).toArray((err,result) => {res.send(result);})
})

app.get('/get/rec/:keys', function (req, res) {
	var url = require('url');
	var keys = req.params.keys
	var q = url.parse("http://13.90.58.142/get/rec/?"+keys, true);
	var qdata = q.query;
	var obj = {};
	obj[qdata.field] = qdata.value;
	//console.log(obj);
	db.collection("poster").find(obj).toArray(function(err, result){
              res.send(result);
        })
})

/*
app.put('/put/update', function (req, res) {
        //res.send(req.body.text);
        db.collection("chatroom").updateOne({"item" : "journal"}, req.body, (err, result) => {res.send("updated\n");})
})

app.delete('/delete/delete', function (req, res) {
        db.collection("chatroom").deleteOne(queryJson, (err,result) => {res.send(result);})
})
*/
var server = app.listen(8081, function () {
        var host = server.address().address
        var port = server.address().port

        console.log("Example app listening at http://%s:%s", host, port)
})
