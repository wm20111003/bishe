/**
 * picture 页面使用javascript
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2014-08-20 22:48:34
 */


jQuery(document).ready(function(){
  
});



jQuery(function($) {
	var $overflow = '';
	var colorbox_params = {
		rel: 'colorbox',
		reposition:true,
		scalePhotos:true,
		scrolling:false,
		previous:'<i class="ace-icon fa fa-arrow-left"></i>',
		next:'<i class="ace-icon fa fa-arrow-right"></i>',
		close:'&times;',
		current:'{current} of {total}',
		maxWidth:'100%',
		maxHeight:'100%',
		onOpen:function(){
			$overflow = document.body.style.overflow;
			document.body.style.overflow = 'hidden';
		},
		onClosed:function(){
			document.body.style.overflow = $overflow;
		},
		onComplete:function(){
			$.colorbox.resize();
		}
	};
	$('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
	$("#cboxLoadingGraphic").append("<i class='ace-icon fa fa-spinner orange'></i>");//let's add a custom loading icon
});




/**
 * 根据 业务Id查找所有的图片,并执行回调函数
 * @param businessId
 * @param fun
 */
function ajaxPicture(businessId,fun){
	var extinfourl=ctx+"/system/picture/list/json";
	var par={"businessId":businessId};
	jQuery.ajax({    
        type:"post",        
        data:par,
        url:extinfourl, 
        dataType:"json",    
        success:function(data){
        	fun.call(this,data);
        }    
    });    
	
}

