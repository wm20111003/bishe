
function ajaxDicData(pathkey,selectId,defaultValue,defaultKey,defaultshow,rsync){
	var _ctx=ctx+"/system/dicdata/"+pathkey+"/list/json";
	ajaxData2SelectByURL(_ctx, selectId, defaultValue, defaultKey, defaultshow, rsync)
}


function ajaxData2SelectByURL(_ctx,selectId,defaultValue,defaultKey,defaultshow,rsync){
	var _async=true;
	if(rsync){
		_async=false;
	}
	var idKey="name";
	var nameshow="name";
	if(defaultKey){
		idKey=defaultKey;
	}if(defaultshow){
		nameshow=defaultshow;
	}
	jQuery.ajax({    
        type:"post",        
        url:_ctx, 
        async:_async,
        dataType:"json",    
        success:function(_data){
        	jQuery("#"+selectId).empty();
        	jQuery("#"+selectId).append("<option value=''>===请选择===</option>");
        	jQuery(_data.data).each(function(i,_json){
        	 	jQuery("#"+selectId).append("<option value='"+_json[idKey]+"'>"+_json[nameshow]+"</option>");
        	});
        	//回选 下拉框
        	jQuery("#"+selectId+" option[value='"+defaultValue+"']").prop("selected",true);
        }    
    });    
}







function ajaxarea(selectId,defaultValue,superName,fun){
	var _ctx=ctx+"/dicdata/area/ajax/bySuperName";
	var par={"superName":superName};
	jQuery.ajax({    
        type:"post",        
        data:par,
        url:_ctx, 
        dataType:"json",    
        success:function(data){
        	jQuery("#"+selectId).empty();
        	jQuery("#"+selectId).append("<option value=''>===请选择===</option>");
        	jQuery(data).each(function(i,_json){
        	 	jQuery("#"+selectId).append("<option value='"+_json+"'>"+_json+"</option>");
        	});
        	//回选 下拉框
        	jQuery("#"+selectId+" option[value='"+defaultValue+"']").prop("selected",true);
        	if(fun){
        	fun();
        	}
        }    
    });    
}



/**
 * ajax  根据typeKey查询字典选项渲染到指定容器,并根据businessId默认选中初始值
 * @param pathkey
 * @param businessId
 * @param checkboxName
 * @param containerId
 * @param defaultKey
 * @param defaultshow
 * @param rsync
 */
function ajaxCheckBoxBusiness(pathkey,businessId,checkboxName,containerId,defaultKey,defaultshow,rsync){
	var checkbox_ctx=ctx+"/system/dicdata/"+pathkey+"/list/json";
	var idKey="name";
	var nameshow="name";
	if(defaultKey){
		idKey=defaultKey;
	}if(defaultshow){
		nameshow=defaultshow;
	}
	//在容器渲染
	ajaxCheckboxData(checkbox_ctx,checkboxName,containerId,"","id","name",rsync);
	if((!businessId)||businessId==""||businessId==null){
		return false;
	}
	
	  ajaxJSONDataBusiness(pathkey, businessId, function(data){
			if(data.status=="error"){
	    		return false;
	    	}
	    	//初始化选中
	    	jQuery(data.data).each(function(i,_obj){
	    		 jQuery(":checkbox[value='"+_obj[idKey]+"']",	jQuery("#"+containerId)).prop("checked", true);
	    	});
	});
	
	
} 


function ajaxJSONDataBusiness(pathkey,businessId,fun){
	var _ctx=ctx+"/system/dicdata/"+pathkey+"/ajax/business";
	
	var par={"businessId":businessId};
	jQuery.ajax({    
        type:"post",        
        data:par,
        url:_ctx, 
        async:false,
        dataType:"json",    
        success:function(data){
        	fun.call(this,data);
        }    
    });    
}


/**
 * ajax 获取字典,并使用复选框的方式渲染到指定容器
 * @param pathkey
 * @param inputId
 * @param containerId
 * @param defaultValue
 * @param defaultValue
 * @param defaultKey
 * @param defaultshow
 * @param rsync
 */
function ajaxCheckboxDicData(pathkey,checkboxName,containerId,defaultValue,defaultKey,defaultshow,rsync){
	var _ctx=ctx+"/system/dicdata/"+pathkey+"/list/json";
	ajaxCheckboxData(_ctx,checkboxName,containerId,defaultValue,defaultKey,defaultshow,rsync);
} 





/**
 * ajax 获取字典,并使用复选框的方式渲染到指定容器
 * @param pathkey
 * @param inputId
 * @param containerId
 * @param defaultValue
 * @param defaultValue
 * @param defaultKey
 * @param defaultshow
 * @param rsync
 */
function ajaxCheckboxData(_ctx,checkboxName,containerId,defaultValue,defaultKey,defaultshow,rsync){
	var _async=true;
	if(rsync){
		_async=false;
	}
	var idKey="name";
	var nameshow="name";
	if(defaultKey){
		idKey=defaultKey;
	}
	if(defaultshow){
		nameshow=defaultshow;
	}
	jQuery.ajax({    
        type:"post",        
        url:_ctx, 
        async:_async,
        dataType:"json",    
        success:function(data){
        	jQuery("#"+containerId).empty();
        	jQuery(data.data).each(function(i,_json){
        	 	jQuery("#"+containerId).append(" <label class='control-label'> <input  class='ace'  type='checkbox' name='"+checkboxName+"'   value='"+_json[idKey]+"' /><span class='lbl'> "+_json[nameshow]+"</span></label>");
        	});
        	
        	
        	
        	//回选 复选框
        	if(defaultValue){
        		var vl;
        		if(typeof defaultValue === 'string'){
        			 vl=defaultValue.split(",");
        		}else{
        			vl=defaultValue;
        		}
        	   
        	   jQuery(vl).each(function(_i,_value){
        		   jQuery(":checkbox[value='"+_value+"']",	jQuery("#"+containerId)).prop("checked", true);
        	   });
        	}
        }    
    });    
} 



/**
 * ajax 获取字典,并使用复选框的方式渲染到指定容器 未完成
 * @param pathkey
 * @param inputId
 * @param containerId
 * @param defaultValue
 * @param defaultValue
 * @param defaultKey
 * @param defaultshow
 * @param rsync
 */
function ajaxSelectDicData(pathkey,containerId,defaultValue,defaultKey,defaultshow,rsync){
	var _ctx=ctx+"/system/dicdata/"+pathkey+"/ajax/tree/json";
	ajaxSelectData(_ctx,containerId,defaultValue,defaultKey,defaultshow,rsync);
} 




/**
 * ajax 获取字典,并使用复选框的方式渲染到指定容器 未完成
 * @param pathkey
 * @param inputId
 * @param containerId
 * @param defaultValue
 * @param defaultValue
 * @param defaultKey
 * @param defaultshow
 * @param rsync
 */
function ajaxSelectData(_ctx,containerId,defaultValue,defaultKey,defaultshow,rsync){
	var _async=true;
	if(rsync){
		_async=false;
	}
	var idKey="name";
	var nameshow="name";
	if(defaultKey){
		idKey=defaultKey;
	}
	if(defaultshow){
		nameshow=defaultshow;
	}
	
	
	jQuery.ajax({    
        type:"post",        
        url:_ctx, 
        async:_async,
        dataType:"json",    
        success:function(data){
        	jQuery("#"+containerId).empty();
        	addWarpDicSelect(containerId, data.data, idKey, nameshow);
           
        }    
    });    
} 


/**
 * 未完成
 * @param containerId
 * @param ja
 * @param idKey
 * @param nameshow
 */
function addWarpDicSelect(containerId,ja,idKey,nameshow){
	var _str="<select>";
	jQuery(ja).each(function(i,_obj){
		_str=_str+"<option value='"+_obj[idKey]+"'>"+_obj[nameshow]+"</option>";
	});
	_str=_str+"</select>";
	
	var _select=jQuery(_str);
	jQuery("#"+containerId).append(_select);

    if(ja[0].leaf){
    	
    	_select.change(function(){
    		_select.nextAll().empty();
    		addSelectOpetion2JqueryObject(_select.next(),ja[0].leaf,idKey,nameshow);
    	});
    	
    	addWarpDicSelect(containerId, ja[0].leaf, idKey, nameshow);
    }
	
	
	
}

/**
 * 未完成
 * @param select
 * @param data
 * @param idKey
 * @param nameshow
 */
function addSelectOpetion2JqueryObject(select,data,idKey,nameshow){
	select.append("<option value=''>===请选择===</option>");
	jQuery(data).each(function(i,_obj){
		select.append("<option value='"+_obj[idKey]+"'>"+_obj[nameshow]+"</option>");
	});
}


/**
 * 根据pid ajax数据渲染到指定的 select
 * @param pathkey
 * @param selectId
 * @param pid
 * @param defaultValue
 * @param defaultKey
 * @param defaultshow
 * @param rsync
 */

function addOptionSelectIdPid(pathkey,selectId,pid,defaultValue,defaultKey,defaultshow,rsync){
	
	var idKey="id";
	var nameshow="name";
	
	if(defaultKey){
		idKey=defaultKey;
	}
	if(defaultshow){
		nameshow=defaultshow;
	}
	
	
	ajaxDataPid(pathkey,pid,function(_data){
    	jQuery("#"+selectId).empty();
    	jQuery("#"+selectId).append("<option value=''>===请选择===</option>");
    	jQuery(_data.data).each(function(i,_json){
    	 	jQuery("#"+selectId).append("<option value='"+_json[idKey]+"'>"+_json[nameshow]+"</option>");
    	});
    	//回选 下拉框
    	jQuery("#"+selectId+" option[value='"+defaultValue+"']").prop("selected",true);
		
	},rsync);
	
	
	
 
}



function ajaxDataPid(pathkey,pid,fun,rsync){
	var _async=true;
	if(rsync){
		_async=false;
	}
	var par={"pid":pid};
	var _ctx=ctx+"/system/dicdata/"+pathkey+"/ajax/findbypid";
	jQuery.ajax({    
        type:"post",        
        url:_ctx, 
        data:par,
        async:_async,
        dataType:"json",    
        success:function(_data){
        	fun.call(this,_data);
        }    
    });    
}









