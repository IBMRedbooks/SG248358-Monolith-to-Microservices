var https = require('https')
	, querystring = require('querystring')
	, elasticsearch = require('elasticsearch')
	, config = require('../config/config')
	;

var client = elasticsearch.Client({
	hosts: [   
		'https://' + config.elastic.user + ':' + config.elastic.password + '@' + config.elastic.host1,
		'https://' + config.elastic.user + ':' + config.elastic.password + '@' + config.elastic.host2
	]
});

module.exports = {
	post : function(object, callback){
		client.index({
			index: config.elastic.index,
			type: config.elastic.type,
			id: object.PRODUCT_ID,
			body: object
		}, function (err, resp) {
			if (err){
				console.error(err);
				callback(err, null);
			} else {
				//console.log("Inserted:",object.PRODUCT_ID);
				callback(null, resp);
			}
		});
	}
	, bulk : function(object, callback){
		client.bulk({body: object}, function (err, resp) {
			if (err){
				console.error(err);
				callback(err, null);
			} else {
				//console.log("Inserted:",resp);
				callback(null, resp);
			}
		});
	}
	, count : function(req, res){
		client.count({
			index: config.elastic.index
		}, function (error, response) {
			if (error) {
  				console.log("Error counting the documents, please check the index name", error);
  				res.status(400).json({
	        		"message": "There was an error ccounting the documents, please check the index name" + error
	        	});
  			} else {
  				res.status(200).json(response);
  			}
		});
	}
	, search : function(req, res){
		var words = req.params.text.split(' ');
		var q = '"' + words[0] + '"';
		var i = 1;
		for(i=1; i<words.length; i++){
			q = q + ' AND "' + words[i] + '"'; 
		}

		client.msearch({
			body: [
				// query_string query, on index/mytype
				{ index: config.elastic.index, type: config.elastic.type },
				{ query: { query_string: { query: q } } }
			]
		}, function(error, response){
			if (error) {
  				console.log("Error searching the documents", error);
  				res.status(400).json({
	        		"message": "There was an error ccounting the documents, please check the index name" + error
	        	});
  			}
  			res.status(200).json(response);
		});
	}
}