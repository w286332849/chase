<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 标签库引入 -->
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/tag/c.tld" %>
<%@ taglib prefix="fn" uri="/WEB-INF/tag/fn.tld" %>

<!DOCTYPE html>
<html>
<head>
	
	<title>Chase ↗</title>
	
	<!-- 设置项目根路径 -->
	<c:set value="<%=request.getContextPath()%>" var="path"></c:set>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<link rel="stylesheet" type="text/css" href="${path}/common/easyui/themes/metro/easyui.css">
	<link rel="stylesheet" type="text/css" href="${path}/common/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${path}/common/css/chase.css">

	<script type="text/javascript" src="${path}/common/easyui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${path}/common/easyui/jquery.easyui.min.js"></script>
	<!-- <script type="text/javascript" src="${path}/common/easyui/easyui-lang-zh_CN.js"></script> -->
	
	<script type="text/javascript">
		(function($){
		
			$.path = "${path}";   // 可以再任意js中使用 $.path,即可得到当前的path名称
		
		})(jQuery);	
	</script>
	
</head>
</html>