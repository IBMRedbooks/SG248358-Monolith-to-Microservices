var ibmdb = require('ibm_db')
	, products = require('./products')
	, config = require('../config/config')
	;

module.exports = {
	transform: function(req, res) {
		ibmdb.open("DRIVER={DB2};DATABASE=" + config.db2.database + ";HOSTNAME=" + config.db2.hostname + ";UID=" + config.db2.uid + ";PWD=" + config.db2.password + ";PORT=" + config.db2.port + ";PROTOCOL=TCPIP", function (err,conn) {
  			if (err) {
  				console.log("Error connecting to DB2:::", err);
  				res.status(500).json({
	        		"message": "There was an error connecting to the DB2 database ::: " + err
	        	});
  			} else {
  				conn.query('select p.*, c.* FROM product as p join prod_cat as pc on p.product_id = pc.product_id join category as c on c.cat_id = pc.cat_id order by product_id', function (err, data) {
			    	if (err) {
		  				console.log("Error executing select:::", err);
		  				res.status(500).json({
			        		"message": "There was an error executing the query ::: " + err
			        	});
		  			}
			    	else {
			    		//console.log(data);
			    		var newJSONArray = [{ index:  { _index: config.elastic.index, _type: config.elastic.type, _id: data[0].PRODUCT_ID} },
			    							{"PRODUCT_ID": data[0].PRODUCT_ID, "name": data[0].NAME, "description": data[0].DESCRIPTION, "image": data[0].IMAGE, "price": data[0].PRICE, "categories" : [{"CAT_ID": data[0].CAT_ID, "name" : data[0].CAT_NAME}]}];
			    		var i = 1;
			    		var j = 1;

			    		for (i=1; i<data.length; i++) {
			    			if (data[i].PRODUCT_ID == data[i-1].PRODUCT_ID) {
			    				newJSONArray[j].categories.push({"CAT_ID": data[i].CAT_ID, "name": data[i].CAT_NAME});
			    			} else {
			    				newJSONArray.push({ index:  { _index: config.elastic.index, _type: config.elastic.type, _id: data[i].PRODUCT_ID} });
			    				newJSONArray.push({"PRODUCT_ID": data[i].PRODUCT_ID, "name": data[i].NAME, "description": data[i].DESCRIPTION, "image": data[i].IMAGE, "price": data[i].PRICE, "categories" : [{"CAT_ID": data[i].CAT_ID, "name" : data[i].CAT_NAME}]});
			    				j = j + 2;
			    			}
			    		}
			    		products.bulk(newJSONArray, function(error, resp){
	    					if (error) {
				  				console.log(error2);
				  			} else {
				  				console.log(resp);
				  			}
	    				});
			    		res.status(200).json(newJSONArray);
			    	}
			 
			    	conn.close(function () {
			      		console.log('done');
			    	});
			  	});
  			}
		});
	}
}