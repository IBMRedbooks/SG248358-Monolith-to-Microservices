var config = {
	db2 : {
		database : "ORDERDB",
		hostname : "<secure gateway cloud host>",//the one provided by the secure gateway
		uid : "<db2 user>",
		password : "<db2 user password>",
		port : "<secure gateway cloud port>" //provided by the secure gateway
	}
	, elastic : {
		index : "catalog",
		type : "product",
		user : "<Elasticsearch by Compose user>",
		password : "<Elasticsearch by Compose password>",
		host1: "<Elasticsearch by Compose host 1>",
		host2: "<Elasticsearch by Compose host 2>"
	}
};

module.exports = config;