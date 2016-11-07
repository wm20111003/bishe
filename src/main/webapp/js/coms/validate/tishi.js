// JavaScript Document
//此函数与validform结合使用,实现输入框上方弹出提示框
//msgid为当前显示区域id名  msg为validform中tiptype中的msg
//未通过验证时显示提示框
function showTooltips(obj, msg) {
	if (obj == null) {
		return;
	}
	// if (obj.id==null){ return; }
	if (msg == '') {
		msg = 'Error!';
	}
	$(obj)
			.prepend(
					"<div class='for_fix_ie6_bug' style='position:relative;'><div class='tooltips_main'><div class='tooltips_box'><div class='tooltips'><div class='msg'>"
							+ msg
							+ "</div></div><div class='ov'></div></div></div></div>");
	$(obj).find(".tooltips_main").fadeIn("slow").animate({
		marginTop : "-30px"
	}, {
		queue : true,
		duration : 400
	});
	//新增，将--->提示信息x<--隐藏
	$("#Validform_msg").attr("style","display:none");
	// $('#'+msgid+' .tooltips_main').fadeIn("slow").animate({ marginTop:
	// "-30px"}, {queue:true, duration:400});
}
// 通过验证后隐藏提示框
function hideTooltips(obj) {
	try {
		$(obj).find('.tooltips_main').fadeOut("slow");
		$(obj).find('.tooltips_main').remove();
	} catch (e) {
	}
}
// 隐藏所有提示框
function hideAllTooltips() {
	try {
		$('.tooltips_main').fadeOut("slow");
		$('.tooltips_main').remove();
		//ouyang 添加
		$("#Validform_msg").remove();
	} catch (e) {
	}
}

// -----------------------------------------------------------validform
// ------------
function myValidataForm(myform) {
	if (myform == null) {
		myform = '#updateForm';
	} // 验证
	var demo = jQuery(myform).Validform({
		// btnSubmit:"#btn_add",
		tiptype : function(msg, o, cssctl) {
			// msg：提示信息;
			// o:{obj:*,type:*,curform:*},
			// obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4，
			// 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
			// cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
			if (!o.obj.is("form")) {// 验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
				var objtip = o.obj.siblings(".span_validate");
				if (objtip.length == 0) {
					$(o.obj).before("<span class='span_validate'></span>");
					var objtip = o.obj.siblings(".span_validate");
				}
				hideTooltips(objtip);
				showTooltips(objtip, msg);
				// cssctl(objtip,o.type);
				if (o.type == 2) {
					hideTooltips(objtip);
				}
			}
		},
		label : ".label",
		showAllError : true,
		datatype : {
			"zh1-6" : /^[\u4E00-\u9FA5\uf900-\ufa2d]{1,6}$/
		},
		postonce : true,
		ajaxPost : false,
		callback : function(data) {
			if (data.status == "y") {

			}
		}

	});
	return demo;
}

// ----------------------校验后提交
function validateSubmit(myformId, listurl) {
	//debugger;
	if (myformId == null) {
		myformId = "updateForm";
	}
	var demo = $("#" + myformId).Validform();
	if (demo.check()) {
		if (typeof commonUpdateForm != 'undefined'
				& commonUpdateForm instanceof Function) {
			commonUpdateForm(myformId, listurl);
		} else {
			return false;
		}
	} else {
		return false;
	}
}

function isNull(t) {
	if (t == null || t == "" || typeof (t) == "undefined") {
		return true;
	}
	return false;
}

function isNotNull(t) {
	if (t == null || t == "" || typeof (t) == "undefined") {
		return false;
	}
	return true;
}
function isFloat(v) {
	var reg = /^[0-9]*\.?[0-9]*$/;

	if (!reg.test(v)) {
		return false;
	}
	return true;
}