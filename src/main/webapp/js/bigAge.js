function findBigAge(year,startAge,endAge) {
	var par = {
		"year" : year,
		"start" : startAge,
		"endAge" : endAge

	};
	jQuery.ajax({
		url : ctx+"/system/daikuaninfo/daxue/ajax/findBigAge",
		type : "post",
		cache : false,
		scriptCharset : "utf-8",
		async : false,
		dataType : "json",
		data : par,
		success : function(data) {

			/**
			 * pie
			 * 
			 */
			require.config({

				paths : {
					echarts : ctx+'/js/echarts/echarts',
					'echarts/chart/pie' : ctx+'/js/echarts/echarts'
				}
			});
			require([ 'echarts', 'echarts/chart/pie' ], function(ex) {

				// --- 折柱 ---

				var myChart1 = ex.init(document.getElementById("school"));
				var option = {
					title : {
						text : '签收大龄学校对比图',
						x : 'center'
					},
					tooltip : {
						trigger : 'item',
						formatter : "{a} <br/>{b} : {c} ({d}%)"
					},
					legend : {
						orient : 'vertical',
						x : 'left',
						itemGap:20,
						data : data.schoolName
					},
					toolbox : {
						show : true,
						feature : {
							mark : {
								show : true
							},
							
							grid : {
								x : 0,
								y : 0,
								x2 : 0,
								y2 : 0
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
					series : [ {
						name : '签收大龄学校对比',
						type : 'pie',
						radius : '55%',
						center : [ '50%', '60%' ],
						itemStyle:{
							normal:{label:{show:false},labelLine:{show:false}},
							emphasis:{label:{show:true,position:'outer'},labelLine:{show:true,lineStyle:{color:'red'}}}
			
						},
						data : data.datas
					} ]
				};

				myChart1.setOption(option);

			});

		}
	});
}