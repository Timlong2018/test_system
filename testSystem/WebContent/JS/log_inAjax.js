
$(document).ready(function(){
	function getData(){
		var json = {
				"username" : $("#username").val().trim(),
				"password" : $("#password").val().trim()
		}
		return json;
	}

	$("#submit").click(function(){
		var username = $("#username").val().trim();
		var password = $("#password").val().trim();

		$.ajax({
			url : "../MyLoginServlet",
			type : "POST",
			dataType : "json",  //dataTTType
			data : JSON.stringify(getData()),
			success : function(res) {


				var flag = res["res"];
				var message = res["message"];
				if("true" == flag){
					alert(message);
					window.location.href = "index.html";
				}
				else if("false" == flag){
					alert(message);
					$("#loginForm").reset();
				}
			},
			error : function(xmlHttp, status, errMsg) {
				alert("数据加载失败！" + errMsg	);
				alert(xmlHttp.status);
			}
		});


	});
});