var express = require('express')
	, router = express.Router()
	, accounts = require('../../../controllers/accounts')
	;

/* GET home page. */
router.get('/:userid', function(req, res) {
	accounts.getUser(req, res);
});

module.exports = router;