webpackJsonp([6],{

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(148);


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

/***/ 120:
/***/ (function(module, exports, __webpack_require__) {

	/*
	* @Author: Rosen
	* @Date:   2017-05-19 17:39:14
	* @Last Modified by:   Rosen
	* @Last Modified time: 2017-05-24 16:46:02
	*/
	'use strict';
	__webpack_require__(121);
	var _mm             = __webpack_require__(10);
	var templateIndex   = __webpack_require__(123);
	// 侧边导航
	var navSide = {
	    option : {
	        name : '',
	        navList : [
	            {name : 'user-center', desc : '个人中心', href: './user-center.html'},
	            {name : 'order-list', desc : '我的订单', href: './order-list.html'},
	            {name : 'user-pass-update', desc : '修改密码', href: './user-pass-update.html'},
	            {name : 'about', desc : '关于MMall', href: './about.html'}
	        ]
	    },
	    init : function(option){
	        // 合并选项
	        $.extend(this.option, option);
	        this.renderNav();
	    },
	    // 渲染导航菜单
	    renderNav : function(){
	        // 计算active数据
	        for(var i = 0, iLength = this.option.navList.length; i < iLength; i++){
	            if(this.option.navList[i].name === this.option.name){
	                this.option.navList[i].isActive = true;
	            }
	        };
	        // 渲染list数据
	        var navHtml = _mm.renderHtml(templateIndex, {
	            navList : this.option.navList
	        });
	        // 把html放入容器
	        $('.nav-side').html(navHtml);
	    }
	};

	module.exports = navSide;

/***/ }),

/***/ 121:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 123:
/***/ (function(module, exports) {

	module.exports = "{{#navList}}\n{{#isActive}}\n<li class=\"nav-item active\">\n{{/isActive}}\n{{^isActive}}\n<li class=\"nav-item\">\n{{/isActive}}\n    <a class=\"link\" href=\"{{href}}\">{{desc}}</a>\n</li>\n{{/navList}} ";

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
	    },

	    getOrderList: function(listParam,resolve,reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/order/list.do'),
	            data    : listParam,
	            success : resolve,
	            error   : reject
	        })
	    },

	    getOrderDetail: function(orderNumber,resolve,reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/order/detail.do'),
	            data    : {
	                orderNo : orderNumber
	            },
	            success : resolve,
	            error   : reject
	        })
	    },
	    cancelOrder :  function(orderNumber,resolve,reject){
	        _mm.request({
	            url     : _mm.getServerUrl('/order/cancel.do'),
	            data    : {
	                orderNo : orderNumber
	            },
	            success : resolve,
	            error   : reject
	        })
	    }
	}
	module.exports = _order;

/***/ }),

/***/ 148:
/***/ (function(module, exports, __webpack_require__) {

	/*
	* @Author: Rosen
	* @Date:   2017-05-23 19:33:33
	* @Last Modified by:   Rosen
	* @Last Modified time: 2017-05-23 22:30:31
	*/

	'use strict';
	__webpack_require__(149);
	__webpack_require__(14);
	__webpack_require__(7);
	var navSide         = __webpack_require__(120);
	var _mm             = __webpack_require__(10);
	var _order          = __webpack_require__(141);
	var templateIndex   = __webpack_require__(151);

	// page 逻辑部分
	var page = {
	    data :{
	        orderNumber : _mm.getUrlParam('orderNumber')
	    },
	    init: function(){
	        this.onLoad();
	        this.bindEvent();
	    },
	    bindEvent:function()
	    {
	      var _this = this;
	      $(document).on('click','.order-cancel',function(){
	        if(window.confirm('确定要取消订单吗？'))
	        {
	             _order.cancelOrder(_this.data.orderNumber,function(res){
	            _mm.successTips("订单取消成功");
	            _this.loadOrderDetail();
	            },function(errMsg){
	                _mm.errorTips(errMsg);
	            });
	        }
	      })
	    },
	    onLoad : function(){
	       
	        // 初始化左侧菜单
	        navSide.init({
	            name: 'order-detail'
	        });
	         this.loadOrderDetail();
	    },

	    //加载订单列表
	    loadOrderDetail: function()
	    {
	        var _this =  this,
	           orderDetailHtml =   '',
	           $content = $('.content');

	            $content.html('<div class="loading"></div>')
	           _order.getOrderDetail(this.data.orderNumber,function(res){
	            
	            _this.dataFilter(res);

	            orderDetailHtml = _mm.renderHtml(templateIndex,res);
	            $content.html(orderDetailHtml);

	           },function(errMsg){
	            $content.html(errMsg);
	           });
	    },

	    dataFilter: function(data)
	    {
	        data.needPay        = data.status == 10;
	        data.isCancelable   = data.status == 10;

	    }
	        
	};
	$(function(){
	    page.init();
	});

/***/ }),

/***/ 149:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 151:
/***/ (function(module, exports) {

	module.exports = "<div class=\"panel\">\r\n    <div class=\"panel-title\">订单信息</div>\r\n    <div class=\"panel-body\">\r\n        <div class=\"order-info\">\r\n            <div class=\"text-line\">\r\n                <span class=\"text\">订单号：{{orderNo}} </span>\r\n                <span class=\"text\">创建时间：{{createTime}} </span>\r\n            </div>\r\n            <div class=\"text-line\">\r\n                <span class=\"text\">\r\n                    收件人： {{recevierName}} {{shippingVo.receiverProvince}} {{shippingVo.receiverCity}} {{shippingVo.receiverAddress}} {{shipping.receiverMobile}}\r\n                </span>\r\n            </div>\r\n            <div class=\"text-line\">\r\n                <span class=\"text\">订单状态：{{statusDesc}}</span>\r\n            </div>\r\n            <div class=\"text-line\">\r\n                <span class=\"text\">支付方式：{{paymentTypeDesc}} </span>\r\n            </div>\r\n            <div class=\"text-line\">\r\n            {{#needPay}}\r\n                <a class=\"btn\" href=\"./payment.html?orderNumber={{orderNo}} \">去支付</a>\r\n            {{/needPay}}\r\n            {{#isCancelable}}\r\n                <a class=\"btn order-cancel\" >取消订单</a>\r\n            \r\n            {{/isCancelable}}\r\n            </div>\r\n\r\n        </div>\r\n    </div>\r\n</div>\r\n<div class=\"panel\">\r\n    <div class=\"panel-title\">商品清单</div>\r\n    <div class=\"panel-body\">\r\n        <table class=\"product-table \">\r\n            <tr>\r\n                <th class=\"cell-th cell-img\">&nbsp;</th>\r\n                <th class=\"cell-th cell-info\">商品信息</th>\r\n                <th class=\"cell-th cell-price\">单价</th>\r\n                <th class=\"cell-th cell-count\">数量</th>\r\n                <th class=\"cell-th cell-total\">小计</th>\r\n            </tr>\r\n           {{#orderItemVOList}}\r\n            <tr>\r\n                <td class=\"cell-th cell-img\">\r\n                    <a href=\"./detail.html?productId={{productId}}\" target=\"_blank\">\r\n                        <img class=\"p-img\" src=\"{{imageHost}}{{productImage}} \" alt=\"{{productName}} \"/>\r\n                    </a>\r\n                </td>\r\n                <td class=\"cell-th cell-info\">\r\n                    <a class=\"link\" href=\"./detail.html?productId={{productId}}\" target=\"_blank\">\r\n                        {{productName}}\r\n                    </a>\r\n                </td>\r\n                <td class=\"cell-th cell-price\">{{currentUnitPrice}}</td>\r\n                <td class=\"cell-th cell-count\">{{quantity}}</td>\r\n                <td class=\"cell-th cell-total\">{{totalPrice}}</td>\r\n            </tr>\r\n            {{/orderItemVOList}}\r\n        </table>\r\n        <p class=\"total\">\r\n            <span>订单总价：</span>\r\n            <span class=\"total-price\">{{payment}}</span>\r\n        </p>\r\n    </div>\r\n</div>";

/***/ })

});