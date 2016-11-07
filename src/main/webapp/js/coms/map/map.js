jQuery("document").ready(function() {
	jQuery("#_mapdialog").hide();
});
var _map;
var _a_index;
function buildMap(xId, yId, callback) {
	// 初始化坐标
	_map = new BMap.Map("_allmap");
	// 设置地图中心店
	var point = new BMap.Point(113.66028, 34.760999);

	// 设置放大缩小级别
	_map.centerAndZoom(point, 13);
	//根据浏览器自动选择城市
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r) {
		if (this.getStatus() == BMAP_STATUS_SUCCESS) {
			var mk = new BMap.Marker(r.point);
			_map.addOverlay(mk);
			_map.panTo(r.point);
		} else {
			alert('failed' + this.getStatus());
		}
	}, {
		enableHighAccuracy : true
	})
	// 添加平移缩放控件
	_map.addControl(new BMap.NavigationControl());
	// map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
	// 添加缩略地图控件
	_map.addControl(new BMap.OverviewMapControl());
	// 启用滚轮放大缩小
	_map.enableScrollWheelZoom();
	// 添加地图类型控件
	_map.addControl(new BMap.MapTypeControl());
	var geoc = new BMap.Geocoder(); 
	// 启用点击设置坐标
	getPoint(_map, xId, yId,callback,geoc);
	// 添加地图类型控件
	function myFun(result) {
		var cityName = result.name;
		_map.setCenter(cityName);
	}
	var myCity = new BMap.LocalCity();
	myCity.get(myFun);

}
function search() {
	var local = new BMap.LocalSearch(_map, {
		renderOptions : {
			map : _map
		}
	});
	var address = jQuery("#_seachrAddress").val();
	if (address != "" && address != null) {
		local.search(address);
	}
}

function showMap(xId, yId, callback) {
	_a_index = jQuery.layer({
		type : 1,
		title : '选取地图坐标',
		fix : false,
		zIndex : 19891014,
		fadeIn : 300,
		shade : [ 0.5, '#000' ],
		area : [ '600px', '500px' ],
		page : {
			dom : '#_mapdialog'
		},
		success : function(layero) {
			buildMap(xId, yId, callback);
		}
	});

}

function getPoint(t, xId, yId, callback,geoc) {
	t.addEventListener("click", function(e) {
		jQuery("#" + xId).val(e.point.lng);
		jQuery("#" + yId).val(e.point.lat);
		var pt = e.point;
		geoc.getLocation(pt, function(rs){
			var addComp = rs.addressComponents;
			if (typeof (callback) == "function") {
				callback(addComp);
				layer.close(_a_index);
			}
		});  
		
	});
}