<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/import.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<style type="text/css">
	.body{
		background-image: url('${path}/source/image/background.png')
	}
	.login {
	    margin-left: 430px;
	    margin-top: 200px;
	}
	</style>
</head>
  
  <body class="body">
  	<div class="login">
  	<div class="easyui-panel" title="登陆" style="width:400px">
		<div style="padding:10px 0 10px 60px">
	    <form id="loginForm" method="post" name="loginForm" action="${path}/login/login!login.action">
	    	<table style="margin: 20px;">
	    		<tr>
	    			<td>用户名:</td>
	    			<td><input class="easyui-validatebox" type="text" name="username" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>密码:</td>
	    			<td><input class="easyui-validatebox" type="password" name="password" data-options="required:true"></input></td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
	    </div>
	</div>
	</div>
  </body>
  <script type="text/javascript">
  		$(function(){
			var responseCode = "${responseCode}";
			if(responseCode != ""){
				switch(responseCode){
					case "LOGIN_002" :
						$.messager.alert('提示','用户名或者密码错误','error');
					break;
					case "LOGIN_003" :
						$.messager.alert('提示','用户已失效','error');
					break;
					case "LOGIN_004" :
						$.messager.alert('提示','验证码错误','error');
					break;
					default:
						$.messager.alert('提示','未知错误','error');
					break;
				}
			}
		});
		
		function clearForm() {
		    $('#loginForm').form('clear');
		}
		
		function submitForm() {
			$('#loginForm').submit();
		}
  </script>
</html>
