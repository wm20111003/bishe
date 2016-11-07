
/**
 * 提示信息
 * @param context
 * @param source
 * @returns {String}
 */
	function tooltip_placement(context, source) {
			var $source = $(source);
			var $parent = $source.closest('table');
			var off1 = $parent.offset();
			var w1 = $parent.width();
	
			var off2 = $source.offset();
			//var w2 = $source.width();
	
			if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
			return 'left';
		}


	function toggle_adv_search() {
		if ($("#adv_search").attr("class").indexOf("display-none") < 0) {
			$("#adv_search").addClass("display-none");
			$("#toggle_adv_search_icon").addClass("fa-chevron-down");
			$("#toggle_adv_search_icon").removeClass("fa-chevron-up");
		} else {
			$("#adv_search").removeClass("display-none");
			$("#toggle_adv_search_icon").addClass("fa-chevron-up");
			$("#toggle_adv_search_icon").removeClass("fa-chevron-down");
		}
	}
	
	
	function close_adv_search() {
		$("#adv_search").addClass("display-none");
		$("#toggle_adv_search_icon").addClass("fa-chevron-down");
		$("#toggle_adv_search_icon").removeClass("fa-chevron-up");
	}
	$(document).ready(function() {
		$('.input-date').datepicker({
				language:"zh-CN",
				autoclose : true
			});
			$(".input-daterange").datepicker({
				 format: "yyyy-mm-dd",
				 language:"zh-CN",
				 keyboardNavigation: false,
				 forceParse: false,
				 autoclose: true,
			});
			
		});



	