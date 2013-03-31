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
	
	<!-- CSS引用 -->
	<link rel="stylesheet" type="text/css" href="${path}/common/easyui/themes/bootstrap/easyui.css">
	<link rel="stylesheet" type="text/css" href="${path}/common/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${path}/common/bootstrap/css/bootstrap.min.css"></link>
	<link rel="stylesheet" type="text/css" href="${path}/common/bootstrap/css/bootstrap-responsive.min.css"></link>
	<link rel="stylesheet" type="text/css" href="${path}/common/css/chase.css">
	<style type="text/css">
		body {
			font-family:"微软雅黑" "黑体" "新宋体",helvetica,tahoma,verdana,sans-serif;
		    font-size:12px;
		}
		
		.chase-info{
			background:#FFFEE6;
			color:#8F5700;
			padding:12px;
		}
		
		.chase-tip{
			width:16px;
			height:16px;
			margin-right:8px;
			float:left;
		}
		
		.blank{
			width: 100%;
			height: 10px;
			clear: both;
			overflow: hidden;
		}
	</style>
	
	<!-- JS引用 -->
	<script type="text/javascript" src="${path}/common/easyui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${path}/common/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${path}/common/js/chase.js"></script>
	<!-- <script type="text/javascript" src="${path}/common/easyui/easyui-lang-zh_CN.js"></script> -->
	<script type="text/javascript" src="${path}/common/bootstrap/js/bootstrap.min.js"></script>
	
	<!-- zTree插件引用 -->
	<link rel="stylesheet" type="text/css" href="${path}/source/ztree/css/zTreeStyle.css">
	<script type="text/javascript" src="${path}/source/ztree/js/jquery.ztree.all-3.5.js"></script>
	
	<script type="text/javascript">
		(function($){
		
			$.path = "${path}";   // 可以再任意js中使用 $.path,即可得到当前的path名称
			
		})(jQuery);	
		
		
		/**
		 * 添加String的replaceAll方法
		 * 
		 * source      String 目标字符串
		 * replaceWith String 替换的字符串
		 * ignoreCase  String:gi g : 是否忽略大小写
		 */
		String.prototype.replaceAll = function(source, replaceWith, ignoreCase) {
		    if (!RegExp.prototype.isPrototypeOf(source)) {
		        return this.replace(new RegExp(source, (ignoreCase ? "gi": "g")), replaceWith);
		    } else {
		        return this.replace(source, replaceWith);
		    }
		}
	</script>
	

</head>
</html>