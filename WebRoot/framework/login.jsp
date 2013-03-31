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
  	<div class="easyui-panel" title="Sign in" style="width:500px">
		<div style="padding:10px 0 10px 60px">
	    <form id="loginForm" method="post" name="loginForm" action="${path}/login/login!login.action">
	    	<table>
	    		<tr>
	    			<td width="35%"><h6>UserName:</h6></td>
	    			<td width="65%"><input class="easyui-validatebox" type="text" name="username" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td><h6>Password:</h6></td>
	    			<td><input class="easyui-validatebox" type="password" name="password" data-options="required:true"></input></td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	    <div style="text-align:center;padding:10px">
	    	<a href="javascript:void(0)" class="btn btn-success" onclick="submitForm()">Submit</a>
	    	<a href="javascript:void(0)" class="btn btn-danger" onclick="clearForm()">Clear</a>
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
