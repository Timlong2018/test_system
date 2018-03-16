$(document).ready(function(){

	$("#subButton").click(function(){

		function getData(){

			var json = {
					"name" : $("#name").val().trim(),
					"type" : $("#type").val().trim(),
					"examtime" : $("#examTime").val().trim().replace("T", " ").concat(":00")
			}
			return json;
		}

		// true  OR  false  is the return value  of is(":checked")
		var single = $("#single").is(":checked");
		var tf = $("#tf").is(":checked");
		var frq = $("#frq").is(":checked");
		var data = getData();

		if(single){
			data.singleNum = $("#single_num").val().trim();
		}else{
			data.singleNum = '0';
		}
		if(tf){
			data.tfNum = $("#tf_num").val().trim();
		}else{
			data.tfNum = '0';
		}
		if(frq){
			data.frqNum = $("#frq_num").val().trim();
		}else{
			data.frqNum = '0';
		}

		$.ajax({
			url : '../ViewTestPaperServlet',
			type : 'POST',
			dataType : 'json',
			data : JSON.stringify(data),
			success : function(res){
				$("#testpaper").val("");

				if("false" == res["res"]){
					alert("插入试卷失败，请更换试卷名称重试！");
					return;
				}

				var stems = "";
				for(var i = 0; i < res.length; i++){
					stems += res[i].stem;
				}
				$("#testpaper").val(stems);
			},
			error : function(xmlHttp, status, errMsg){
				alert('获取数据失败！' + '	message: ' + errMsg);
			}

		});
	});

});