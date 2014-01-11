<%@ page contentType="text/html; charset=utf-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<base href="/prism/" />
<link href="images/skin.css" rel="stylesheet" type="text/css" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jquery/jquery.prism.js"></script>
<script type="text/javascript">
$(init);
function init(){
	$("#priSpec").prism();
}
</script>
<title>设置权限</title>
</head>
<body style="height:100%;background:#EEF2FB;">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" valign="top" background="images/mail_leftbg.gif"><img src="images/left-top-right.gif" width="17" height="29" /></td>
    <td valign="top" background="images/content-bg.gif" style="height:31px;"><div class="titlebt">设置权限</div></td>
    <td width="16" valign="top" background="images/mail_rightbg.gif"><img src="images/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
  <tr>
    <td valign="middle" background="images/mail_leftbg.gif">&nbsp;</td>
    <td valign="top" bgcolor="#F7F8F9">
    <table class="gridTable" width="100%" border="0" cellspacing="0" style="margin-top:20px;">
        <tr>
          <th scope="col">权限名称</th>
          <th scope="col">权限编码</th>
          <th scope="col">操作</th>
        </tr>
        <tr>
          <td colspan="3" style="height:2px; background:#999"></td>
        </tr>
        <tbody>
          <tr prism:type="dataGrid" id="priSpec" prism:src="manager.priSpec.do" class="gridBody">
            <td>#@MANAGE_CD#</td>
            <td>#@PRI_SPEC_NAME#</td>
            <td>#@PRI_SPEC_ID#</td>
          </tr>
        </tbody>
      </table></td>
    <td background="images/mail_rightbg.gif"></td>
  </tr>
  <tr>
    <td valign="bottom" background="images/mail_leftbg.gif"><img src="images/buttom_left2.gif" width="17" height="17" /></td>
    <td height="17" background="images/buttom_bgs.gif"><img src="images/buttom_bgs.gif" width="17" height="17" /></td>
    <td valign="bottom" background="images/mail_rightbg.gif"><img src="images/buttom_right2.gif" width="16" height="17" /></td>
  </tr>
</table>
</body>
</html>