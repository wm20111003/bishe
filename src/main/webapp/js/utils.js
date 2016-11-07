Util = {
	/**
	 * 取消事件冒泡
	 * @param {Object}
	 *            e 事件对象
	 */
	stopBubble : function(e) {
		if (e && e.stopPropagation) {
			e.stopPropagation();
		} else {
			// ie
			window.event.cancelBubble = true;
		}
	},
	/**
	 * 入参转码
	 * @param {string}
	 * 		json格式
	 */
	transCoding : function(json){
		var temp=encodeURIComponent(json);
		temp=CryptoJS.enc.Utf8.parse(temp);
		temp=CryptoJS.enc.Base64.stringify(temp);
		return temp;
	},
	/**
	 * 入参转码
	 * @param {string}
	 * 		json格式
	 */
	transDecoding : function(objStr){
		var words = CryptoJS.enc.Base64.parse(objStr);
		words = words.toString(CryptoJS.enc.Utf8);
		words = decodeURIComponent(words)
		return words;
	}
};
/**
 * 日期时间处理工具
 * 
 * @namespace Util
 * @class date
 */
Util.date = {
	/**
	 * 格式化日期时间字符串
	 * 
	 * @method dateTime2str
	 * @param {Date}
	 *            dt 日期对象
	 * @param {String}
	 *            fmt 格式化字符串，如：'yyyy-MM-dd hh:mm:ss'
	 * @return {String} 格式化后的日期时间字符串
	 */
	dateTime2str : function(dt, fmt) {
		var z = {
			M : dt.getMonth() + 1,
			d : dt.getDate(),
			h : dt.getHours(),
			m : dt.getMinutes(),
			s : dt.getSeconds()
		};
		fmt = fmt.replace(/(M+|d+|h+|m+|s+)/g, function(v) {
			return ((v.length > 1 ? "0" : "") + eval('z.' + v.slice(-1)))
					.slice(-2);
		});
		return fmt.replace(/(y+)/g, function(v) {
			return dt.getFullYear().toString().slice(-v.length);
		});
	},
	/**
	 * 根据日期时间格式获取获取当前日期时间
	 * 
	 * @method dateTimeWrapper
	 * @param {String}
	 *            fmt 日期时间格式，如："yyyy-MM-dd hh:mm:ss";
	 * @return {String} 格式化后的日期时间字符串
	 */
	dateTimeWrapper : function(fmt) {
		if (arguments[0])
			fmt = arguments[0];
		return this.dateTime2str(new Date(), fmt);
	},
	/**
	 * 获取当前日期时间
	 * 
	 * @method getDatetime
	 * @param {String}
	 *            fmt [optional,default='yyyy-MM-dd hh:mm:ss'] 日期时间格式。
	 * @return {String} 格式化后的日期时间字符串
	 */
	getDatetime : function(fmt) {
		return this.dateTimeWrapper(fmt || 'yyyy-MM-dd hh:mm:ss');
	},
	/**
	 * 获取当前日期时间+毫秒
	 * 
	 * @method getDatetimes
	 * @param {String}
	 *            fmt [optional,default='yyyy-MM-dd hh:mm:ss'] 日期时间格式。
	 * @return {String} 格式化后的日期时间字符串
	 */
	getDatetimes : function(fmt) {
		var dt = new Date();
		return this.dateTime2str(dt, fmt || 'yyyy-MM-dd hh:mm:ss') + '.'
				+ dt.getMilliseconds();
	},
	/**
	 * 获取当前日期（年-月-日）
	 * 
	 * @method getDate
	 * @param {String}
	 *            fmt [optional,default='yyyy-MM-dd'] 日期格式。
	 * @return {String} 格式化后的日期字符串
	 */
	getDate : function(fmt) {
		return this.dateTimeWrapper(fmt || 'yyyy-MM-dd');
	},
	/**
	 * 获取当前时间（时:分:秒）
	 * 
	 * @method getTime
	 * @param {String}
	 *            fmt [optional,default='hh:mm:ss'] 日期格式。
	 * @return {String} 格式化后的时间字符串
	 */
	getTime : function(fmt) {
		return this.dateTimeWrapper(fmt || 'hh:mm:ss');
	}
};
/**
 * 通过 HTTP 请求加载远程数据，底层依赖jQuery的AJAX实现。当前接口实现了对jQuery AJAX接口的进一步封装。
 */
Util.ajax = {
	/**
	 * 请求状态码
	 * 
	 * @type {Object}
	 */
	reqCode : {
		/**
		 * 成功返回码 0000
		 * 
		 * @type {Number} 1
		 * @property SUCC
		 */
		SUCC : 200
	},
	/**
	 * 请求的数据类型
	 * 
	 * @type {Object}
	 * @class reqDataType
	 */
	dataType : {
		/**
		 * 返回html类型
		 * 
		 * @type {String}
		 * @property HTML
		 */
		HTML : "html",
		/**
		 * 返回json类型
		 * 
		 * @type {Object}
		 * @property JSON
		 */
		JSON : "json",
		/**
		 * 返回text字符串类型
		 * 
		 * @type {String}
		 * @property TEXT
		 */
		TEXT : "text"
		//JSONP : "jsonp"
	},
	/**
	 * 超时,默认超时30000ms
	 * 
	 * @type {Number} 10000ms
	 * @property TIME_OUT
	 */
	TIME_OUT : 60000,
	/**
	 * 显示请求成功信息
	 * 
	 * @type {Boolean} false
	 * @property SHOW_SUCC_INFO
	 */
	SHOW_SUCC_INFO : false,
	/**
	 * 显示请求失败信息
	 * 
	 * @type {Boolean} false
	 * @property SHOW_ERROR_INFO
	 */
	SHOW_ERROR_INFO : false,
	/**
	 * GetJson是对Util.ajax的封装,为创建 "GET" 请求方式返回 "JSON"(text) 数据类型
	 * @param {String}
	 *            url HTTP(GET)请求地址
	 * @param {Object}
	 *            cmd json对象参数
	 * @param {Function}
	 *            callback [optional,default=undefined] GET请求成功回调函数
	 */
	getJson : function(url, cmd, callback) {
		if (arguments.length !== 3)
			callback = cmd, cmd = '';
		dataType = this.dataType.TEXT;
		// var _this = this;
		// setTimeout( function(){_this.ajax(url, 'GET', cmd, dataType,
		// callback)},1000);
		this.ajax(url, 'GET', cmd, dataType, callback);
	},
	/**
	 * PostJsonAsync是对Util.ajax的封装,为创建 "POST" 请求方式返回 "JSON"(text) 数据类型,
	 * 采用同步阻塞的方式调用ajax
	 * @param {String}
	 *            url HTTP(POST)请求地址
	 * @param {Object}
	 *            cmd json对象参数
	 * @param {Function}
	 *            callback [optional,default=undefined] POST请求成功回调函数
	 */
	postJsonSync : function(url, cmd, callback) {
		dataType = this.dataType.TEXT;
		// dataType = this.dataType.JSONP;
		this.ajax(url, 'POST', cmd, dataType, callback, true);
	},
	/**
	 * PostJson是对Util.ajax的封装,为创建 "POST" 请求方式返回 "JSON"(text) 数据类型
	 * @param {String}
	 *            url HTTP(POST)请求地址
	 * @param {Object}
	 *            cmd json对象参数
	 * @param {Function}
	 *            callback [optional,default=undefined] POST请求成功回调函数
	 */
	postJson : function(url, cmd, callback,flag) {
        // if(!flag){Util.loading.showLoading();}
		dataType = this.dataType.JSON;
		// dataType = this.dataType.JSONP;
		// var _this = this;
		// setTimeout( function(){_this.ajax(url, 'POST', cmd, dataType,
		// callback)},1000);
		this.ajax(url, 'POST', cmd, dataType, callback,'',flag);
	},
	/**
	 * loadHtml是对Ajax load的封装,为载入远程 HTML 文件代码并插入至 DOM 中
	 * @param {Object}
	 *            obj Dom对象
	 * @param {String}
	 *            url HTML 网页网址
	 * @param {Function}
	 *            callback [optional,default=undefined] 载入成功时回调函数
	 */
	loadHtml : function(obj, url, data, callback) {
		$(obj).load(url, data, function(response, status, xhr) {
			callback = callback ? callback : function() {
			};
			status == "success" ? callback(true) : callback(false);
		});
	},
	/**
	 * loadTemp是对handlebars 的封装,请求模版加载数据
	 * @param {Object}
	 *            obj Dom对象
	 * @param {Object}
	 *            temp 模版
	 * @param {Object}
	 *            data 数据
	 */
	loadTemp : function(obj, temp, data) {
		var template = Handlebars.compile((temp instanceof jQuery)?temp.html():temp);
		obj = (obj instanceof jQuery)?obj:$(obj);
		obj.html(template(data));
	},
	/**
	 * GetHtml是对Util.ajax的封装,为创建 "GET" 请求方式返回 "hmtl" 数据类型
	 * @param {String}
	 *            url HTTP(GET)请求地址
	 * @param {Object}
	 *            cmd json对象参数
	 * @param {Function}
	 *            callback [optional,default=undefined] GET请求成功回调函数
	 */
	getHtml : function(url, cmd, callback) {
		if (arguments.length !== 3)
			callback = cmd, cmd = '';
		dataType = this.dataType.HTML;
		this.ajax(url, 'GET', cmd, dataType, callback);
	},
	/**
	 * GetHtmlSync是对Util.ajax的封装,为创建 "GET" 请求方式返回 "hmtl" 数据类型
	 * 采用同步阻塞的方式调用ajax
	 * @param {String}
	 *            url HTTP(GET)请求地址
	 * @param {Object}
	 *            cmd json对象参数
	 * @param {Function}
	 *            callback [optional,default=undefined] GET请求成功回调函数
	 */
	getHtmlSync : function(url, cmd, callback) {
		if (arguments.length !== 3)
			callback = cmd, cmd = '';
		dataType = this.dataType.HTML;
		this.ajax(url, 'GET', cmd, dataType, callback,true);
	},
	/**
	 * 基于jQuery ajax的封装，可配置化
	 * 
	 * @method ajax
	 * @param {String}
	 *            url HTTP(POST/GET)请求地址
	 * @param {String}
	 *            type POST/GET
	 * @param {Object}
	 *            cmd json参数命令和数据
	 * @param {String}
	 *            dataType 返回的数据类型
	 * @param {Function}
	 *            callback [optional,default=undefined] 请求成功回调函数,返回数据data和isSuc
	 */
	ajax : function(url, type, cmd, dataType, callback, sync,flag) {
		var param = "";
		/*if (typeof (cmd) == "object"){
			param = JSON.stringify(cmd);
		}else if(typeof(cmd)=="string"){
			param = cmd;
		}*/
		//cmd = this.jsonToUrl(cmd);
		async = sync ? false : true;
		var thiz = Util.ajax;
		var cache = (dataType == "html") ? true : false;
		$.ajax({
			url : url,
			type : type,
			data : cmd,
			// data : encodeURI(cmd),
			/*processData: false,  	// 告诉jQuery不要去处理发送的数据
			contentType: false,		// 告诉jQuery不要去设置Content-Type请求头*/
			cache : cache,
			dataType : dataType,
			// jsonp: "jsonpcallback",
			// contentType: "application/jsonp; charset=utf-8",
			async : async,
			timeout : thiz.TIME_OUT,
			beforeSend : function(xhr) {
				xhr.overrideMimeType("text/plain; charset=utf-8");
			},
			success : function(data) {
				if (!data) {
					return;
				}
				if (dataType == "html") {
					callback(data, true);
					return;
				}
				var isSuc = thiz.printReqInfo(data);
				if (callback && data) {
					callback(data || {}, isSuc);
				}
			},
			error : function() {
			    var retErr ={};
			    retErr['returnCode']="404";
			    retErr['returnMessage']="网络异常或超时，请稍候再试！"; 
				callback(retErr, false);
			},
            complete:function(){
                //if(!flag){Util.loading.hideLoading();}
            }
		});
	},
	/**
	 * 打开请求返回代码和信息
	 * 
	 * @method printRegInfo
	 * @param {Object}
	 *            data 请求返回JSON数据
	 * @return {Boolean} true-成功; false-失败
	 */
	printReqInfo : function(data) {
		if (!data)
			return false;
		var code = data.statusCode, msg = data.message, succ = this.reqCode.SUCC;
		if (code == succ) {
			if (this.SHOW_SUCC_INFO) {
				// Util.msg.infoCorrect([ msg, ' [', code, ']' ].join(''));
				Util.msg.infoCorrect(msg);
			}
		} else {
			// Util.msg.infoAlert([ msg, ' [', code, ']' ].join(''));
			if (this.SHOW_ERROR_INFO) {
				Util.dialog.tips(msg);
			}
		}
		return !!(code == succ);
	},
	/**
	 * JSON对象转换URL参数
	 * 
	 * @method printRegInfo
	 * @param {Object}
	 *            json 需要转换的json数据
	 * @return {String} url参数字符串
	 */
	jsonToUrl : function(json) {
		var temp = [];
		for ( var key in json) {
			if (json.hasOwnProperty(key)) {
				var _key = json[key] + "";
				_key = _key.replace(/\+/g, "%2B");
				_key = _key.replace(/\&/g, "%26");
				temp.push(key + '=' + _key);
			}
		}
		return temp.join("&");
	},
	msg : {
		"suc" : function(obj, text) {
			var _text = text || "数据提交成功！";
			$(obj).html(
					'<div class="msg-hint">' + '<h3 title=' + _text
							+ '><i class="hint-icon hint-suc-s"></i>' + _text
							+ '</h3>' + '</div>').show();
		},
		"war" : function(obj, text) {
			var _text = text || "数据异常，请稍后尝试!";
			$(obj).html(
					'<div class="msg-hint">' + '<h3 title=' + _text
							+ '><i class="hint-icon hint-war-s"></i>' + _text
							+ '</h3>' + '</div>').show();
		},
		"err" : function(obj, text) {
			var _text = text || "数据提交失败!";
			$(obj).html(
					'<div class="msg-hint">' + '<h3 title=' + _text
							+ '><i class="hint-icon hint-err-s"></i>' + _text
							+ '</h3>' + '</div>').show();
		},
		"load" : function(obj, text) {
			var _text = text || "正在加载中，请稍候...";
			$(obj).html(
					'<div class="msg-hint">' + '<h3 title=' + _text
							+ '><i class="hint-loader"></i>' + _text + '</h3>'
							+ '</div>').show();
		},
		"inf" : function(obj, text) {
			var _text = text || "数据提交中，请稍等...";
			$(obj).html(
					'<div class="msg-hint">' + '<h3 title=' + _text
							+ '><i class="hint-icon hint-inf-s"></i>' + _text
							+ '</h3>' + '</div>').show();
		},
		"errorInfo" : function(obj, text) {
			var _text = text || "数据提交失败!";
			$(obj)
					.html(
							'<div class="ui-tiptext-container ui-tiptext-container-message"><p class="ui-tiptext ui-tiptext-message">'
									+ '<i class="ui-tiptext-icon icon-message" title="阻止"></i>'
									+ _text + '</p>' + '</div>').show();
		}
	}
};
Util.browser = {
	/**
	 * 获取URL地址栏参数值
	 * name 参数名
	 * url [optional,default=当前URL]URL地址
	 * @return {String} 参数值
	 */
	getParameter : function(name, url) {
		var paramStr = url || window.location.search;
		paramStr = paramStr.split('?')[1];
		if ((!paramStr)||paramStr.length == 0) {return null;}
		var params = paramStr.split('&');
		for ( var i = 0; i < params.length; i++) {
			var parts = params[i].split('=', 2);
			if (parts[0] == name) {
				if (parts.length < 2 || typeof (parts[1]) === "undefined"
						|| parts[1] == "undefined" || parts[1] == "null")
					return '';
				return parts[1];
			}
		}
		return null;
	}
};
/**
 * 常用正则表达式
 */
Util.validate = {
	/**
	 * 格式校验方法
	 * 
	 * @method Check
	 * @param {String}
	 *            type 验证类型
	 * @param {String}
	 *            value 验证值
	 */
	Check : function(type, value) {
		var _reg = this.regexp[type];
		if (_reg == undefined) {
			alert("Type " + type + " is not in the data");
			return false;
		}
		var reg;
		if (typeof _reg == "string") {
			reg = new RegExp(_reg);
		} else if ((typeof _reg) == "function") {
			return _reg(value);
		} else {
			reg = _reg[type];
		}
		return reg.test(value);
	}
};

/*
*	分页
*/
Util.pagination = function( pindex , onepage , obj , formStr ){
    var pageIndex = pindex;
    var pageParams = obj;
    var str = formStr; //form序列化的数据 
	pageParams.page_index = pindex;   //弹出窗口修改数据后，刷新当前页的数据需要用到这些数据.
	pageParams.page_params = formStr;
    Util.ajax.postJson( pageParams.url ,'pageIndex='+(pageIndex+1)+'&pageSize='+pageParams.items_per_page+'&'+str , function(json,state){
		if (pageParams.pagination instanceof jQuery) {
			var _page = pageParams.pagination;
		}else{
			var _page = $("#"+pageParams.pagination);
		}
    	var _jcontrol = $("#J_table_control");
		if(state){
			if (pageParams.tablewrap instanceof jQuery) {
				if (typeof(pageParams.tabletpl) == 'function'){
					var template = pageParams.tabletpl;
					pageParams.tablewrap.html(template(json));
				}else{
					var template = Handlebars.compile(pageParams.tabletpl.html());
					pageParams.tablewrap.html(template(json));
				}
			}else{
	            Util.ajax.loadTemp('#'+pageParams.tablewrap,$('#'+pageParams.tabletpl),json);//加载模板
			}
            //触发回调函数
            if (typeof obj.pageCallback == 'function') {
                obj.pageCallback.call(_page, json);
            }
			//分页调用-只初始化一次  
	        if( onepage ){
	    		if(json.page.totalCount<1){
	    			_jcontrol.hide();
	    			
	    			_page.html('<p class="ui-tiptext ui-tiptext-warning">'+
							    '<i class="ui-tiptext-icon" title="警告"></i>'+
							    '没有查询到数据,请更换查询条件!'+
								'</p>');
	    			_page.next().hide();
                    _page.prev().hide();
	    		}else{
	    			_jcontrol.show();
		            _page.pagination( json.page.totalCount , {
		                'items_per_page'      : pageParams.items_per_page,
		                'current_page': 0 ,
		                'num_display_entries' : 3,
		                'num_edge_entries'    : 1,  
		                'link_to': '#tradeRecordsIndex' ,
		                'prev_text'           : "<",  
		                'next_text'           : ">",  
		                'call_callback_at_once' : false,  //控制分页控件第一次不触发callback.
		                'callback'            : function(page_index, jq){  
													Util.pagination(page_index , false , pageParams , str );  
												}  
		            });
		            if(pageParams.ChangeLimit){
			            var jsonStr = JSON.stringify(obj);
			            var paginationHtml = "<span style='color:#999;'>每页显示&#160;&#160;</span><select style='border-color:#ccc;' id='changeLimit' onchange='backPagination(this,"+pindex+","+onepage+","+jsonStr+",\""+formStr+"\")'>"+
			            "<option value='10'>10</option>"+
			            "<option value='20'>20</option>"+
			            "<option value='30'>30</option>"+
			            "<option value='40'>40</option>"+
			            "<option value='50'>50</option></select><span style='color:#999;'>&#160;&#160;条</span><span style='padding-left:15px; color:#999;'>共"+json.bean.total+"条</span>";
			            _page.next().html(paginationHtml).show();
			            $.each($('#changeLimit').find("option"),function(i){
			            	if($(this).val() == pageParams.items_per_page){
			            		$(this).attr("selected",true);
			            	}
			            });
		            }else{
		            	_page.next().text("共"+json.page.totalCount+"条").show();
		            }
		            if(_page.prev().length>0){
		            	_page.prev().show();
		            }
	    		}
	        }
		}else{
			var _errorMsg = json.returnMessage ? ('查询数据失败！原因：'+json.returnMessage) : '加载数据失败,请稍后再试!' ;
			_page.html('<p class="ui-tiptext ui-tiptext-warning">'+
				    '<i class="ui-tiptext-icon" title="警告"></i>'+
				    ''+_errorMsg+
					'</p>');
			_jcontrol.hide();
			_page.next().hide();
            _page.prev().hide();
		}
	});
};
function backPagination($obj,pindex,onepage,obj,formStr){
	obj.items_per_page = $($obj).val();
	Util.pagination(pindex,onepage,obj,formStr);
}
/*
 * 加载中弹出层
 */
Util.loading = {
	create:function(obj,text){
		text = text?text:'正在加载中，请稍候...';
		this.loading(obj,text);
	},
	loading:function(obj,text){
		var $obj = null;
		if (typeof(obj) == 'string'){
			$obj = $(obj);
		}else{
			$obj = obj;
		}
		if ($obj && $obj.length){
			$obj.block && $obj.block({
	            message: '<div class="fn-loading">'+text+'</div>', 
	            css: { border:'1px solid #DDD', padding:"10px 20px",textAlign:"left",width:'20%'},
	            overlayCSS:{
	                backgroundColor: '#333', 
	                opacity:  0.2, 
	                cursor: 'wait' 
	            }
	        }) || console.log('Util.loading.loading 缺少$.block的引用，请联系组件维护人员');
		} else{
			console.log('Util.loading.loading $obj对象异常');
			console.log($obj);
		}
	},
	close:function(obj){
		var $obj = null;
		if (typeof(obj) == 'string'){
			$obj = $(obj);
		}else{
			$obj = obj;
		}
		if($obj && $obj.length){
			$obj.unblock && $obj.unblock() || console.log('Util.loading.close 缺少$.block的引用，请联系组件维护人员');
		}else{
			console.log('Util.loading.close $obj对象异常');
			console.log($obj);
		}
	}
};


/*
*	窗口控制
*	如果在页面嵌入的iframe中打开dialog，需要在params中传入top
*	top: 顶层页面
*/
Util.dialog = {
	openDiv: function(params){
		if (params.top) {
			if (top.dialog.get(params.id)) {
				top.dialog.get(params.id).remove();
			};
			var d = params.top.dialog({
				id:params.id,
				fixed: true,
				// quickClose: true,	//点击空白处弹出框消失
			    title: params.title,
			    content: params.content,
			    okValue: params.okVal,
		        ok: params.okCallback,
		        cancelValue: params.cancelVal,
		        cancel: params.cancelCallback,
		        onclose: params.closeCallback	//关闭对话框回调函数
			});
		}else{
			var d = dialog({
				id:params.id,
				fixed: true,
				// quickClose: true,	//点击空白处弹出框消失
			    title: params.title,
			    content: params.content,
			    okValue: params.okVal,
		        ok: params.okCallback,
		        cancelValue: params.cancelVal,
		        cancel: params.cancelCallback,
		        onclose: params.closeCallback	//关闭对话框回调函数
			});
		}
		d.width(params.width);
		d.height(params.height);
		if (params.modal) {
			d.showModal();
		}else{
			d.show();
		}
		return d;
	},
	tips: function(content,top, delay){
		if (top) {
			var d = top.dialog({
				fixed: true,
				quickClose: true,	//点击空白处弹出框消失
			    content: content
			});
		}else{
			var d = dialog({
				fixed: true,
				quickClose: true,	//点击空白处弹出框消失
			    content: content
			});
		}
		d.show();
		setTimeout(function () {
		    d.close().remove();
		}, delay || 2000);
	},
	confirm: function(params){
		if (params.top) {
			var d = params.top.dialog({
				id:'D_confirm',
	        	title: '提示',
				fixed: true,
			    content: params.content,
			    okValue: params.okVal?params.okVal:'确认',
		        ok: params.okCallback,
		        cancelValue: params.cancelVal?params.cancelVal:'取消',
		        cancel :function(){
		            return;
		        }
			});	
		}else{
			var d = dialog({
				id:'D_confirm',
	        	title: '提示',
				fixed: true,
			    content: params.content,
			    okValue: params.okVal?params.okVal:'确认',
		        ok: params.okCallback,
		        cancelValue: params.cancelVal?params.cancelVal:'取消',
		        cancel :function(){
		            return;
		        }
			});	
		}
		d.showModal();
	},
	close: function(id,top){
		if (top) {
	        top.dialog.get(id).close();
		}else{
	        dialog.get(id).close();
		}
	}, 
	bubble:function(arguments){
		//console.log(typeof(arguments));
		/* var d = dialog({
		    content: 'Hello World!',
		    quickClose: true// 点击空白处快速关闭
		}); */
		var d = null;
		if (typeof(arguments) === 'object' && arguments.element){
			arguments.content = arguments.content || '没有内容';
			arguments.quickClose = arguments.quickClose == null ? true : arguments.quickClose ;
			d = dialog(arguments);
			d.show(arguments.element.length ? arguments.element[0] :arguments.element);
		}else{
			d.show();
		}
		
	}, 
	zTree:function(userSetting){
		var d = null;
		var zTree = null;
		var params = {
			id : 'd1',    //弹出对话框的id
			skin:'zx-popup-tree', 
			fixed:true, 
			modal: userSetting.modal,
			padding: 3, 
			title : '提示信息', //左上角提示标题
			//content : $('#dialogContent').html(), //具体提示内容
			content:'<ul id="treeDemo" class="ztree"></ul>', 
			button: [
				{
					value: '确定',
					callback: function () {
						var nodes = zTree.getSelectedNodes();
						if (userSetting && userSetting.onSelect){
							return userSetting.onSelect(nodes[0]);
						}
						//return false;
					},
					autofocus: true
				},
				{
					value: '取消',
					callback: function () { }
				}
			], 
			width : userSetting.width || '270',  //对话框宽度
			height : userSetting.height || '400'  //对话框高度
		}
		if (userSetting && userSetting.top){
			d = new top.dialog(params);
		}else{
			d = new dialog(params);
		}
		d.show();
		
		var setting = { 
			data:{
				key:{
					//name:'name1'
				}
			}
		};
		_.extend(setting, userSetting);
		if (userSetting && userSetting.dataUrl){
			Util.ajax.postJson(userSetting.dataUrl,userSetting.param || {},function(json,status){
	            if (status) {
	            	zTree = $.fn.zTree.init($('.ztree', d.node), setting, json.object || json.beans);
	            }else{
	                Util.dialog.tips(json.returnMessage||'查询失败，请重试！');
	            }
	            var valueKey = (userSetting && userSetting.data 
	            	&& userSetting.data.key && userSetting.data.key.value) || 'value';
	            var node = zTree.getNodeByParam(valueKey, userSetting.defaultValue);
            	zTree.selectNode(node);
	            
	        })
		}else{
			zTree = $.fn.zTree.init($('.ztree', d.node), setting, userSetting.zNodes);
			zTree.selectNode(zTree.getNodeByParam('value', userSetting.defaultValue));
		}
		
		
		//return zTree;
	}
};


/*
 * 功能:删除数组元素.
 * 返回:在原数组上删除后的数组
 */
Util.Arrays = {
	// 参数:dx删除元素的下标.
	removeByIndex : function(arrays , dx){
		if(isNaN(dx)||dx>arrays.length){return false;}
		for(var i=0,n=0;i<arrays.length;i++){
			if(arrays[i]!=arrays[dx]){
				arrays[n++]=arrays[i]
			}
		}
		arrays.length-=1
		return arrays;
	},
	//删除指定的item,根据数组中的值
	removeByValue : function(arrays, item ){
		for( var i = 0 ; i < arrays.length ; i++ ){
			if( item == arrays[i] ){
				break;
			}
		}
		if( i == arrays.length ){return;}
		for( var j = i ; j < arrays.length - 1 ; j++ ){
			arrays[ j ] = arrays[ j + 1 ];
		}
		arrays.length--;
		return arrays;
	}
};

/**
 * cookie 操作，设置，取出，删除
 *
 * @namespace Rose
 * @class string
 */
Util.cookie = {
	/**
	 * 显示当前对象名称路径
	 * 
	 * @method toString
	 * @return {String} 'Rose.string'
	 */
	toString : function() {
		return 'Rose.cookie';
	},  
    /**
	 * 设置一个cookie
	 * @method set
	 * @param {String} name cookie名称
	 * @param {String} value cookie值
	 * @param {String} path 所在路径
	 * @param {Number} expires 存活时间，单位:小时
	 * @param {String} domain 所在域名
	 * @return {Boolean} 是否成功
	 */
    set : function(name, value, expires, path, domain) {
       	var str = name + "=" + encodeURIComponent(value);
   		if (expires != undefined && expires != null && expires != '') {
   			if (expires == 0) {expires = 100*365*24*60;}
   			var exp = new Date();
   			exp.setTime(exp.getTime() + expires*60*1000);
   			str += "; expires=" + exp.toGMTString();
   		}
   		if (path) {
   			str += "; path=" + path;
   		} else {
   			str += "; path=/";
   		}
   		if (domain) {str += "; domain=" + domain;}
   		document.cookie = str;
    },
    /**
	 * 获取指定名称的cookie值
	 * @method get
	 * @param {String} name cookie名称
	 * @return {String} 获取到的cookie值
	 */
	get : function(name) {
		var v = document.cookie.match('(?:^|;)\\s*' + name + '=([^;]*)');
		return v ? decodeURIComponent(v[1]) : null;
	},
	/**
	 * 删除指定cookie,复写为过期
	 * @method remove 
	 * @param {String} name cookie名称
	 * @param {String} path 所在路径
	 * @param {String} domain 所在域
	 */
	remove : function(name, path, domain) {
		document.cookie = name + "=" +
			((path) ? "; path=" + path : "") +
			((domain) ? "; domain=" + domain : "") +
			"; expires=Thu, 01-Jan-70 00:00:01 GMT";
	}
};

//将Util对象注册为符合AMD规范的模块，可使用requireJS模块化加载
if (typeof define === "function" && define.amd) {
    define('Util',[], function () {
    	return Util;
    });
}

