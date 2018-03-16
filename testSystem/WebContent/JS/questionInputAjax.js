$(document).ready(function(){

	$("#submit").click(function(){
		function getData(){
			var json = {
					"module" : $("#module").val().trim(),
					"type" : $("#type").val().trim(),
					"stem" : $("#stem").val().trim(),
					"answer" : $("#answer").val().trim(),
					"difdeg" : $("#difdeg").val().trim()
			}
			return json;
		}

		$.ajax({
			url : '../QuestionInputServlet',
			type : 'POST',
			dataType : 'json',
			data : JSON.stringify((getData())),
			success : function(res){    //这里返回的res就是JS 对象而不是json字符串
				var res = (res["res"]);
				if("true" == res){
					alert("试题已经成功插入题库！");
				}
			},
			error : function(xmlHttp, status, errMsg){
				alert('上传试题失败！' + '	message:' + errMsg);
			}

		});
	});
});