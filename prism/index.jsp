<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>卡布管理后台</title>
<link rel="stylesheet" type="text/css" href="css/global-min.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/jquery.prism.js"></script>
<script type="text/javascript" src="lhgdialog/lhgdialog.min.js?skin=blue"></script>
<script type="text/javascript" src="scripts/index/index.js"></script>
<script type="text/javascript">

</script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	overflow:hidden;
}
</style>
</head>

<body>
<div id="myBody"
 style="width:100%; margin:auto">
  <div id="header">
    <h1 id="site-name">卡布管理后台</h1>
    <div id="site-logo" class="fl"><a href="#"><img src="images/logo.jpg" alt="卡布管理后台" width="591" height="94" /></a></div>
    <div id="site-banner" class="fr">&nbsp;</div>
    <div class="clear"></div>
    <div id="main-nav" class="clearfix">
      <ul class="clearfix">
        <li><span class="mr5">系统管理员【<%=session.getAttribute("USER_NAME")%>】</span>欢迎您</li>
        <li class="timer"><span id="date" style="color:#FFF;font-size:12px;"></span></li>
      </ul>
      <a class="logout" id="logout" href="#">退出系统</a> <a class="logout" id="updatePassword" href="javascript:window.parent.updatePassword();">修改密码</a> </div>
  </div>
  <div id="tSide" style="float:left; width:190px;">
    <ul id="leftTree" prism:type="dataGrid" prism:src="manager.SysMenu.do" style="width:190px;">
      <li class="#@SUP_MENU#" style="margin-left:#@LV#0px;cursor:pointer;" level="#@LV#" JSIndex="#@JSIndex#"> #@MENU_NAME# </li>    
    </ul>
  </div>
  <div id="togBtn" style="width:10px; cursor:pointer; float:left;"> 
  	<div style="background: url(images/toggleSide.gif) no-repeat;width: 12px;height: 51px;line-height: 51px; ">&lt;</div> 
  </div>
  <div style="float:left; background:#0C3;">
    <iframe scrolling="auto" width="100%" src="welcome.html" id="main" name="main" frameborder="0" marginwidth="0"></iframe>
  </div>
</div>
</body>
</html>
