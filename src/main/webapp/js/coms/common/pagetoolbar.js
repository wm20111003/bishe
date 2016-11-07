//跳转的页面
function toPage(formId,pageIndex){
document.getElementById("pageIndex").value=pageIndex;
mySubmitForm(formId);
}


//首页
function startPage(formId){
document.getElementById("pageIndex").value="1";
mySubmitForm(formId);
}
//末页
function endPage(formId,pageIndex){
document.getElementById("pageIndex").value=pageIndex;
mySubmitForm(formId);
}
