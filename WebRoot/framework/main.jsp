<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/import.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<script type="text/javascript" src="${path}/framework/main.js"></script>
</head>
<body>
   <div class="easyui-layout" style="width:100%;height:640px;">
   
        <div data-options="region:'north'" style="height:50px">
        </div>  
        
        <div data-options="region:'west',split:true" title="Menu" style="width:180px;"> 
        	<ul id="menuTree" class="ztree"></ul>
        </div>  
        
        <div data-options="region:'center',title:'Welcome ${user.username}'">  
        	<div id="mainTab" class="easyui-tabs" fit="true"> 
	             <div id="tabs" title="About" style="padding:5px">
	             	<h3>Welcome to use Chase Framework</h3>
	             </div> 
            </div> 
        </div> 
    </div>
</body>  
</html>
