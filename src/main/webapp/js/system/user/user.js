/**
 * user 页面使用javascript
 * @copyright {@link erp.centfor.com}
 * @author centfor<Auto generate>
 * @version  2013-07-29 11:36:47
 */


jQuery(document).ready(function(){

	//validateRules('saveForm');
});

function delUser(id){
    var url = ctx + "/system/user/delete?id=" + id;
        myconfirm("确定要删除么?",function(){
		 jQuery.get(url, null, function(data){
            if (data.status == "success") {
                myalert(data.message);
                myreloadpage();
            }
            else {
                myalert(data.message);
            }
          });
		});
       
    
}
function delMulti(){
     var records = jQuery(":checkbox[name='check_li']").checkbox().val();
    if (records.length == "") {
        myalert('未选中任何记录!');
        return;
    }
	var url = ctx + "/system/user/delMulti";
    myconfirm("记录删除后将不能恢复,确定要删除选中的记录么?",function(){
	 jQuery.get(url, "records=" + records, function(data){
            myalert(data.message);
            myreloadpage();
        });
	});
 
}
