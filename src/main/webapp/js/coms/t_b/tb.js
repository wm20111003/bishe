jQuery(function() {
	findBigType();
});
var _callBack;
function _tb_init(bigType,smallType,brand){
	jQuery("#_tb_m").find("#bigType").val(bigType);
	findSmallType();
	jQuery("#_tb_m").find("#smallType").val(smallType);
	findBrand();
	jQuery("#_tb_m").find("#brand").val(brand);
}
function setCallBack(call){
	_callBack=call;
	
}
function findBigType() {
	jQuery.ajax({
		type : "POST",
		url : ctx + "/common/ajax/findBigType",
		async : false,
		success : function(data) {
			var i = 0;
			jQuery("#_tb_m").find("#bigType").empty();
			jQuery("#_tb_m").find("#bigType").append("<option value=''>请选择产品大类</option>");
			for (i = 0; i < data['data'].length; i++) {
				var strHtml = "<option value='" + data['data'][i]['id'] + "'>"
						+ data['data'][i]['name'] + "</option>";
				jQuery("#_tb_m").find("#bigType").append(strHtml);
			}
		}
	});
}
function findSmallType() {
	var parentId = jQuery("#bigType").val();
	var par = {
		"parentId" : parentId
	};
	jQuery.ajax({
		type : "POST",
		url : ctx + "/common/ajax/findSmallType",
		async : false,
		data : par,
		success : function(data) {
			var i = 0;
			jQuery("#_tb_m").find("#smallType").empty();
			jQuery("#_tb_m").find("#smallType").append("<option value=''>请选择产品小类</option>");
			for (i = 0; i < data['data'].length; i++) {
				var strHtml = "<option value='" + data['data'][i]['id'] + "'>"
						+ data['data'][i]['name'] + "</option>";
				jQuery("#_tb_m").find("#smallType").append(strHtml);
			}
		}
	});
}

function findBrand() {
	var parentId = jQuery("#smallType").val();
	var par = {
		"typeId" : parentId
	}; 
	if(typeof(_callBack)=="function"){
		_callBack(parentId);
	}
	jQuery.ajax({
		type : "POST",
		url : ctx + "/common/ajax/findBrand",
		async : false,
		data : par,
		success : function(data) {
			var i = 0;
			jQuery("#_tb_m").find("#brand").empty();
			jQuery("#_tb_m").find("#brand").append("<option value=''>请选择产品品牌</option>");
			for (i = 0; i < data['data'].length; i++) {
				var strHtml = "<option value='" + data['data'][i]['id'] + "'>"
						+ data['data'][i]['name'] + "</option>";
				jQuery("#_tb_m").find("#brand").append(strHtml);
			}
		}
	});
}