function findBar(year){
jQuery.ajax({
	url : ctx+"/system/daikuaninfo/daxue/ajax/findExceptionByMonth?year="
			+ year,
	type : "post",
	cache : false,
	scriptCharset : "utf-8",
	async : false,
	dataType : "json",
	success : function(data) {

		require.config({

			paths : {
				echarts : ctx+'/js/echarts/echarts',
				'echarts/chart/bar' : ctx+'/js/echarts/echarts'
			}
		})
		/***/
		require([ 'echarts', 'echarts/chart/bar' ], function(ec) {
			// --- 折柱 ---
			var myChart = ec.init(document.getElementById('placeholder'));
			myChart.setOption({
				tooltip : {
					trigger : 'axis'
				},
				legend : {

					data : [ '异常签到' ]

				},
				grid : {
					x : 0,
					y : 0,
					x2 : 0,
					y2 : 0
				},

				toolbox : {
					show : true,
					feature : {
						mark : {
							show : true
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
					center : [ '50%', '60%' ],
					data : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月',
							'9月', '10月', '11月', '12月' ]
				} ],
				yAxis : [ {
					type : 'value',
					splitArea : {
						show : true
					}
				} ],
				series : [ {
					name : '异常签到',
					type : 'bar',
					data : data
				} ]

			});

			/** *************** */
		});

	}
});
}