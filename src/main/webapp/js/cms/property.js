//保存字段
function ajaxSaveColumn(formId,propertyId,businessId){
	if(formId==null){
		formId="form_dg";
	}
	 $("#"+formId).ajaxSubmit({
         type: 'post', 
         url: ctx+"/cms/property/ajax/update", 
         data: {
             'businessId': businessId,
             'id': propertyId,
         },  
         success: function(_json) { 
        	 if (_json.status == "success") {
        		 if(propertyId!=null&&propertyId!=""&&propertyId!="null"){
        			 $("#tr_"+propertyId).remove();    
        		 }
        		 //添加行
        		 if(_json['data']!=null){
        			 var id=_json['data']['id'];
        			 var name=_json['data']['name'];
        			 var code=_json['data']['code'];
        			 var text_value=_json['data']['pvalue'];
        			 var hidden_id='<input type="hidden" name="propertyId" value="'+id+'"/>';  
        			 $("#tab_property").append('<tr id="tr_'+id+'"><th>'+name+' '+hidden_id+'</th>'
        					 +'<td>'+text_value+'</td>'
        					 +'<td><i class="black ace-icon fa fa-cog">&nbsp;&nbsp;<a href="###" onclick=showdg("form_dg","'+id+'","'+businessId+'")>配置</i></a></td></tr>');
        		 }
        		 
        		 myalert("保存成功！");
        		 
        	 }else{
        		 myalert("保存失败！");
        	 }
        	// 提交后重置表单    
             $("#"+formId).resetForm();   
         }
     });
}

//查询父模块下自定义字段
function findParentProperty(businessId,modelType){ 
	jQuery.ajax({
		url : ctx+"/cms/property/ajax/findParentProperty",   
		type : "post",
		data:{'businessId':businessId,'modelType':modelType}, 
		dataType : "json", 
		success : function(_json) {
			if(_json.status=="error"){
				return false; 
			}
			$("#tab_parent tbody").empty(); 
			$(_json.data).each(function(i,_obj){
   			 var id=_obj['id'];
			 var name=_obj['name'];
			 var code=_obj['code'];
			 var text_value='<input type="text" id="pvalue_'+id+'" value="'+_obj['pvalue']+'" />';;   
			 $("#tab_parent").append('<tr id="p_tr_'+id+'"><th>'+name+'</th>'  
					 +'<td>'+text_value+'</td>'  
					 +'<td><i class="black ace-icon fa fa-cog">&nbsp;&nbsp;<a href="###" onclick=extendParent("'+id+'","'+businessId+'")>继承</i></a></td></tr>');
			});	   	
		}
	});
	
}
//继承父类属性
function extendParent(propertyId,businessId){
	var pvalue=$("#pvalue_"+propertyId).val();   
	jQuery.ajax({
		url : ctx+"/cms/property/ajax/extendParent",  
		type : "post",
		data:{'businessId':businessId,'propertyId':propertyId,'pvalue':pvalue}, 
		dataType : "json", 
		success : function(_rs) {
			if(_rs.status=="error"){
				return false; 
			}
			//删除父属性
			$("#p_tr_"+propertyId).remove();
			var newId=_rs['data']['id'];  
			if(newId==null){
				return false;  
			}
			//增加新属性
			jQuery.ajax({
				url : ctx+"/cms/property/look/json",  
				type : "post",
				data:{'id':newId},  
				dataType : "json",
				async:false,
				success : function(_json) {
					var _obj=_json;  
					if(_obj.status=="error"){ 
						return false; 
					}
					 var id=_obj.data['id'];
					 var name=_obj.data['name'];
					 var code=_obj.data['code'];
					 var text_value=_obj.data['pvalue'];    
					 var hidden_id='<input type="hidden" name="propertyId" value="'+id+'"/>'; 
					 
       			 $("#tab_property").append('<tr id="tr_'+id+'"><th>'+name+' '+hidden_id+'</th>'
    					 +'<td>'+text_value+'</td>'
    					 +'<td><i class="black ace-icon fa fa-cog">&nbsp;&nbsp;<a href="###" onclick=showdg("form_dg","'+id+'","'+businessId+'")>配置</i></a></td></tr>');
					
				}
			});
			myalert("操作成功！"); 
		}
	});
}

//查询模块下自定义字段
function findPropertyByBusiness(businessId,modelType,state){
	findParentProperty(businessId,modelType);
	$.ajax({
		url : ctx+"/cms/property/ajax/findByBusiness",  
		type : "post",
		data:{'businessId':businessId,'modelType':modelType,'state':state}, 
		dataType : "json", 
		success : function(_json) {
			if(_json.status=="error"){
				return false; 
			}
			$("#tab_property tbody").empty(); 
			$(_json.data).each(function(i,_obj){
   			 var id=_obj['id'];
			 var name=_obj['name'];
			 var code=_obj['code'];
			 var text_value=_obj['pvalue'];   
			 var hidden_id='<input type="hidden" name="propertyId" value="'+id+'"/>';  
			 $("#tab_property").append('<tr id="tr_'+id+'"><th>'+name+' '+hidden_id+'</th>'
					 +'<td>'+text_value+'</td>'
					 +'<td><i class="black ace-icon fa fa-cog">&nbsp;&nbsp;<a href="###" onclick=showdg("form_dg","'+id+'","'+businessId+'")>配置</i></a></td></tr>');
			});	   	
		}
	});
	
}
//打开弹出框
function showdg(formId,propertyId,businessId){
	if(formId==null||formId=="null"||formId==""){   
		formId="form_dg";
	} 
    $("#"+formId).resetForm();    
    if(propertyId!=null||propertyId==""||propertyId=="null"){
    	$("#code").attr("readonly",true);  
    	ajaxfindById(propertyId,formId);  
    }else{
    	$("#code").attr("readonly",false);    
    }
    var btnContent=[{    
    	                 value: '保存',
    	                 callback: function () {
		            	 ajaxSaveColumn(formId,propertyId,businessId);
		             },
		             autofocus: true
		         },
		         {
		             value: '关闭'
		         }
		     ];
    var d=dialog(); 
    var titleContent="";
	if(propertyId==null||propertyId==""||propertyId=="null"){
		titleContent="自定义属性添加";
	}else{
		titleContent="自定义属性修改";
		var btnDel={ 
				value: '删除',
				callback: function () {
                   delProperty(propertyId);
				}
         };
		btnContent.push(btnDel);
	}
	d.title(titleContent); 
	d.button(btnContent);
	var elem = document.getElementById('div_dg');   
	d.content(elem);  
	d.show();
}

//删除属性
function delProperty(propertyId){
	if(propertyId==null||propertyId==""||propertyId=="null"){
		return false;
	}
	myconfirm("确认删除这个属性?", function(){
		$.ajax({
			url : ctx+"/cms/property/delete",  
			type : "post",
			data:{'id':propertyId},
			dataType : "json",
			async:false,
			success : function(_json) {
				if(_json.status=="error"){ 
					myalert("删除失败！");
				}else{
					$("#tr_"+propertyId).remove();
					myalert("删除成功！");
				}
			}
		}); 
	});
	
}

//查看
function ajaxfindById(id,formId){    
	if(formId==null||formId=="null"||formId==""){   
		formId="form_dg";
	}
	jQuery.ajax({
		url : ctx+"/cms/property/look/json",  
		type : "post",
		data:{'id':id},
		dataType : "json",
		async:false,
		success : function(_json) {
			var result=_json;  
			if(result.status=="error"){ 
				return false;
			}
			
			$("#"+formId).find("[name='propertyId']").val(result.data['id']); 
			$("#"+formId).find("[name='modelType']").val(result.data['modelType']); 
			$("#"+formId).find("[name='name']").val(result.data['name']); 
			$("#"+formId).find("[name='code']").val(result.data['code']); 
			$("#"+formId).find("[name='inputType']").val(result.data['inputType']); 
			$("#"+formId).find("[name='style']").val(result.data['style']); 
			$("#"+formId).find("[name='selectValues']").val(result.data['selectValues']); 
			$("#"+formId).find("[name='defaultValue']").val(result.data['defaultValue']); 
			$("#"+formId).find("[name='pvalue']").val(result.data['pvalue']); 
			$("#"+formId).find("[name='sort']").val(result.data['sort']); 
			$("#"+formId).find("[name='state']").each(function(i,obj){
				if(obj.value==result.data['state']){
					obj.checked=true;
				}else{
					obj.checked=false;  
				}
			});
			$("#"+formId).find("[name='isextend']").each(function(i,obj){
				if(obj.value==result.data['isextend']){
					obj.checked=true;   
				}else{
					obj.checked=false;
				}
			});


		},
		error: function(e){
			myalert("请求异常！"); 
		}
	});
}

//打开隐藏高级选项
function moreconfigControl(tagId){
	$("#"+tagId).bind('click',function(){
		var clazz=$(this).attr('class');
		if(clazz=='myclose'){
			$(this).attr('class','myopen'); 
		    $(".moreconfig").each(function(i,obj){
		    	$(obj).show(); 
		    });
		}else{
			$(this).attr('class','myclose'); 
		    $(".moreconfig").each(function(i,obj){
		    	$(obj).hide(); 
		    });
		}
	});
}