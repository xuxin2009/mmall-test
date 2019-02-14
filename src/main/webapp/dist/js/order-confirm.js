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
	var addressModel     = __webpack_require__(145);

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
	         //添加新地址
	         $(document).on('click', '.address-add', function(){
	          addressModel.show({
	            isUpdate : false,
	            onSuccess : function(){
	                _this.loadAddressList();
	            }
	          });
	        });

	         //删除地址
	         $(document).on('click', '.address-delete', function(e){
	            e.stopPropagation();
	          var id = $(this).parents('.address-item').data('id');
	          
	          if(window.confirm('确定要删除该地址吗？'))
	          {
	            _address.deleteAddress(id,function(res){
	                _this.loadAddressList();
	            },function(errMsg){
	                _mm.errorTips(errMsg);
	            });
	          }
	        });

	        //编辑地址
	         $(document).on('click', '.address-update', function(e)
	        {   
	            e.stopPropagation();
	             var shippingId = $(this).parents(".address-item").data('id');

	            _address.getAddress(shippingId,function(res)
	            {
	                 addressModel.show(
	                 {
	                     isUpdate : true,
	                     data     : res,
	                    onSuccess : function()
	                    {
	                         _this.loadAddressList();
	                    }
	                 }); 
	            },function(errMsg)
	            {
	                _mm.errorTips(errMsg);
	            });
	        });

	    },
	    // 加载地址列表
	    loadAddressList : function(){
	        $(".address-con").html('<div class="loading"></div>');
	        var _this = this;
	        // 获取地址列表
	        _address.getAddressList(function(res){
	            _this.addressFilter(res);

	            var addressListHtml = _mm.renderHtml(templateAddress,res);
	            $(".address-con").html(addressListHtml);
	        }, function(errMsg){
	              $(".address-con").html('<p class="err-tip">地址列表加载失败，请刷新后重新加载</p>');
	        
	        })
	    },
	    //处理地址列表选中状态
	    addressFilter :function(data)
	    {   
	        if(this.data.selectedAddressId)
	        {
	            var selectedAddressIdFlag = false;
	            for (var i = 0 ,length = data.list.length; i < length; i++) {
	                    if(data.list[i].id === _this.data.selectedAddressId)
	                    {
	                        data.list[i].isActive = true;
	                        selectedAddressIdFlag = true;
	                    }
	            }
	            //如果以前选中的不再地址列表中删除
	            if(!selectedAddressIdFlag)
	            {
	                this.data.selectedAddressId = null;
	            }
	        }
	    },
	   // 加载商品清单列表
	    loadProductList : function(){
	        $(".product-con").html('<div class="loading"></div>');
	        var _this   = this;
	        // 获取商品列表
	        _order.getProductList(function(res){
	            var productListHtml = _mm.renderHtml(templateProduct,res);
	            $(".product-con").html(productListHtml);
	        }, function(errMsg)
	        {
	            // $(".product-con").html('<p class="err-tip">+'errMsg'+</p>');
	            $(".product-list-con").html('<p class="err-tip">地址列表加载失败，请刷新后重新加载</p>');
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
	    },
	    save : function(addressInfo,resolve, reject)
	    {
	        _mm.request({
	            url     : _mm.getServerUrl('/shipping/add.do'),
	            data    : addressInfo,
	            success : resolve,
	            error   : reject
	        });
	    },
	    //查询单条地址信息
	    getAddress : function(shippingId,resolve, reject)
	    {
	       _mm.request({
	            url     : _mm.getServerUrl('/shipping/select.do'),
	            data    : 
	            {
	                shippingId : shippingId
	            },
	            success : resolve,
	            error   : reject
	        }); 
	    },
	    update : function(addressInfo,resolve, reject)
	    {
	         _mm.request({
	            url     : _mm.getServerUrl('/shipping/update.do'),
	            data    : addressInfo,
	            success : resolve,
	            error   : reject
	        });
	    },
	    //删除地址
	    deleteAddress: function(shippingId,resolve, reject)
	    {
	        _mm.request({
	            url          : _mm.getServerUrl('/shipping/delete.do'),
	            data         : {
	            shippingId   : shippingId
	            },
	            success      : resolve,
	            error        : reject
	        });
	    }
	    
	}
	module.exports = _address;

/***/ }),

/***/ 143:
/***/ (function(module, exports) {

	module.exports = " {{#orderItemVOList}}\r\n <table class=\"product-table\">\r\n    <tr>\r\n        <th class=\"cell-img\">&nbsp;</th>\r\n        <th class=\"cell-info\">商品描述</th>\r\n        <th class=\"cell-price\">价格</th>\r\n        <th class=\"cell-count\">数量</th>\r\n        <th class=\"cell-total\">小计</th>\r\n    </tr>\r\n    <tr>\r\n        <td class=\"cell-img\">\r\n            <a href=\"./detail/productId= {{productId}} \" target=\"_blank\">\r\n                <img class=\"p-img\" src=\"{{imageHost}}{{productImage}} \" alt=\"{{productName}} \">\r\n            </a>\r\n        </td>\r\n        <td class=\"cell-info\">\r\n            <a class=\"link\" href=\"./detail/productId= {{productId}}\" target=\"_blank\">{{productName}}</a>\r\n        </td>\r\n        <td class=\"cell-price\">￥{{currentUnitPrice}} </td>\r\n        <td class=\"cell-count\">{{quantity}}</td>\r\n        <td class=\"cell-total\">￥{{totalPrice}}</td>\r\n    </tr>   \r\n</table>\r\n{{/orderItemVOList}}\r\n<div class=\"submit-con\">\r\n    <span>订单总价:</span>\r\n    <span class=\"submit-total\">￥{{productTotalPrice}}</span>\r\n    <span class=\"btn order-submit\">提交订单</span>\r\n</div>\r\n";

/***/ }),

/***/ 144:
/***/ (function(module, exports) {

	module.exports = " {{#list}}\r\n     {{#isActive}}\r\n     <div class=\"address-item active\" data-id={{id}} >\r\n    {{/isActive}}\r\n    {{^isActive}}\r\n     <div class=\"address-item \" data-id={{id}} >\r\n    {{/isActive}}\r\n    <div class=\"address-title\">\r\n        {{receiverProvince}} {{receiverCity}} （ {{receiverName}} 收)\r\n    </div>\r\n    <div class=\"address-detail\">\r\n        {{receiverAddress}} {{receiverMobile}}\r\n    </div>\r\n    <div class=\"address-opera\">\r\n        <span class=\"link address-update\">编辑</span>\r\n        <span class=\"link address-delete\">删除</span>\r\n    </div>\r\n</div>\r\n{{/list}}\r\n<div class=\"address-add\">\r\n    <div class=\"address-new\">\r\n        <i class=\"fa fa-plus\"></i>\r\n        <div class=\"text\">使用新地址</div>\r\n    </div>\r\n</div>";

/***/ }),

/***/ 145:
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	var _mm             	   = __webpack_require__(10);
	var _address      		   = __webpack_require__(142);
	var templateAddressModel   = __webpack_require__(146);
	var _cities   			   = __webpack_require__(147);

	var addressModel = 
	{
		show : function(option)
		{
			//option数据绑定
			this.option = option;
			this.option.data = option.data || {};
			this.$modelWrap = $('.model-wrap');
			//渲染页面
			this.loadModel();
			//事件绑定
			this.bindEvent();

		},


		loadModel : function(){
			var addressModelHtml = _mm.renderHtml(templateAddressModel,{
				isUpdate	: 	this.option.isUpdate,
				data		: 	this.option.data 
			});
			this.$modelWrap.html(addressModelHtml);
			//加载省份
			this.loadProvince();
		},
		//加载省份
		loadProvince:function()
		{
			var provinces 	= _cities.getProvinces(),
			$provinceSelect = this.$modelWrap.find('#receiver-province');

			$provinceSelect.html(this.getSelectOption(provinces));
			//如果是更新操作，则获取省份并回填
			if(this.option.isUpdate && this.option.data.receiverProvince)
			{
				$provinceSelect.val(this.option.data.receiverProvince);
				this.loadCity(this.option.data.receiverProvince);
			}
		},
		//获取option
		getSelectOption : function(optionArray)
		{
			var html = '<option value="">请选择</option>'
			for(var i=0,length=optionArray.length;i<length;i++)
			{
				html +='<option value="'+optionArray[i]+'">'+optionArray[i]+'</option>';
			}
			return html;
		},
		//加载城市
		loadCity : function(provinceName){
			var cities = _cities.getCities(provinceName) || [],
			$citySelect = this.$modelWrap.find('#receiver-city');
			$citySelect.html(this.getSelectOption(cities));

			//如果是更新操作，则获取城市并回填
			if(this.option.isUpdate && this.option.data.receiverCity)
			{
				$citySelect.val(this.option.data.receiverCity);
			}

		},
		bindEvent :function(){
			var _this = this;
			this.$modelWrap.find('#receiver-province').change(function(){
				var selectedProvince = $(this).val();
				_this.loadCity(selectedProvince);
			});

			this.$modelWrap.find('.address-btn').click(function(){
				var receiverInfo = _this.getReceiverInfo(),
				isUpdate=_this.option.isUpdate;
				//新增地址并且验证通过
				if(!isUpdate && receiverInfo.status)
				{
					_address.save(receiverInfo.data,function(res){
						_mm.successTips("地址添加成功");
						_this.hide();
						typeof _this.option.onSuccess === 'function' 
						&& _this.option.onSuccess(res);
					},function(errMsg){
						_mm.errorTips(errMsg);
					});
				}
				//修改地址并且验证通过
				else if(isUpdate && receiverInfo.status)
				{
					_address.update(receiverInfo.data,function(res){
						_mm.successTips("地址修改成功");
						_this.hide();
						typeof _this.option.onSuccess === 'function' 
						&& _this.option.onSuccess(res);
					},function(errMsg){
						_mm.errorTips(errMsg);
					});
				}else
				{
					_mm.errorTips(receiverInfo.errMsg || '好像哪里不对了');
				}
			});

			//点击x关闭弹窗
			this.$modelWrap.find('.close').click(function(){
				_this.hide()
			});

		},
		//获取表单信息
		getReceiverInfo:function()
		{

			var receiverInfo = {},
			result = { 
				status : false
			 };
			 receiverInfo.receiverName = $.trim(this.$modelWrap.find('#receiver-name').val());
			 receiverInfo.receiverProvince = (this.$modelWrap.find('#receiver-province').val());
			 receiverInfo.receiverCity = (this.$modelWrap.find('#receiver-city').val());
			 receiverInfo.receiverAddress = $.trim(this.$modelWrap.find('#receiver-address').val());
			 receiverInfo.receiverPhone = $.trim(this.$modelWrap.find('#receiver-phone').val());
			 if(this.option.isUpdate)
			 {
			 	receiverInfo.id = parseInt(this.$modelWrap.find('#receiver-id').val());
			 }
			 
			 //做数据验证
			 if(!receiverInfo.receiverName)
			 {
			 	result.errMsg = '请输入收件人的姓名';
			 }else if(!receiverInfo.receiverProvince)
			 {
			 	result.errMsg = '请选择收件人所在的省份';
			 }else if(!receiverInfo.receiverCity)
			 {
			 	result.errMsg = '请选择收件人所在的城市';
			 }else if(!receiverInfo.receiverAddress)
			 {
			 	result.errMsg = '请输入收件人的详细地址';
			 }
			 else
			 {
			 	result.status =true;
			 	result.data = receiverInfo;
			 }
			 return result;

		},
		hide: function()
		{
			this.$modelWrap.empty();
		}
	     
	};
	module.exports = addressModel;





/***/ }),

/***/ 146:
/***/ (function(module, exports) {

	module.exports = "<div class=\"model\">\r\n    <div class=\"model-container\">\r\n        <div class=\"model-header\">\r\n            {{#isUpdate}}\r\n                <h1 class=\"model-title\">更新地址</h1>\r\n            {{/isUpdate}}\r\n            \r\n            {{^isUpdate}}\r\n                <h1 class=\"model-title\">使用新地址</h1>\r\n            {{/isUpdate}}\r\n\r\n            <i class=\"fa fa-close close\"></i>\r\n        </div>\r\n        <div class=\"model-body\">\r\n            <div class=\"form\">\r\n                <div class=\"form-line\">\r\n                    <label class=\"label\" for=\"\">\r\n                        <span class=\"required\">*</span>收件人姓名:\r\n                    </label>\r\n                    <input class=\"form-item\" id=\"receiver-name\" value=\"{{data.receiverName}}\" placeholder=\"请输入收件人姓名\">\r\n                </div>\r\n                 <div class=\"form-line\">\r\n                    <label class=\"label\" for=\"\">\r\n                        <span class=\"required\">*</span>所在城市:\r\n                    </label>\r\n                    <select class=\"form-item\" id=\"receiver-province\">  \r\n                        <option value=\"\">请选择</option>\r\n                    </select>\r\n                    <select class=\"form-item\" id=\"receiver-city\">\r\n                        <option value=\"\">请选择</option>\r\n                    </select>\r\n                </div>\r\n                 <div class=\"form-line\">\r\n                    <label class=\"label\" for=\"\">\r\n                        <span class=\"required\">*</span>详细地址:\r\n                    </label>\r\n                    <input class=\"form-item\" id=\"receiver-address\" value=\"{{data.receiverAddress}}\" placeholder=\"请输入详细地址\">\r\n                </div>\r\n                 <div class=\"form-line\">\r\n                    <label class=\"label\" for=\"\">\r\n                        <span class=\"required\">*</span>收件人手机:\r\n                    </label>\r\n                    <input class=\"form-item\" id=\"receiver-phone\" value=\"{{data.receiverMobile}}\" placeholder=\"请输入11位收件人手机号码\">\r\n                </div>\r\n                 <div class=\"form-line\">\r\n                    <label class=\"label\" for=\"\">邮政编码:</label>\r\n                    <input class=\"form-item\" id=\"receiver-zip\" value=\"{{data.receiverZip}}\" placeholder=\"如：100000\">\r\n                </div>\r\n                <div class=\"form-line\">\r\n                    <input type=\"hidden\" value=\"{{data.id}}\" id=\"receiver-id\"/>\r\n                    <div class=\"btn address-btn\">保存收货地址</div>\r\n\r\n                </div>\r\n            </div>\r\n\r\n        </div>\r\n    </div>\r\n</div>";

/***/ }),

/***/ 147:
/***/ (function(module, exports) {

	'use strict';

	var _cities = 
	{
		
		cityInfo : 
		{
			
		    "北京": ["北京"],
		    "天津": [ "天津"],
		    "河北": ["石家庄","唐山","秦皇岛","邯郸","邢台","保定","张家口","衡水","廊坊","沧州","承德"],
		    "山西": [ "太原","晋中","朔州","晋城","长治", "阳泉", "大同","运城","忻州","临汾","吕梁"],
		    "内蒙古": ["呼和浩特","包头","乌海",	"赤峰","通辽","鄂尔多斯","呼伦贝尔","巴彦淖尔","乌兰察布",
		    			"兴安","锡林郭勒","阿拉善"],
		 
		    "辽宁":
			[
		        "沈阳","大连", "鞍山", "抚顺", "本溪", "丹东","锦州", "营口", "阜新", "辽阳","盘锦","铁岭",
		         "朝阳","葫芦岛"
			],
		    "吉林": 
			[
		        "长春","吉林","四平","辽源", "通化",  "白山","松原","白城","延边"
			],
		    "黑龙江":
			[
		        "哈尔滨","齐齐哈尔",  "鸡西", "鹤岗","双鸭山", "大庆","伊春", "佳木斯","七台河", "牡丹江",
		         "黑河","绥化","大兴安岭"
			],
		    "上海":
			[
		        "上海"
		    ],
		    "江苏":
			[
		        "南京", "无锡", "徐州", "常州", "苏州", "南通", "连云港", "淮安",
		        "盐城","扬州","镇江", "泰州","宿迁"
		    ],
		    "浙江":
			[
		        "杭州", "宁波", "温州", "嘉兴","湖州", "绍兴", "金华",   "衢州", "舟山","丽水"
			],
		    
		    "安徽": 
			[
		        "合肥","芜湖", "蚌埠", "淮南", "马鞍山",  "淮北","铜陵", "安庆", "黄山",
		        "滁州","阜阳", "宿州", "巢湖","六安", "亳州", "池州", "宣城"
			],
		    "福建": 
			[
		        "福州", "厦门", "莆田", "三明", "泉州","漳州", "南平", "龙岩", "宁德"
			],
		    
		    "江西": 
			[
		        "南昌","景德镇", "萍乡", "九江", "新余", "鹰潭", "赣州","吉安", "宜春", "抚州", "上饶"
			],
		    "山东": 
			[
		        "济南", "青岛", "淄博", "枣庄",  "东营", "烟台",  "潍坊",  "济宁",  "泰安", "威海",
		        "日照","莱芜", "临沂", "德州", "聊城","滨州","菏泽"
			],
		    "河南":
			[ 
		        "郑州","开封", "洛阳","平顶山", "安阳", "鹤壁","新乡",  "焦作",
		        "濮阳",  "许昌", "漯河", "三门峡",   "南阳",  "商丘", "信阳",  "周口", "驻马店","济源"
			],
		    "湖北": 
			[	
		        "武汉", "黄石","十堰", "宜昌", "襄樊", "鄂州",
		        "荆门", "孝感", "荆州",  "黄冈",  "咸宁", "随州",
		        "恩施","仙桃","潜江", "天门","神农架"
			],
		    
		    "湖南": 
			[
		        "长沙","株洲","湘潭", "衡阳","邵阳", "岳阳", "常德", "张家界",
		        "益阳","郴州", "永州","怀化", "娄底", "湘西"
			],

			 "广东": 
			[
		        "广州", "韶关", "深圳",  "珠海", "汕头",  "佛山",
		        "江门","湛江", "茂名", "肇庆", "惠州", "梅州", "汕尾",
		        "河源", "阳江",  "清远", "东莞", "中山","潮州","揭阳","云浮"
		    ],
		    "广西": 
			[
		        "南宁", "柳州", "桂林", "梧州", "北海", "防城港", "钦州",
		        "贵港", "玉林", "百色","贺州", "河池", "来宾",  "崇左"
			],
		    "海南": 
			[
		        "海口", "三亚","三沙", "五指山",  "琼海",
		        "儋州", "文昌", "万宁","东方", "定安", "屯昌",
		        "澄迈", "临高","白沙","昌江", "乐东","陵水","保亭", "琼中"
			],
		    "重庆": 
			[
		        "重庆"
		     ],
		    "四川": 
			[
		        "成都", "自贡","攀枝花","泸州", "德阳", "绵阳", "广元",
		        "遂宁","内江","乐山","南充", "眉山","宜宾","广安","达川",
		        "雅安","巴中","资阳","阿坝","甘孜", "凉山"  
			],
		    "贵州": 
			[
		        "贵阳","六盘水", "遵义", "安顺", "铜仁", "黔西南","毕节","黔东南","黔南"
			],
		    "云南": 
			[
		        "昆明", "曲靖", "玉溪","保山", "昭通", "丽江","普洱", "临沧", "楚雄","红河", "文山", "西双版纳", "大理","德宏", "怒江", "迪庆"
			],
		    "西藏": 
			[
		        "拉萨","昌都","山南","日喀则","那曲","阿里", "林芝"
		    ],
		    "陕西": 
			[
		        "西安", "铜川", "宝鸡","咸阳", "渭南", "延安", "汉中","榆林","安康","商洛"
		    ],
		    "甘肃": 
			[
		        "兰州",  "嘉峪关","金昌","白银", "天水", "武威","张掖","平凉", "酒泉","庆阳" ,"定西","陇南","临夏", "甘南"
			],
		    "青海": 
			[
		        "西宁", "海东", "海北", "黄南",  "海南", "果洛","玉树", "梅西"
			],
		    "宁夏":
			[
		        "银川", "石嘴山", "吴忠", "固原",  "中卫"
		    ],
		   
		    "新疆": 
			[
		        "乌鲁木齐", "克拉玛依", "吐鲁番", "哈密","昌吉", "博尔塔拉","巴音郭楞", "阿克苏",
		        "克孜勒苏","喀什", "和田","伊犁", "塔城","阿勒泰", "石河子", "阿拉尔", "图木舒克", "五家渠"
			],
		    "香港": [ "香港"],
		    "澳门":["澳门"],
		    "台湾": [  "台湾"]
		   
		},
		//获取所有的省份
		getProvinces : function()
		{
			var province = [];
			for(var item in this.cityInfo){
				province.push(item);
			}
			return province;
		},
		//获取城市
		getCities : function(provinceName){
			return this.cityInfo[provinceName];
		}
	}

	module.exports = _cities;



/***/ })

});