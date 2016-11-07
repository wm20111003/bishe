jQuery("document").ready(function() {
	ajaxmenu();
});

function mynavhref(_url,_id,_name){
	jQuery("a[id^='href_']").parent("li").removeClass("active");
	var _li=jQuery("#href_"+_id).parent("li");

	_li.addClass("active");
	myhref(_url);

	jQuery("#johnNav").html('当前位置<i class="ace-icon fa fa-angle-double-right"></i> '+_name);
	
}



/**
 * 获取所有导航资源
 */
function ajaxmenu() {

	jQuery.ajax({
		url : ctx + "/system/menu/leftMenu",
		type : "post",
		cache : false,
		async : false,
		scriptCharset : "utf-8",
		dataType : "json",
		success : function(_json) {
			if(_json.status=="success"){
				buildModule(_json.data);
			}
		}
	});
}

function buildModule(data) {
	if (data != null && typeof (data) != "undefined") {
	
		for ( var i = 0; i < data.length; i++) {
			var html = '';
			
			
			html = getParentModule(data[i]);
			
			jQuery("#module").append(html);
		}
	}
}

function getParentModule(json) {
	
	var _leaf=json["leaf"];
	
	var t = '<li><a class="dropdown-toggle" ';
	if(_leaf&&_leaf.length>0){
		t =t+ ' href="javascript:void(0)">';
	}else{
		t =t+ '  id=\'href_' +json["id"] + '\' href="javascript:mynavhref(\'' + ctx+json["pageurl"] + '\',\'' +json["id"] + '\',\'' +json["name"] + '\');">';
		//t =t+ ' data-trigger="ajax"  data-target="#ajax_target"   href="' +ctx+ json["pageurl"] + '">';
		
	}

	
	
	t=t+'<i class="menu-icon fa '+(json["icon"]?json["icon"]:"fa-list")+'"></i><span class="menu-text">'+ json["name"]+'</span>';

	if(_leaf&&_leaf.length>0){
	t=t+'<b class="arrow fa fa-angle-down"></b></a><b class="arrow"></b>';
	var m = "<ul class='submenu'>";
	
	for ( var i = 0; i < _leaf.length; i++) {
		var n = getChindModule(_leaf[i], '');
		m = m+ n;
	}
	t = t + m + "</ul>";

	}else{
		
		t = t +"</a>";
		
	}
	
	

t= t+"</li>";

	return t;
}

function getChindModule(json, html) {
	
	var _leaf=json["leaf"];
	var t = '<li><a  ';
	if(_leaf&&_leaf.length>0){
		t =t+ ' class="dropdown-toggle" href="####">';
	}else{
		t =t+ '  id=\'href_' +json["id"] + '\' href="javascript:mynavhref(\'' + ctx+json["pageurl"] + '\',\'' +json["id"] + '\',\'' +json["name"] + '\');">';
		//t =t+ ' data-trigger="ajax"  data-target="#ajax_target"  href="' +ctx+ json["pageurl"] + '">';
	}
	t=t+'<i class="menu-icon fa fa-caret-right"></i>'+ json["name"];
	
	if (_leaf&& _leaf.length > 0) {
		t=t+'<b class="arrow fa fa-angle-down"></b></a><b class="arrow"></b>';
		var m = "<ul class='submenu'>";
		for ( var i = 0; i < _leaf.length; i++) {
			var n = getChindModule(_leaf[i], '');
			m = m + n;
		}
		m = m + "</ul>";
		t=t+m+"</li>";
	}else{
		t=t+'</a>	<b class="arrow"></b></li>';
		
	}
	html = html + t;
	return html;
	
	
}
function openUrl(_url) {
	if(_url=="####"){
		return;
	}
	window.location.href = _url;
}
function openUrlctx(_url) {
	
	if(_url=="####"){
		return;
	}
	window.location.href = ctx+_url;

}

