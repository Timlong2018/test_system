$(document).ready(function(){
	var flag = false;

	$("#browse").click(function(){
		$("#fileUpload").click();
	});

	$("#fileUpload").change(function(){
		$("#txt").val($("#fileUpload").val().trim());
		flag = checkSuffix();

		if(!flag){
			alert("请务必上传扩展名为“.xlsx”或“.xls”的文件！"); 
		}
	});


	$("#submitFile").click(function(){

		if(flag){

			var item = $("#fileUpload").files[0];
			var formData = new FormData();
			formData.append("uploadItem", item);

			$.ajax({  
				url : '../ImportQuesServlet',  
				type : 'POST', 
				secureuri:false,//一般设置为false
				fileElementId:'fileUpload',
				dataType : "json",
//				processData : false,  //必须false才会避开jQuery对 formdata 的默认处理   
//				contentType : false,  //必须false才会自动加上正确的Content-Type 
				success : function(res) {  
					alert(res);
				},  

				error : function(xmlHttp, status, errMsg) {  
					alert("文件上传失败！");
				}  
			}); 
		}else{
			alert("上传的文件格式不正确！");
		}
	});


	function checkSuffix(){
		var name = $("#txt").val().trim();
		var strRegex = "(.xls|.xlsx)$"; 
		var re = new RegExp(strRegex);

		if (re.test(name.toLowerCase())){
			return true;
		} else{
			return false;
		}
	}




});