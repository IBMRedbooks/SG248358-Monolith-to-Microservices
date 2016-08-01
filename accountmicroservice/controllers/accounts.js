var ibmdb = require('ibm_db') 
	, async = require('async')
	, http = require('http')
	, config = require('../config/config')
	, me = config.cloudant.username // Set this to your own accou
  	, password = config.cloudant.password
  	, base = config.cloudant.db
  	, cloudantUrl = 'http://' + me + ':' + password + '@' + me + '.cloudant.com/' + base
	;

var getCloudantData = function(userid, callback) {
	http.get(cloudantUrl + '/_design/' + base + '/_view/all?key="' + userid + '"', 
		function(response) {
	        // Continuously update stream with data
	        var body = '';
	        response.on('data', function(d) {
	            body += d;
	        });
	        response.on('end', function() {
	        	//console.log('Body: ', body);
	            // Data reception is done, do whatever with it!
	            if (response.statusCode == 200) {
	            	var parsed = JSON.parse(body);
		            if (parsed.rows.length == 0){
		            	callback(null, {fbData : {}, twData : {}});
		            } else {
		            	callback(null, parsed.rows[0].value);
		            }
	            } else {
	            	callback({"message" : JSON.parse(body)}, null);
	            }
	        });
	        response.on('error', function(err){
	        	console.log('Error: ', err);
	        	callback(err, null);
	        });
		}).end();
}

var getDB2Data = function(userid, callback){
	ibmdb.open("DRIVER={DB2};DATABASE=" + config.db2.database + ";HOSTNAME=" + config.db2.hostname + ";UID=" + config.db2.uid + ";PWD=" + config.db2.password + ";PORT=" + config.db2.port + ";PROTOCOL=TCPIP", function (err,conn) {
			if (err) {
				console.log("Error connecting to DB2:::", err);
				return callback(err, null);
			}

	  	conn.query('select * from customer where username = \'' + userid + '\'', function (err, data) {
	    	if (err) {
  				console.log("Error executing select:::", err);
  				return callback(err, null);
  			}
	    	else {
	    		//console.log(data);
	    		if (data.length == 0) {
	    			return callback(null, false);
	    		} else {
	    			return callback(null, data[0]);
	    		}
	    	}
	 
	    	conn.close(function () {
	      		//console.log('done');
	    	});
	  	});
	});
}

module.exports = {
	getUser: function (req, res) {
		async.parallel([function(callback){getCloudantData(req.params.userid,callback);},function(callback){getDB2Data(req.params.userid,callback);}], function(err, result){
			if (err) {
  				console.log("Error finding user information", err);
  				res.status(500).json({
	        		"message": "There was an error getting the information ::: ",
	        		"error" : err
	        	});
  			} else {
  				if (result[1]){
  					var userData = {
						customerData : result[1],
						socialNetworkOneData : result[0].twData,
						socialNetworkTwoData : result[0].fbData
					};

					res.status(200).json(userData);
  				} else {
  					res.status(400).json({"message":"The user was not found"});
  				}
  			}
		});
	}
}