$(document).ready(function() {
	
	//var year=$("#year").find("option:selected").text();
	var currentTime=new Date();
	jQuery.ajax({
		url :  "${ctx}/system/daikuaninfo/daxue/ajax/findExceptionByMonth?year="+currentTime.getFullYear(),
		type : "post",
		cache : false,
		scriptCharset : "utf-8",
		async : false,
		dataType : "json",
		success : function(data) {
			require.config({

				paths : {
					echarts : '${ctx}/js/echarts/echarts',
					'echarts/chart/bar' : '${ctx}/js/echarts/bar'
				}
			})
			/***/
			require([ 'echarts', 'echarts/chart/bar' ], function(ec) {
				//--- 折柱 ---
				var myChart = ec.init(document.getElementById('placeholder'));
				myChart
						.setOption({
							tooltip : {
								trigger : 'axis'
							},
							legend : {
								data : [ '异常签到' ]
							},
							toolbox : {
								show : true,
								feature : {
									mark : {
										show : true
									},
									dataView : {
										show : true,
										readOnly : false
									},
									magicType : {
										show : true,
										type : [ 'line', 'bar' ]
									},
									restore : {
										show : true
									},
									saveAsImage : {
										show : true
									}
								}
							},
							calculable : true,
							xAxis : [ {
								type : 'category',
								data : [ '1月', '2月', '3月', '4月', '5月',
										'6月', '7月', '8月', '9月', '10月',
										'11月', '12月' ]
							} ],
							yAxis : [ {
								type : 'value',
								splitArea : {
									show : true
								}
							} ],
							series : [
									{
										name : '异常签到',
										type : 'bar',
										data : data
									} ]

						});
			
			/******************/
		});
			
		}
		});
	});