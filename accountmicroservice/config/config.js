var config = {
	db2 : {
		database : "ORDERDB",
		hostname : "<secure gateway cloud host>",//the one provided by the secure gateway
		uid : "<db2 user>",
		password : "<db2 user password>",
		port : "<secure gateway cloud port>" //provided by the secure gateway
	}
	, cloudant : {}
};

//Cloudant Credentials
if ( process.env.VCAP_SERVICES ) {
	var services = JSON.parse(process.env.VCAP_SERVICES);
	services = services['cloudantNoSQLDB'];
	if ( !services ) {
	    var err = new Error('The application does not have a Cloudant service');
	    console.error(err.message);
	} else {
	    if ( services.length > 1 ) {
	    	console.warn('There is more than one Cloudant service binded to this application');
	    }
	    services = services[0];
	    console.info('Using Cloudant service: ', services.name);
	    config.cloudant = services.credentials;
	    if ( process.env.DB ) {
	    	config.cloudant.db = process.env.DB;
	    } else {
	    	var err = new Error('The database "' + process.env.DB + '" has not been created, please create it and start the application again', services.name);
	    }
	}
} else {
	console.log("There is not a Cloudant service in the runtime variables, using local data");
	config.cloudant = {
	   	"username": "<Cloudant username>",
    	"password": "<Cloudant password>",
    	"host": "<Cloudant host>",
    	"port": 443,
    	"db" : "accounts"
	};
}

module.exports = config;