var express = require('express')
	, router = express.Router()
	, etl = require('../../../controllers/etl')
	, auth = require('http-auth')
	;

var basic = auth.basic({
        realm: "Admin Area"
    }, function (username, password, callback) { 
        return callback(username === "user" && password === "passw0rd");
    }
);

/* GET home page. */
router.get('/transform', auth.connect(basic), function(req, res) {
	etl.transform(req, res);
});

module.exports = router;
