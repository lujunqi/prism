
function isNotNull(param){//不为空
	var str = param["$this"];
	if (str == "") return false;
	return true;
}
function isNum(param) {//数字
	var s = param["$this"];
	var regu = "^[0-9]+$";
	var re = new RegExp(regu);
	if (s.search(re) != -1) {
		return true;
	} else {
		return false;
	}
}
function isAjax(param){ //后台效验
	var $url = param["url"];
	var result = false; 
	$.ajax({
	url:$url,
	dataType:"json",
	async:false,
	data:{val:param["$this"]},
	cache:false,
	type: "post",
	success:function(data){
		if(data["result"]=="y"){
			result = true;
		}else{
			result = false;
		}
	}
	});
	return result;
}
function isMobile(param){//手机
	var s = param["$this"];
	var regu = /^[1][0-9][0-9]{9}$/;
	var re = new RegExp(regu);
	if (re.test(s)){
		return true;
	} else 	{
		return false;
	}
}
/**
*判断身份证号码格式函数
*公民身份号码是特征组合码，
*排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码
*/
function isChinaIDCard(param){
	var StrNo = param["$this"];  
	StrNo = StrNo.toString();
	if(StrNo.length == 15){        
		ret = isValidDate("19"+StrNo.substr(6,2),StrNo.substr(8,2),StrNo.substr(10,2));
		if(ret){
			return false;
		}      
	}else if(StrNo.length == 18){    
		ret = isValidDate(StrNo.substr(6,4),StrNo.substr(10,2),StrNo.substr(12,2));
		if(ret){
			return false;
		}    
	}else{  
		return false ;  
	}
  
	if (StrNo.length==18){  
		var a,b,c  
		if (!isNumber(StrNo.substr(0,17))){
			return false;
		}  
		a=parseInt(StrNo.substr(0,1))*7+parseInt(StrNo.substr(1,1))*9+parseInt(StrNo.substr(2,1))*10;  
		a=a+parseInt(StrNo.substr(3,1))*5+parseInt(StrNo.substr(4,1))*8+parseInt(StrNo.substr(5,1))*4;  
		a=a+parseInt(StrNo.substr(6,1))*2+parseInt(StrNo.substr(7,1))*1+parseInt(StrNo.substr(8,1))*6;    
		a=a+parseInt(StrNo.substr(9,1))*3+parseInt(StrNo.substr(10,1))*7+parseInt(StrNo.substr(11,1))*9;    
		a=a+parseInt(StrNo.substr(12,1))*10+parseInt(StrNo.substr(13,1))*5+parseInt(StrNo.substr(14,1))*8;    
		a=a+parseInt(StrNo.substr(15,1))*4+parseInt(StrNo.substr(16,1))*2;  
		b=a%11;  
		if (b==2) { //最后一位为校验位  
			c=StrNo.substr(17,1).toUpperCase();   //转为大写X  
		} else {  
			c=parseInt(StrNo.substr(17,1));  
		}  
		switch(b){  
		  	case 0: if ( c!=1 ) {return false;}break;  
		  	case 1: if ( c!=0 ) {return false;}break;  
		  	case 2: if ( c!="X") {return false;}break;  
		  	case 3: if ( c!=9 ) {return false;}break;  
		  	case 4: if ( c!=8 ) {return false;}break;  
		  	case 5: if ( c!=7 ) {return false;}break;  
		  	case 6: if ( c!=6 ) {return false;}break;  
		  	case 7: if ( c!=5 ) {return false;}break;  
		  	case 8: if ( c!=4 ) {return false;}break;  
		  	case 9: if ( c!=3 ) {return false;}break;  
		  	case 10: if ( c!=2 ){return false;}  
		}  
	} else{//15位身份证号  
		if (!isNumber(StrNo)){
			return false;
		}    
	}  
	return true;
	
}  
    
function isValidDate(iY, iM, iD){
	if (iY>2200 || iY<1900 || !isNumber(iY)){
		return false;
	}
	if (iM>12 || iM<=0 || !isNumber(iM)){
		return false;
    }
	if (iD>31 || iD<=0 || !isNumber(iD)){
		return false;
    }
	return "";
}  
function isNumber(s) {
	var regu = "^[0-9]+$";
	var re = new RegExp(regu);
	if (s.search(re) != -1) {
		return true;
	} else {
		return false;
	}
}