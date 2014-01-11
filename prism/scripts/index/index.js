var param = {};
var req = {};
param.data = req;	
$(init2);
function init2(){
	
	$("#leftTree").prism(param,function(data){
		
		$("li[level!=1]","#leftTree").hide();
		$("li","#leftTree").click(function(){
			var id = $(this).attr("JSIndex")-1;
			var c = $(".C"+data[id]["MENU_ID"],"#leftTree");
			if(!c.is(":visible")){
				c.show();
			}else{
				c.hide();
			}
			var menu_url = data[id]["MENU_URL"];
			if(menu!=null){
				alert(menu_url);
			}
			
		});
	});
	
	frameSize();
	$(window).resize(frameSize);
	$("#togBtn").click(function(){
		var tSide = $("#tSide");
		if(tSide.is(":visible")){
			$("#tSide").hide();
			$("#main").css({height:vHeight,width:vWidth-$("#togBtn").width()});
			//$(this).html("&lt;");
		}else{
			$("#tSide").show();
			$("#main").css({height:vHeight,width:vWidth-$("#tSide").width()-$("#togBtn").width()});
			//$(this).html("&gt;");
		}
	});
}
function frameSize(){
	
	var wHeight = $(window).height();
	$("body").css({height:wHeight});
	vWidth = $("#myBody").width();
	vHeight = wHeight -140;

	$("#togBtn").css({height:vHeight});
	
	$("#side").css({height:vHeight});
	$("#main").css({height:vHeight,width:vWidth-$("#tSide").width()-$("#togBtn").width()});
}