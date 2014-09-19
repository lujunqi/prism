var systemObject = {};
systemObject["formValidatorResult"] = false;
$.ajaxSetup({ cache: false });
var pager_length = 11;    //不包next 和 prev 必须为奇数
var pager_display = 10;   //每页显示记录数
//标签加载
$.fn.prism = function(opt) {
	var $This = this;
	if(opt==null){
		opt = {};
	}
	for(var i = 0;i<$This.length;i++){
		run($This.eq(i),opt);
	}
	return this;
};

function run($this,opt){
	var prismType = $this.attr("prism");
	if (prismType == null){
		return;
	}
	if (prismType == "dataGrid") {
		return setGriddata($this,opt);
	}
	if(prismType == "include"){
		return setInclude($this,opt);
	}
}
function setGriddata($this,opt){
	var html = $this.html();
	if ($this.data("prismViewTemplate") == null) { //视图模板
		$this.data("prismViewTemplate", html);
	} else {
		html = $this.data("prismViewTemplate");
	}
	if(opt["prismData"]=="NOTE"){ // 注释模式
		html = html.replaceAll("<!--","");
		html = html.replaceAll("-->","");
	}
	$this.html("");
	/** 执行前回调 **/
	if(opt["begin"]!=null){
		opt["begin"]($this);
	}
	//内容
	var content = opt["content"];
	if(Array == content.constructor){// 已有数据源
		getPrismData(content,$this,html);
		/** 完成时回调 **/
		func_Finish(content,opt,$this);
	}else if(String == content.constructor){// 字符串数据源
		var key = content.substring(0,5);
		if(key.toUpperCase()=="AJAX:"){//AJAX获取
			// AJAX提交参数
			getAjaxContent(opt,$this,html);
		}else if(key.toUpperCase()=="JSON:"){//JSON字符串获取
			var data = $.parseJSON(content.substring(5));
			getPrismData(data,$this,html);
			/** 完成时回调 **/
			func_Finish(data,opt,$this);
		}else{
			// AJAX提交参数
			opt["content"]="AJAX:"+opt["content"];
			getAjaxContent(opt,$this,html);
		}
	}
}

//完成时回调
function func_Finish(data,opt,$this){
	func_page(opt,$this);
	if(opt["end"]!=null){
		opt["end"]($this,data);
	}
}
// 翻页
function func_page(opt,$this){
	var $page = $(".page",$this.parent());
	if(opt["pageUrl"]!=null){

		$page.html("");
		var current_page = opt["current_page"];//当前页
		var $data = opt["param"];
		$.post(opt["pageUrl"],$data,function(data){
			try{
				var total_page = Math.ceil(data[0]["total"]/pager_display); //总记录页
				$page.html(update_page(total_page,current_page));
				$("a[current_page]",$page).click(function(){
					var that = $(this);
					opt["current_page"] = that.attr("current_page");
					setGriddata($this,opt);//回调获取数据
				});
			}catch(e){}
		},"json");
	}
}
//AJAX数据获取
function getAjaxContent(opt,$this,html){
	var content = opt["content"];
	var $data = {};
	if(opt["param"]==null){
		opt["param"] = {};
	}
	var $data = $.extend(true,{},opt["param"]);
	if(opt["pageUrl"]!=null){
		if(opt["current_page"]==null){
			opt["current_page"] = 1;
		}
		$data["prism_begin_number"] = (opt["current_page"]-1)*pager_display;
		$data["prism_end_number"] = $data["prism_begin_number"]+pager_display;
	}
	// 同步设定
	var $async = true;
	if(opt["async"]!=null){
		$async = opt["async"];
	}
	$url = content.substring(5);
	$.ajax({
		url:$url,
		dataType:"json",
		async:$async,
		data:$data,
		cache:false,
		type: "post",
		success:function(data){
			if(data!=null){
				getPrismData(data,$this,html);
			}
			/** 完成时回调 **/
			func_Finish(data,opt,$this);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			if(opt["error"]!=null){
				opt["error"](XMLHttpRequest, textStatus, errorThrown);
			}
		}
	});
}
//回填数据
function getPrismData(content,$this,html){
	var linkRegx = /#@([A-Za-z0-9_:]+)#/ig;
	var group = html.match(linkRegx);
	for ( var i = 0; i < content.length; i++) {
		var d = content[i];
		d["JSIndex"] = i+1;
		var p50 = html;
		if(null!=group){
			for( var j = 0; j < group.length; j++) {
				var key = group[j].substring(2,group[j].length-1);
				var k53 = key.split(":");
				if(k53.length==1){
					if(d[key]==null){
						d[key]="";
					}
					p50 = p50.replaceAll("#@"+key+"#",d[key]);
				}else if(k53.length==2){//调用函数
					var funcName = k53[1];
					var typeName = k53[0];
					if(typeName.toUpperCase()=="FUNC"){
						var data = eval(funcName).call(this,d);		
						p50 = p50.replaceAll("#@"+key+"#",data);
					}
				}
			}
		}
		$this.append($(p50));
	}
}
/*页面加载****************************************************************/
function loadingOne(type){
	if(type=="show"){
		if($(".loadingOne").length==0){
			$(document.body).append('<div class="mask"></div><div class="loadingOne"></div>');
		}
	}else if(type=="hide"){
		$(".loadingOne").remove();
		$(".mask").remove();
	}
}
/*表单验证****************************************************************/
$.fn.formValidator = function(){
	var result = [];
	var $this = this;
	var validas = $("[valida]",$this);
	for(var i =0;i<validas.length;i++){
		var c = validas.eq(i);
		var data = func_formValidator(c);
		if(data==false){
			result.push(c);
			$("em",c.parent()).removeClass("checked");
			$("em",c.parent()).addClass("unchecked");
			$("em",c.parent()).html(c.attr("validaMsg"));
		}else{
			$("em",c.parent()).removeClass("unchecked");
			$("em",c.parent()).addClass("checked");
			$("em",c.parent()).html("");
		}
	}
	return result;
};
function func_formValidator(c){
	var valida = c.attr("valida");
	var paramStr = c.attr("validaParam");
	try{
		var paramKey = paramStr.substring(0,4);
		if(paramKey.toUpperCase()=="FUNC"){
			var param = eval(paramStr.substring(5)).call(this,c);
		}else{
			var param = $.parseJSON(paramStr);
		}
	}catch(e){
		var param = $.parseJSON(paramStr);
	}
	if(c.is(":radio")){ //单选框
		var name = c.attr("name");
		if(name!=null){
			var myValue = $(":radio[name="+name+"][checked]").val();
		}else{
			if(c.attr("checked")){
				var myValue = c.val();
			}
		}
	}else if(c.is(":checkbox")){//多选框
		var name = c.attr("name");
		if(name!=null){
			var myValue = $(":checkbox[name="+name+"][checked]").val();
		}else{
			if(c.attr("checked")){
				var myValue = c.val();
			}
		}
	}else{
		var myValue = c.val();
	}
	if(myValue==null){
		myValue = "";
	}
	
	try{
		c.data("validaParam",param);
		c.data("value",myValue);
		var validas = valida.split(",");
		if(validas.length>1){
			for(i =0;i<validas.length;i++){
				var data = eval(validas[i]).call(this,c,myValue);
				if(data==false){
					return data;
				}
			}
		}else{
			var data = eval(valida).call(this,c,myValue);
			return data;
		}		
	}catch(e){
		var data = false;
		c.attr("validaMsg","函数调用错误！");
		return data;
	}
	
}
/**初始化prism**/
$(func_initPrism);
function func_initPrism(){
	//formValidator
	var validas = $("[valida]");
	validas.blur(function() {
		var c = $(this);
		var data = func_formValidator(c);
		if(data==false){
			$("em",c.parent()).removeClass("checked");
			$("em",c.parent()).addClass("unchecked");
			$("em",c.parent()).html(c.attr("validaMsg"));
		}else{
			$("em",c.parent()).removeClass("unchecked");
			$("em",c.parent()).addClass("checked");
			$("em",c.parent()).html("");
		}
	});
	//dataGrid
	var initPrism = $("[prism='dataGrid']");
	for(var i = 0;i<initPrism.length;i++){
		var $this = initPrism.eq(i);
		var html = $this.html();
		$this.data("prismViewTemplate", html);
		$this.html("");
	}
	var initPrism = $("[prism='include']");
	for(var i = 0;i<initPrism.length;i++){
		var $this = initPrism.eq(i);
		var html = $this.html();
		$this.data("prismViewTemplate", html);
		$this.html("");
	}
	
}
/*********************************************/

/**补充函数**/
String.prototype.replaceAll  = function(s1,s2){    
	return this.replace(new RegExp(s1,"gm"),s2);    
};
/**翻页控件************************/
function update_page(total_page,current_page){
      var total_page = parseInt( total_page );
      var current_page = parseInt( current_page );
      var pager = new Array( pager_length );
      var header_length = 2; 
      var tailer_length = 2;
      var main_length = pager_length - header_length - tailer_length; //必须为奇数      
      var a_tag = 'a';
      var a_class = '';
      var a_id = '';
      var a_name = '';
      var disable_class = 'disable';
      var select_class = 'pageSelected';
      var i;
      var code = '';
      if( total_page < current_page ){
          alert('总页数不能小于当前页数');
          return false;    
      }    
      //判断总页数是不是小于 分页的长度，若小于则直接显示
      if( total_page < pager_length ){
          for(i = 0; i <     total_page; i++){
              code += (i+1 != current_page) ? fill_tag(a_tag, a_class, a_id, a_name, i+1) : fill_tag(a_tag, select_class, a_id, a_name, i+1);
          }
      }else{//如果总页数大于分页长度，则为一下函数
          //先计算中心偏移量
          var offset = ( pager_length - 1) / 2;
          //分三种情况，第一种左边没有...
          if( current_page <= offset + 1){
              var tailer = '';
              //前header_length + main_length 个直接输出之后加一个...然后输出倒数的    tailer_length 个
              for( i = 0; i < header_length + main_length; i ++)
                  code += (i+1 != current_page) ? fill_tag(a_tag, a_class, a_id, a_name, i+1) : fill_tag(a_tag, select_class, a_id, a_name, i+1);
              code += fill_tag(a_tag, a_class, a_id, a_name, '...');
              for(i = total_page; i > total_page - tailer_length; i --)
                  tailer = fill_tag(a_tag, a_class, a_id, a_name, i) + tailer;
              code += tailer;
          }else if( current_page >= total_page - offset ){//第二种情况是右边没有...
              var header = '';
              //后tailer_length + main_length 个直接输出之前加一个...然后拼接 最前面的 header_length 个
              for( i = total_page; i >= total_page-main_length - 1; i --)
                  code = (( current_page != i ) ? fill_tag(a_tag, a_class, a_id, a_name, i) : fill_tag(a_tag, select_class, a_id, a_name, i)) + code;
              code = fill_tag(a_tag, a_class, a_id, a_name, '...') + code;
              for( i = 0; i < header_length ; i++)
                  header +=  fill_tag(a_tag, a_class, a_id, a_name, i + 1);
              
              code = header + code;
          }
          //最后一种情况，两边都有...
          else
          {
              var header = '';
              var tailer = '';
              //首先处理头部
              for( i = 0; i < header_length; i ++)
                  header += fill_tag(a_tag, a_class, a_id, a_name, i + 1);
              header += fill_tag(a_tag, a_class, a_id, a_name, '...');
              //处理尾巴
              for(i = total_page; i > total_page - tailer_length; i --)
                  tailer = fill_tag(a_tag, a_class, a_id, a_name, i) + tailer;
              tailer = fill_tag(a_tag, a_class, a_id, a_name, '...') + tailer;
              //处理中间
              //计算main的中心点
              var offset_m = ( main_length - 1 ) / 2;
             var partA = '';
             var partB = '';
             var j;
             var counter = (parseInt(current_page) + parseInt(offset_m));
             for(i = j = current_page ; i <= counter; i ++, j --)
             {
                 partA = (( i == j ) ? '' : fill_tag(a_tag, a_class, a_id, a_name, j)) + partA;
                 partB += ( i == j ) ? fill_tag(a_tag, select_class, a_id, a_name, i) : fill_tag(a_tag, a_class, a_id, a_name, i);
             }
             //拼接
             code = header + partA + partB + tailer;  
         }
     }    
     var prev = ( current_page == 1 ) ? fill_tag(a_tag, disable_class, a_id, a_name, '上一页') : fill_tag(a_tag, a_class, current_page-1, a_name, '上一页');
     var next = ( current_page == total_page ) ? fill_tag(a_tag, disable_class, a_id, a_name, '下一页') : fill_tag(a_tag, a_class, current_page+1, a_name, '下一页');
     code = prev + code + next;
	 return code;
 }
 function fill_tag(a_tag, a_class, a_id, a_name, a_html){
     a_class = (a_class == '') ? '' : ' class="'+a_class+'"';
	 if(a_id==""){
     	a_id = (!parseInt(a_html)) ? '' : ' current_page="'+a_html+'"';
	 }else{
		 a_id = ' current_page="'+a_id+'"';
	 }
     a_name = (a_name == '') ? '' : ' name="'+a_name+'"';
     var code = '<'+a_tag+a_class+a_id+a_name+' >'+a_html+'</'+a_tag+'>';
     return code;
 }