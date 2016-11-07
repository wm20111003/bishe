function myalert(message,fun){
	 if(fun){
		 bootbox.alert(message,fun);
	 }else{
		 bootbox.alert(message);
	 }
}

function myerror(message,fun){
	myalert(message,fun);
}

function myinfo(message,fun){
	myalert(message,fun);
}

function myquestion(message,fun){
	myalert(message,fun);
}

function mywarning(message,fun){
	myalert(message,fun);
}

function myconfirm(message,fun){
		 bootbox.confirm(message,function(result){
			 if(result&&fun){
				 fun();
			 }
			
		 });
}

function myprompt(message,fun){
	 bootbox.prompt(message,function(result){
		 if(fun){
			 fun(result);
		 }
		
	 });
	
}















