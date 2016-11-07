	//头像上传初始化方法
	function initWebS(fileId,pickerId,serverUrl){
	    //webuploader
	    var $ = jQuery,
	            $list = $(fileId),
	    // 优化retina, 在retina下这个值是2
	            ratio = window.devicePixelRatio || 1,

	    // 缩略图大小
	            thumbnailWidth = 100 * ratio,
	            thumbnailHeight = 100 * ratio,

	    // Web Uploader实例
	       uploaderS;
	    // 初始化Web Uploader
	    uploaderS = WebUploader.create({
	        // 自动上传。
	        auto: true,

	        // 图片上传前进行压缩
	        compress: true,
	        
	        // swf文件路径
	        swf:  'js/Uploader.swf',

	        // 文件接收服务端。
	        server: serverUrl,

	        // 选择文件的按钮。可选。
	        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	        pick:pickerId,
	           

	        // 只允许选择文件，可选。
	        accept: {
	            title: 'Images',
	            extensions: 'gif,jpg,jpeg,bmp,png',
	            mimeTypes: 'image/*'
	        },
		    fileVal: "file",
		    fileSingleSizeLimit:1048576
		    
	    });

	    // 图片上传前，尝试将图片压缩到225 * 225
	    uploaderS.option( 'compress', {
	        width: 225,
	        height: 225
	    });
	    
	  //图片加入队列前,判断图片大小
	    uploaderS.on('beforeFileQueued',function(file){
	  	  if(file.size > 1048576){
		    	    Util.dialog.tips("图片大于1M，请重新选择",top,800);
		         }
	    })
	    uploaderS.on( 'uploadStart', function( file ) {});
	    // 当有文件添加进来的时候
	    uploaderS.on( 'fileQueued', function( file ) {
	    	var chil = $list.children('div');
	        var $li = $(
	                        '<div id="' + file.id + '" class="file-item thumbnail" style="width:100px;">' +
	                        '<img>' +
	                        '<div class="info">' + file.name + '</div>' +
	                        '<div class="delpic"><span>删除图片</span></div>'+
	                        '</div>'
	                ),
	        $img = $li.find('img');
	        if(chil.length > 0){
	        	chil.replaceWith( $li );
	        }else{
	        	$list.append( $li );
	        }
	        //图片删除
	        $(".delpic span").bind("click",function(){
	        var _this=$(this);
		    $('#filePath').val('');
		    $('#fileName').val('');
	        _this.parents(".file-item").remove();
	         });
	        // 创建缩略图
	        uploaderS.makeThumb( file, function( error, src ) {
	            if ( error ) {
	                $img.replaceWith('<span>不能预览</span>');
	                return;
	            }

	            $img.attr( 'src', src );
	        }, thumbnailWidth, thumbnailHeight );
	    });

	    // 文件上传过程中创建进度条实时显示。
	    uploaderS.on( 'uploadProgress', function( file, percentage ) {
	        var $li = $( '#'+file.id ),
	                $percent = $li.find('.progress span');

	        // 避免重复创建
	        if ( !$percent.length ) {
	            $percent = $('<p class="progress"><span></span></p>').appendTo( $li ).find('span');
	        }

	        $percent.css( 'width', percentage * 100 + '%' );
	    });

	    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
	    uploaderS.on( 'uploadSuccess', function(file, data) {
	    	var picUrl = data.data.fileUrl;
	    	var imgName = data.data.fileName;
	        $('#' + file.id).find(".delpic > span").attr("id", picUrl);
	        $('#filePath').val(picUrl);//最终上传图片
	        $('#fileName').val(imgName);
	        $( '#'+file.id ).addClass('upload-state-done');
	    });

	    // 文件上传失败，显示上传出错。
	    uploaderS.on( 'uploadError', function( file ) {
	    		 var $li = $( '#'+file.id ),
	             $error = $li.find('div.error');

			     // 避免重复创建
			     if ( !$error.length ) {
			         $error = $('<div class="error"></div>').appendTo( $li );
			     }
			     $("#"+file.id).remove();
	    });

	    // 完成上传完了，成功或者失败，先删除进度条。
	    uploaderS.on( 'uploadComplete', function( file ) {
	        $( '#'+file.id ).find('.progress').remove();
	        uploaderS.reset();
	    });
	}