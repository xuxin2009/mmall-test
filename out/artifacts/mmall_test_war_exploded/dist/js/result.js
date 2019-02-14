webpackJsonp([9],{

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(161);


/***/ }),

/***/ 161:
/***/ (function(module, exports, __webpack_require__) {

	/*
	* @Author: Rosen
	* @Date:   2017-05-19 21:52:46
	* @Last Modified by:   Rosen
	* @Last Modified time: 2017-05-19 23:01:25
	*/

	'use strict';
	__webpack_require__(162);
	__webpack_require__(164);
	var _mm = __webpack_require__(10);

	$(function(){
	    var type        = _mm.getUrlParam('type') || 'default',
	        $element    = $('.' + type + '-success');
	    // 显示对应的提示元素
	    $element.show();
	    if(type === 'payment')
	    {
	    	var orderNumber  = _mm.getUrlParam('orderNumber');
	    	var $orderNumber = $element.find('.order-number');
	    	$orderNumber.attr('href',$orderNumber.attr('href')+orderNumber);
	    }
	})

/***/ }),

/***/ 162:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 164:
/***/ (function(module, exports, __webpack_require__) {

	/*
	* @Author: Rosen
	* @Date:   2017-05-17 11:26:25
	* @Last Modified by:   Rosen
	* @Last Modified time: 2017-05-17 11:26:46
	*/

	'use strict';
	__webpack_require__(165);

/***/ }),

/***/ 165:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ })

});