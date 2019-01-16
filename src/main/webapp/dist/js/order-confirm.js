webpackJsonp([5],{

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(138);


/***/ }),

/***/ 7:
/***/ (function(module, exports, __webpack_require__) {

	/*
	* @Author: Rosen
	* @Date:   2017-05-18 19:30:12
	* @Last Modified by:   Rosen
	* @Last Modified time: 2017-05-27 19:46:42
	*/

	'use strict';
	__webpack_require__(8);
	var _mm     = __webpack_require__(10);
	// 通用页面头部
	var header = {
	    init : function(){
	        this.onLoad();
	        this.bindEvent();
	    },
	    onLoad : function(){
	        var keyword = _mm.getUrlParam('keyword');
	        // keyword存在，则回填输入框
	        if(keyword){
	            $('#search-input').val(keyword);
	        };
	    },
	    bindEvent : function(){
	        var _this = this;
	        // 点击搜索按钮以后，做搜索提交
	        $('#search-btn').click(function(){
	            _this.searchSubmit();
	        });
	        // 输入会车后，做搜索提交
	        $('#search-input').keyup(function(e){
	            // 13是回车键的keyCode
	            if(e.keyCode === 13){
	                _this.searchSubmit();
	            }
	        });
	    },
	    // 搜索的提交
	    searchSubmit : function(){
	        var keyword = $.trim($('#search-input').val());
	        // 如果提交的时候有keyword,正常跳转到list页
	        if(keyword){
	            window.location.href = './list.html?keyword=' + keyword;
	        }
	        // 如果keyword为空，直接返回首页
	        else{
	            _mm.goHome();
	        }
	    }
	};

	header.init();

/***/ }),

/***/ 8:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 14:
/***/ (function(module, exports, __webpack_require__) {

	/*
	* @Author: Rosen
	* @Date:   2017-05-17 14:17:01
	* @Last Modified by:   Rosen
	* @Last Modified time: 2017-05-22 12:21:05
	*/

	'use strict';
	__webpack_require__(15);
	var _mm     = __webpack_require__(10);
	var _user   = __webpack_require__(17);
	var _cart   = __webpack_require__(18);
	// 导航
	var nav = {
	    init : function(){
	        this.bindEvent();
	        this.loadUserInfo();
	        this.loadCartCount();
	        return this;
	    },
	    bindEvent : function(){
	        // 登录点击事件
	        $('.js-login').click(function(){
	            _mm.doLogin();
	        });
	        // 注册点击事件
	        $('.js-register').click(function(){
	            window.location.href = './user-register.html';
	        });
	        // 退出点击事件
	        $('.js-logout').click(function(){
	            _user.logout(function(res){
	                window.location.reload();
	            }, function(errMsg){
	                _mm.errorTips(errMsg);
	            });
	        });
	    },
	    // 加载用户信息
	    loadUserInfo : function(){
	        _user.checkLogin(function(res){
	            $('.user.not-login').hide().siblings('.user.login').show()
	                .find('.username').text(res.username);
	        }, function(errMsg){
	            // do nothing
	        });
	    },
	    // 加载购物车数量
	    loadCartCount : function(){
	        _cart.getCartCount(function(res){
	            $('.nav .cart-count').text(res || 0);
	        }, function(errMsg){
	            $('.nav .cart-count').text(0);
	        });
	    }
	};

	module.exports = nav.init();

/***/ }),

/***/ 15:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 17:
/***/ (function(module, exports, __webpack_require__) {

	/*
	* @Author: Rosen
	* @Date:   2017-05-17 17:04:32
	* @Last Modified by:   Rosen
	* @Last Modified time: 2017-05-24 17:11:19
	*/

	'use strict';

	var _mm = __webpack_require__(10);

	var _user = {
	    // 用户登录
	    login : function(userInfo, resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/user/login.do'),
	            data    : userInfo,
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 检查用户名
	    checkUsername : function(username, resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/user/check_valid.do'),
	            data    : {
	                type    : 'username',
	                str     : username
	            },
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 用户注册
	    register : function(userInfo, resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/user/register.do'),
	            data    : userInfo,
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 检查登录状态
	    checkLogin : function(resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/user/get_user_info.do'),
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 获取用户密码提示问题
	    getQuestion : function(username, resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/user/forget_get_question.do'),
	            data    : {
	                username : username
	            },
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 检查密码提示问题答案
	    checkAnswer : function(userInfo, resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/user/forget_check_answer.do'),
	            data    : userInfo,
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 重置密码
	    resetPassword : function(userInfo, resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/user/forget_reset_password.do'),
	            data    : userInfo,
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 获取用户信息
	    getUserInfo : function(resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/user/get_information.do'),
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 更新个人信息
	    updateUserInfo : function(userInfo, resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/user/update_information.do'),
	            data    : userInfo,
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 登录状态下更新密码
	    updatePassword : function(userInfo, resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/user/reset_password.do'),
	            data    : userInfo,
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 登出
	    logout : function(resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/user/logout.do'),
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    }
	}
	module.exports = _user;

/***/ }),

/***/ 18:
/***/ (function(module, exports, __webpack_require__) {

	/*
	* @Author: Rosen
	* @Date:   2017-05-17 18:55:04
	* @Last Modified by:   Rosen
	* @Last Modified time: 2017-06-02 17:51:15
	*/

	'use strict';

	var _mm = __webpack_require__(10);

	var _cart = {
	    // 获取购物车数量
	    getCartCount : function(resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/cart/get_cart_product_count.do'),
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 添加到购物车
	    addToCart : function(productInfo, resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/cart/add.do'),
	            data    : productInfo,
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 获取购物车列表
	    getCartList : function(resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/cart/list.do'),
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 选择购物车商品
	    selectProduct : function(productId, resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/cart/select.do'),
	            data    : {
	                productId : productId
	            },
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 取消选择购物车商品
	    unselectProduct : function(productId, resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/cart/un_select.do'),
	            data    : {
	                productId : productId
	            },
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 选中全部商品
	    selectAllProduct : function(resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/cart/select_all.do'),
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 取消选中全部商品
	    unselectAllProduct : function(resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/cart/un_select_all.do'),
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 更新购物车商品数量
	    updateProduct : function(productInfo, resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/cart/update.do'),
	            data    : productInfo,
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 删除指定商品
	    deleteProduct : function(productIds, resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/cart/delete_product.do'),
	            data    : {
	                productIds : productIds
	            },
	            success : resolve,
	            error   : reject
	        });
	    },
	}
	module.exports = _cart;

/***/ }),

/***/ 138:
/***/ (function(module, exports, __webpack_require__) {

	
	'use strict';
	__webpack_require__(139);
	__webpack_require__(7);
	__webpack_require__(14);
	var _mm              = __webpack_require__(10);
	var _order           = __webpack_require__(141);
	var _address         = __webpack_require__(142);
	var templateProduct  = __webpack_require__(143);
	var templateAddress  = __webpack_require__(144);

	var page = {

	     data : {
	         selectedAddressId : null
	        
	    },
	    
	    init : function(){
	        this.onLoad();
	        this.bindEvent();
	    },
	    onLoad : function(){
	       this.loadAddressList();
	       this.loadProductList();
	    },
	    bindEvent : function()
	    {
	        var _this = this;
	         // 地址的选择
	        $(document).on('click', '.address-item', function(){
	           $(this).addClass('active').siblings('.address-item').removeClass('active');   
	           _this.data.selectedAddressId = $(this).data('id');  

	        });
	        //订单的提交
	         $(document).on('click', '.order-submit', function(){
	           var shippingId = _this.data.selectedAddressId;
	           if(shippingId)
	           {
	                _order.createOrder({
	                    shippingId : shippingId
	                },function(res){
	                    window.location.href = './payment.html?orderNumber='+res.orderNo;
	                },function(errMsg){
	                    _mm.errorTips(errMsg);
	                });

	           }else
	           {
	            _mm.errorTips('请选择地址后在提交！');
	           }
	        });
	    },
	    // 加载地址列表
	    loadAddressList : function(){
	        var _this = this;
	        // 获取地址列表
	        _address.getAddressList(function(res){
	            var addressListHtml = _mm.renderHtml(templateAddress,res);
	            $(".address-con").html(addressListHtml);
	        }, function(errMsg){
	             // $(".address-con").html('<p class="err-tip">地址列表加载失败，请刷新后重新加载</p>');
	            var addressListHtml = _mm.renderHtml(templateAddress,errMsg);
	            $(".address-con").html(addressListHtml);
	        })
	    },
	    // 加载商品清单列表
	    loadProductList : function(){
	        var _this   = this;
	        // 获取商品列表
	        _order.getProductList(function(res){
	            var productListHtml = _mm.renderHtml(templateProduct,res);
	            $(".product-con").html(productListHtml);
	        }, function(errMsg)
	        {
	            $(".product-con").html('<p class="err-tip">商品信息加载失败，请刷新后重新加载</p>');
	            // var productListHtml = _mm.renderHtml(templateProduct,errMsg);
	            // $(".product-con").html(productListHtml);
	        })
	    }
	    
	};
	$(function(){
	    page.init();
	})

/***/ }),

/***/ 139:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 141:
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	var _mm = __webpack_require__(10);

	var _order = {
	    // 获取商品列表
	    getProductList : function(resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/order/get_order_cart_product.do'),
	            success : resolve,
	            error   : reject
	        });
	    },
	    //订单的提交
	    createOrder : function(orderInfo,resolve,reject){
	    	_mm.request({
	    		url     : _mm.getServerUrl('/order/create.do'),
	    		data    : orderInfo,
	    		success : resolve,
	            error   : reject
	    	})
	    }
	}
	module.exports = _order;

/***/ }),

/***/ 142:
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	var _mm = __webpack_require__(10);

	var _address = {
	    // 获取地址列表
	    getAddressList : function(resolve, reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/shipping/list.do'),
	            data	:{
	            	pageSize : 50
	            },
	            success : resolve,
	            error   : reject
	        });
	    }
	    
	}
	module.exports = _address;

/***/ }),

/***/ 143:
/***/ (function(module, exports) {

	module.exports = " <table class=\"product-table\">\r\n    <tr>\r\n        <th class=\"cell-img\">&nbsp;</th>\r\n        <th class=\"cell-info\">商品描述</th>\r\n        <th class=\"cell-price\">价格</th>\r\n        <th class=\"cell-count\">数量</th>\r\n        <th class=\"cell-total\">小计</th>\r\n    </tr>\r\n    <tr>\r\n        <td class=\"cell-img\">\r\n            <a href=\"./detail/productId=xxx\" target=\"_blank\">\r\n                <img class=\"p-img\" src=\"https://img.alicdn.com/tfs/TB1kpmUSVXXXXaIXpXXXXXXXXXX-223-377.png\" alt=\"图片\">\r\n            </a>\r\n        </td>\r\n        <td class=\"cell-info\">\r\n            <a class=\"link\" href=\"#\" target=\"_blank\">iphoneX</a>\r\n        </td>\r\n        <td class=\"cell-price\">￥8999</td>\r\n        <td class=\"cell-count\">1</td>\r\n        <td class=\"cell-total\">￥8999</td>\r\n    </tr>\r\n    <tr>\r\n        <td class=\"cell-img\">\r\n            <a href=\"#\" target=\"_blank\">\r\n                <img class=\"p-img\" src=\"https://img.alicdn.com/tfs/TB1kpmUSVXXXXaIXpXXXXXXXXXX-223-377.png\" alt=\"图片\">\r\n            </a>\r\n        </td>\r\n        <td class=\"cell-info\">\r\n            <a class=\"link\" href=\"#\" target=\"_blank\">iphoneX</a>\r\n        </td>\r\n        <td class=\"cell-price\">￥8999</td>\r\n        <td class=\"cell-count\">1</td>\r\n        <td class=\"cell-total\">￥8999</td>\r\n    </tr>    \r\n</table>\r\n\r\n<div class=\"submit-con\">\r\n    <span>订单总价:</span>\r\n    <span class=\"submit-total\">￥8999</span>\r\n    <span class=\"btn order-submit\">提交订单</span>\r\n</div>\r\n";

/***/ }),

/***/ 144:
/***/ (function(module, exports) {

	module.exports = " {{#list}}\r\n <div class=\"address-item \" data-id={{id}} >\r\n    <div class=\"address-title\">\r\n        {{receiverCity}} {{receiverCity}} （ {{receiverName}} 收)\r\n    </div>\r\n    <div class=\"address-detail\">\r\n        {{receiverAddress}} {{receiverMobile}}\r\n    </div>\r\n    <div class=\"address-opera\">\r\n        <span class=\"link address-update\">编辑</span>\r\n        <span class=\"link address-delete\">删除</span>\r\n    </div>\r\n</div>\r\n{{/list}}\r\n\r\n<div class=\"address-item \" data-id={{id}} >\r\n    <div class=\"address-title\">\r\n        四川省 成都市 (sean 收)\r\n    </div>\r\n    <div class=\"address-detail\">\r\n        成都市高新区天府软件园B2 13655889955\r\n    </div>\r\n    <div class=\"address-opera\">\r\n        <span class=\"link address-update\">编辑</span>\r\n        <span class=\"link address-delete\">删除</span>\r\n    </div>\r\n</div>\r\n\r\n<div class=\"address-add\">\r\n    <div class=\"address-new\">\r\n        <i class=\"fa fa-plus\"></i>\r\n        <div class=\"text\">使用新地址</div>\r\n    </div>\r\n</div>";

/***/ })

});