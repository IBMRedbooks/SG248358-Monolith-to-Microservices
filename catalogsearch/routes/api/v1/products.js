var express = require('express')
	, router = express.Router()
	, products = require('../../../controllers/products')
	;

/* GET home page. */
router.get('/count', function(req, res) {
	products.count(req, res);
});

router.get('/search/:text', function(req, res) {
	products.search(req, res);
});

module.exports = router;
