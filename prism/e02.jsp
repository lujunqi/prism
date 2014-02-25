<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>无标题文档</title>
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/jquery.prism.js"></script>
<script type="text/javascript">
$(init);
var param = {};
var req = {};
param["data"] = req;
function init(){
	$("#list").prism(param);
}
</script>
</head>



<body>
<table id="" class="comTabList" width="100%" border="0" cellspacing="0" cellpadding="0">
      <thead>
          <tr>
            <th>图片</th>
            <th>文字内容</th>
            <th>链接</th>
            <th nowrap>排序</th>
          </tr>
      </thead> 
      <tbody id="list" prism:type="dataGrid" prism:src="t0902.do">
        <tr>
        <td></td>
        <td></td>
        <td></td>
        
        </tr>
      </tbody>
    </table>
</body>
</html>
