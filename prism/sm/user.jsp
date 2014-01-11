<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>卡布管理后台</title>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"></base>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/global-min.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link href="scripts/paginate/jPaginate.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/jquery.prism.js"></script>
<script type="text/javascript" src="lhgdialog/lhgdialog.min.js?skin=dewblue"></script>
<script type="text/javascript" src="scripts/jquery.paginate.js"></script>
<script type="text/javascript" src="scripts/common.js"></script>

<script type="text/javascript" src="scripts/sm/user.js"></script>
</head>

<body class="mainBody">
<div class="wrapper comWrap">
  <div class="wrap-inner">
    <table id="" class="comTabList" width="100%" border="0" cellspacing="0" cellpadding="0">
      <caption class="wrap-tit clearfix">
      <h3 class="wrap-tit-l"><span class="icos ico_1">通用列表</span></h3>
      <p class="wrap-tit-r"><a class="addItem" href="javascript:addUser()">新增管理员</a></p>
      </caption>
      <tr>
        <th width="3%"><input type="checkbox" id="cbSelectAll" name="cbSelectAll" class="" title="" /></th>
        <th width="10%">账号</th>
        <th width="12%">用户名</th>
        <th width="15%">操作</th>
      </tr>
      <tbody id="user" class="comTabList" prism:type="dataGrid" prism:src="user_info!selectObject.action">
        <tr class="cls212">
          <td><input type="checkbox" id=""  name="tabCheck" class="" title="" /></td>
          <td>#@LOGIN_NAME#</td>
          <td>#@USER_NAME#</td>
          <td><span class="operate"> <a href="javascript:initPwd(#@USER_ID#)">[密码初始化]</a> <a href="javascript:privilege(#@USER_ID#)">[授权]</a><a href="javascript:cancelPrivilege(#@USER_ID#)">[取消授权]</a> <a href="javascript:del(#@USER_ID#)">[注销]</a> </span></td>
        </tr>
      </tbody>
    </table>
    <div style="200px;">
    <div id="pages" style="width:400px;"></div>
    </div>
  </div>
  <div class="wrap-btm clearfix">
    <div class="wrap-btm-l">&nbsp;</div>
    <div class="wrap-btm-r">&nbsp;</div>
  </div>
</div>
<!--/.wrapper-->

</div>
</body>
</html>