jQuery("document").ready(function() {
	jQuery("#_imgSpacse").hide();
	layer.use('/extend/layer.ext.js');
});
var _urlObj;
var _urlId;
var _urlTd;
var _imgId;
var _showType;
var _orderBy;
var _a_index;
var _callBack;
/**
 * 显示图片空间
 */
function uploadImg(urlObj, urlId, urlTd, imgId) {
	_callBack = null;
	_urlObj = urlObj;
	_urlId = urlId;
	_urlTd = urlTd;
	_imgId = imgId;
	findDirc('', '', '');
	/*_a_index = jQuery.layer({
		type : 1,
		title : '图片空间',
		fix : true,
		zIndex : 19891014,
		fadeIn : 300,
		shade : [ 0.5, '#000' ],
		area : [ '600px', 'auto' ],
		page : {
			dom : '#_imgSpacse'
		},
		success : function(layero) {

		}
	});*/
	
	_a_index=layer.open({
	    type: 1,
	    content:$('#_imgSpacse'),
	    shade: [0.5, '#000'],
	    title: ['图片空间'],
	    area: [ '600px', 'auto' ],
	    shift: 4,
	    success: function(obj,index){
	    	
	    }
	});

}
/**
 * 带回调函数
 * 
 * @param urlObj
 * @param urlId
 * @param urlTd
 * @param imgId
 * @param callBack
 */
function uploadImgCallBack(urlObj, urlId, urlTd, imgId, callBack) {
	_urlObj = urlObj;
	_urlId = urlId;
	_urlTd = urlTd;
	_imgId = imgId;
	_callBack = callBack;
	findDirc('', '', '');
	_a_index = jQuery.layer({
		type : 1,
		title : '图片空间',
		fix : false,
		zIndex : 19891014,
		fadeIn : 300,
		offset : [ '30px', '30px' ],
		shade : [ 0.5, '#000' ],
		area : [ '600px', 'auto' ],
		page : {
			dom : '#_imgSpacse'
		},
		success : function(layero) {
		}
	});
}
/**
 * 遍历文件夹
 * 
 * @param showType
 * @param orderBy
 * @param dircPath
 */
function findDirc(showType, orderBy, dircPath) {
	var t_index = layer.load(3);
	_showType = showType;
	_orderBy = orderBy;
	var par = {
		"showType" : showType,
		"orderBy" : orderBy,
		"dircPath" : dircPath
	};
	jQuery.ajax({
		type : "POST",
		url : ctx + "/common/upload/findDirc",
		async : false,
		data : par,

		success : function(data) {
			clear();
			if (data != null && typeof (data) != 'undefined') {
				jQuery("#_parentPath").val(data['parentName']);
				jQuery("#_currPath").val(data['currName']);
				for ( var i = 0; i < data['files'].length; i++) {
					add(data['files'][i], showType, orderBy);
				}
			}
			layer.close(t_index);
		}

	});
}
/**
 * 加入文件夹和图片
 * 
 * @param json
 * @param showType
 * @param orderBy
 */
function add(json, showType, orderBy) {

	var dircPath = json['path'];
	var hreftr = "<li>";
	if (json['isDir'] == "0" || json['isDir'] == 0) {
		hreftr = hreftr + "<a href='####' title='" + json['fileName'] + "'"
				+ "data-rel='colorbox' alt='" + json['imgUrl']
				+ "' onclick=chooseImg(this);>";
	} else {
		hreftr = hreftr + "<a href='####' title='" + json['fileName'] + "'"
				+ "data-rel='colorbox' onclick=findDirc('" + orderBy + "','"
				+ showType + "','" + dircPath + "');>";
	}
	hreftr = hreftr
			+ "<p><img alt='150x150' style='width:80px;height:80px' src='"
			+ ctx + "/" + json['imgUrl'] + "'/></p><p>" + json['fileName']
			+ "</P>";
	hreftr = hreftr + "</a></li>"
	jQuery("#_space").append(hreftr);

	// layer.autoArea(_a_index);
}
/**
 * 清空
 */
function clear() {
	jQuery("#_space").empty();
}
/**
 * 返回上级
 */
function backDirc(showType, orderBy) {
	var dircPath = jQuery("#_parentPath").val();
	findDirc(showType, orderBy, dircPath);
}
/**
 * 创建文件夹
 */
function makdir(showType, orderBy) {
	var dircPath = jQuery("#_currPath").val();
	var ttt = layer.prompt({
		title : '输入文件夹名称，并确认',
		type : 0
	}, function(fileName) {
		createDirc(fileName, dircPath, showType, orderBy);
		layer.close(ttt)
	});
}
/**
 * 删除文件夹
 * 
 * @param showType
 * @param orderBy
 */
function removedir(showType, orderBy) {
	var dircPath = jQuery("#_currPath").val();
	var par = {
		"dircName" : dircName,
		"dircPath" : dircPath
	};
	jQuery.ajax({
		type : "POST",
		url : ctx + "/common/upload/removeDirc",
		data : par,
		dataType : 'html',
		success : function(data) {
			alert(data);
		}

	});

}
/**
 * 创建文件夹
 * 
 * @param dircName
 * @param dircPath
 * @param showType
 * @param orderBy
 */
function createDirc(dircName, dircPath, showType, orderBy) {
	var par = {
		"dircName" : dircName,
		"dircPath" : dircPath
	};
	jQuery.ajax({
		type : "POST",
		url : ctx + "/common/upload/createDirc",
		data : par,
		dataType : 'html',
		success : function(data) {
			if (data != "") {
				alert(data);
			} else {
				findDirc(showType, orderBy, dircPath);
			}
		}

	});
}

function validateImage(th, obj) {
	var file = jQuery(th).parent("div").find("#" + obj);
	var tmpFileValue = file.val();
	// 校验图片格式
	if (/^.*?\.(gif|png|jpg|jpeg|bmp)$/.test(tmpFileValue.toLowerCase())) {
		return true;
	} else {
		alert("只能上传jpg、jpeg、png、bmp或gif格式的图片！");
		return false;
	}

	if (file.val() === "") {
		alert("请选择上传的文件!");
		return false;
	}
}
/**
 * 上传
 */
function uploadFileImg(th, fileId) {
	var dircPath = jQuery("#_currPath").val();
	if (validateImage(th, fileId)) {
		jQuery.ajaxFileUpload({
			url : ctx + '/common/upload/uploadImage?dircPath=' + dircPath, // 需要链接到服务器地址
			secureuri : false,// 是对POST数据的加密设置
			fileElementId : "_fileUpload", // 文件选择框的id属性
			dataType : 'json', // 服务器返回的格式，可以是json，IE中如果返回的不是JOSN会出错，火狐不出错
			success : function(data, textStatus) { // 相当于java中try语句块的用法
				data = eval("(" + data + ")");
				if (typeof (_callBack) == "function") {
					_callBack(data['imgUrl'], _urlObj);
				} else {
					// alert(data.imgUrl);
					jQuery(_urlObj).parent(_urlTd).find("#" + _urlId).val(
							"/"+data['imgUrl']);
					jQuery(_urlObj).parent(_urlTd).find("#" + _imgId).attr(
							"src", ctx + "/" + data['imgUrl']);
					findDirc(_showType, _orderBy, dircPath);
				}
			},
			error : function(data, status, ex) { // 相当于java中catch语句块的用法
				if (typeof (_callBack) == "function") {
					_callBack(imgUrl, _urlObj);
				} else {
					data = eval("(" + data + ")");
					// alert(data.imgUrl);
					jQuery(_urlObj).parent(_urlTd).find("#" + _urlId).val(
							"/"+data['imgUrl']);
					jQuery(_urlObj).parent(_urlTd).find("#" + _imgId).attr(
							"src", ctx + "/" + data['imgUrl']);
					findDirc(_showType, _orderBy, dircPath);
				}
			}
		});
	}
}

function chooseImg(th) {
	var imgUrl = jQuery(th).attr("alt");
	if (typeof (_callBack) == "function") {
		_callBack(imgUrl, _urlObj);
	} else {
		jQuery(_urlObj).parent(_urlTd).find("#" + _urlId).val("/"+imgUrl);
		jQuery(_urlObj).parent(_urlTd).find("#" + _imgId).attr("src",
				ctx + "/" + imgUrl);
	}
	layer.close(_a_index)
}