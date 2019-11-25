var express = require("express");
const mongoclient = require("mongodb").MongoClient;
var db;
var app = express();
app.use(express.json());

//using database named as "local"
mongoclient.connect("mongodb://127.0.0.1:27017",
	(err, client) => {db = client.db("local");});

//return poster JSON object found by poster_id
app.post("/post/download", function (req, res) {
        db.collection("poster").find({poster_id: req.body.poster_id}).toArray(
		(err,result) => {
			res.send(result[0]);
		});
});

//return poster image file
app.get("/get/downloadPoster/:file", function (req, res, next) {
	var file = req.params.file;
	res.sendFile("/home/CPEN321/poster/"+file);
});

//return messages in same chatroom, but sent after "time"
app.get("/get/findAllChatByRoomNumber/:num&:time", function (req, res) {
        const num = Number(req.params.num);
	const time = Number(req.params.time);
	db.collection("chatroom").find({room_number: num, time: {$gt: time}}).toArray((err,result) => {res.send(result);});
});

//store new messages in database
app.post("/post/updateChat", function (req, res) {
	db.collection("chatroom").insertOne(req.body, (err, result) => {res.send(req.body);});
});

//store new user view history in database
app.post("/post/userViewHistory", function (req, res) {
        db.collection("history").insertOne(req.body, (err, result) => {res.send(req.body);});
});

//update "like" tag in history
app.put("/put/userLike", function (req, res) {
        db.collection("history").updateOne({poster_id:req.body.poster_id},{$set:{like:req.body.like}}, (err, result) => {res.send(req.body);});
});

var scores;
var maxscore;
function findMax(result) {
	scores = {"action" : 0, "romantic" : 0};
	//if(!Array.isArray(result)){return;}
	var i;
	for(i = 0; i < result.length; i++){
		if(result[parseInt(i,10)].like === 1){
			scores[result[parseInt(i,10)].movietype] += 2;
		}else{
			scores[result[parseInt(i,10)].movietype]++;
		}
	}

}

function findMaxScore() {
	maxscore = 0;
	var i;
	//if(!Array.isArray(scores)){return;}
	for(i = 0; i < scores.length; i++){
		if(scores[parseInt(i,10)]>scores[parseInt(maxscore,10)]){
			maxscore = i;
		}
	}
}

/* get recommandations
*  input: JSON tag "user_name"
*  return: JSON object array
*  This method will find all view history JSON object based on input "user_name". 
*  Then computes the number of history for each movie type. If "like" tag is 1, 
*  that history will be count twice to add weight of its type. The type with highest
*  count will be used to find movies in database.
*/
app.get("/get/recommandations", function (req, res) {
	db.collection("history").find(req.body).toArray((err,result) => {
		try{
			findMax(result);
		}catch(error){
			res.send(err);
		}
	});
	db.collection("poster").find({"movietype":maxscore}).toArray((err,result) => {
		res.send(result);
	});
});

//Temporary method for getting recommandations
app.get("/get/rec/:keys", function (req, res) {
	var url = require("url");
	var keys = req.params.keys;
	var q = url.parse("http://13.90.58.142/get/rec/?"+keys, true);
	var qdata = q.query;
	var obj = {};
	obj[qdata.field] = qdata.value;
	db.collection("poster").find(obj).toArray(function(err, result){
              res.send(result);
        });
});

var server = app.listen(8081, function () {
        var host = server.address().address;
        var port = server.address().port;
        
        //console.log("Example app listening at http://%s:%s", host, port)
});
