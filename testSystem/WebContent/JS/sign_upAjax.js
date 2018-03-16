$(document).ready(function(){
	
	function getData(){
		var json = {
				"username" : $("#username").val().trim(),
				"password" : $("#password").val().trim(),
		        "active" : "false"
		}
		return json;
	}
	
	$("#submit").click(function(){
		
		$("#remind").html("");
		
		var username = $("#username").val().trim();
		var password = $("#password").val().trim();
		var confirmPassword = $("#confirmPassword").val().trim();
		
		if(password == confirmPassword){
			$.ajax({
				url : "../RegisterServlet",
				type : "POST",
				dataType : "json",  //dataTTType
				data : JSON.stringify(getData()),
				success : function(res) {
					
					var flag = res["res"];
					var message = res["message"];
					if("true" == flag){
						alert(message);
						window.location.href = "log_in.html";
					}
					else if("false" == flag){
						alert(message);
						$("#signUpForm").reset();
					}
				},
				error : function(xmlHttp, status, errMsg) {
					alert("数据加载失败！" + errMsg	);
					alert(xmlHttp.status);
				}
			});
		} else{
			$("#remind").html("两次输入的密码不一致！");
			$("#signUpForm").reset();
			return;
		}
		
	});
});