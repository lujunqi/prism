function isNotNull(el,value){//非空判断

	if(value==""){
		return false;
	}else{
		return true;
	}
}
function isNumber(el,value){//是否数字
	if(!isNotNull(el,value)){
		return false;
	}
	if(isNaN(value)){
		return false;
	}else{
		return true;
	}
}
function ajax(el,value){//ajax验证
	var result = false;
	var validaParam = el.data("validaParam");
	var $data = validaParam["data"]==null?{}:validaParam["data"];
	$data[el.attr("name")==null?el.attr("id"):el.attr("name")] = value;
	$.ajax({
		url:validaParam["url"],
		dataType:"json",
		async:false,
		data:$data,
		cache:false,
		type: "post",
		success:function(data){
			if(data["status"]=="y"){
				result = true;
			}
		}
	});
	return result;
}
function vLength(el,value){//长度验证
	var validaParam = el.data("validaParam");
	var $min = validaParam["min"];
	var $max = validaParam["max"];
	var vLen = value.length;
	if ((vLen<=$max) && (vLen>=$min)){  
		return true;  
	}else{
		return false;
	}
}
function isEmail(el,value){//是否邮箱
	if(/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/.test(value)){
		return true;
	}else{
		return false;
	}
}
function isImg(el,value){//判断是否是一个格式的文件 
	var validaParam = el.data("validaParam");
    var objReg = new RegExp("[.]+("+validaParam["type"]+")$", "gi");
    if(objReg.test(value)){   
        return true;   
    }
    return false;   
}  

