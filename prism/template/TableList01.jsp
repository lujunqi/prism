<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map" %>
<%@ page import="com.prism.common.JsonUtil" %>
<%
/**
* TITLE:页面标题
* MAPPING:显示标题，内容对应：TITLE(标题)DATA(内容)
* DATAURL:数据源
* EXCELURL:导出EXCEL
* SELECT:查询功能 
* PAGETOTAL:记录总数，翻页
*/
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String title = (String)request.getAttribute("TITLE");
List<?> mapping = (List<?>)request.getAttribute("MAPPING");
List<?> select = (List<?>)request.getAttribute("SELECT");
List keys = new ArrayList();
List values = new ArrayList();
for(int i=0;i<mapping.size();i++){
	Map m = (Map)mapping.get(i);
	keys.add(m.get("TITLE"));
	values.add(m.get("DATA"));
}

String dataurl = (String)request.getAttribute("DATAURL");
String pageTotal = (String)request.getAttribute("PAGETOTAL");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><%=title%></title>
<base href="<%=basePath%>">
</base>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/global-min.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/common.js"></script>
<script type="text/javascript" src="scripts/jquery.prism.js"></script>
<script type="text/javascript" src="lhgdialog/lhgdialog.min.js?skin=dblue"></script>
<script type="text/javascript">
var param = {};
var req ={};
param["data"] = req;
<%if(pageTotal!=null){%>
	req["@minnum"] = 0;
	param["total_page"] = "#pages";
	param["pageSize"] = 10;
	param["display"] = 10;
	param["total_url"] = "<%=pageTotal%>";
<%}%>
$(init);
function init(){

	$("#dt").prism(param,initComTablList);
<%
if(select!=null){
	out.println("$(\"#sltBtn\").click(sltView);");
}
%>
}
function initComTablList(){
	$('.comTabList').each(function() {
		$(this).find('tr:first').addClass('first').end().find('tr:last').addClass('last');
		$('tr:odd', this).addClass('odd');
		$('tr:even', this).addClass('eve');
		$('tr', this).each(function() {
			$(this).find('th:last, td:last').addClass('last');
		});
		$('tr').hover(function() {
			$(this).addClass('hover');
		}, function() {
			$(this).removeClass('hover');
		});
		return false;
	});
}
function expExcel(){
	document.getElementById("report").submit();
}
function sltView(){
	$.dialog({
		title:"<%=title%>",
		content: '查询',
		max: false,
		min: false,
		cancelVal: '关闭',
		cancel: true 
	});
}
</script>
</head>

<body class="mainBody">
<div class="wrapper comWrap">
  <div class="wrap-tit clearfix">
    <h3 class="wrap-tit-l"> <span class="icos ico_1"><%=title%></span> </h3>
    <p class="wrap-tit-r">
      <%
if(select!=null){
	out.println("<a class=\"addItem\" id=\"sltBtn\">查询</a>");
}
%>
    </p>
  </div>
  <div class="wrap-inner"> 
    <!-- wrap-inner begin****************************************** -->
    <table id="" class="comTabList" width="100%" border="0"
				cellspacing="0" cellpadding="0">
      <tr>
        <%
for(int i=0;i<keys.size();i++){
	out.print("<th>"+keys.get(i)+"</th>");
}
%>
      </tr>
      <tbody id="dt" prism:type="dataGrid" prism:loading="数据正在加载中..." prism:src="<%=dataurl%>">
        <tr>
          <%
for(int i=0;i<values.size();i++){
	out.print("<td>"+values.get(i)+"</td>");
}
%>
        </tr>
      </tbody>
    </table>
    <div class="p10">
<%if(pageTotal!=null){%>
      <div id="pages" class="pages tr clearfix"> <span class="batch fl mr10" style="display:none;"> </span> <span class="info fl"> <em>共有<b id="total_pages"></b>条数据，当前第<b id="curr_pages">1</b>页</em> </span> <span id="pagesList" class="list"> </span> </div>
<%}%>
    </div>
    <!-- wrap-inner end****************************************** --> 
  </div>
  <div class="wrap-btm clearfix">
    <div class="wrap-btm-l">&nbsp;</div>
    <div class="wrap-btm-r">&nbsp;</div>
  </div>
</div>

<!--/.wrapper-->

</body>
</html>