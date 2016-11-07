
/**
 * 		KindEditor.ready(function(K) {
			
			editor=	initkindeditor(K,"textarea[name='description']",  ctx+'/KingEditorFileManagerController/upload_json?type=${(cerp_session_account)!"empty"}', ctx+'/KingEditorFileManagerController/file_json_manager?type=${(cerp_session_account)!"empty"}');
		
		});
 * @param K
 * @param selector
 * @param uploadJson
 * @param fileManagerJson
 * @returns
 */




function initkindeditor(K,selector,uploadJson,fileManagerJson){
	//初始化 myimg 插件
	KindEditor.plugin('myimg', function(K) {
        var _my = this, name = 'myimg';
        // 点击图标时执行
        _my.clickToolbar(name, function() {
        	_my.loadPlugin('link', function() {
        		_my.plugin.imageDialog({
    				clickFn : function(url, title, width, height, border, align) {
    					_my.insertHtml("<mymedia src='"+url+"'/>");
    					//K('#imgUrl').val(url);
    					//K('#imgUrlView').attr('src',url);
    					_my.hideDialog();
    				}
    			});
    		});
        });
	});
	//初始化 编辑器
	var edit=  K.create(selector, {
		resizeType : 1,
		//width : '200px',
		allowPreviewEmoticons : false,
		allowFileManager : true,
		uploadJson : uploadJson,
	    fileManagerJson :fileManagerJson,
		items : [
'undo', 'redo', '|',  'cut', 'copy', 'paste',
'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
'superscript', 'clearhtml', 'quickformat', 'selectall', '|','fullscreen','source','preview' ,'/',
'formatblock', 'fontname', 'fontsize','|', 'forecolor', 'hilitecolor', 'bold',
'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'myimg','insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
'anchor', 'link', 'unlink']
	});
	
	return edit;
	
}