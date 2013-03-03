<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/import.jsp" %>
<!DOCTYPE html>
<html>
  <body>
  	
	  <h2> 测试事物</h2> 
	  
	  <form action="${path}/user/user!testTx.action" method="post">
	  	<label>userVo1</label><br>
	  	<input type="text" name="userVo.username"><br>
	  	<input type="text" name="userVo.password"><br>
	  	<input type="text" name="userVo.status"><br>
	  	
	  	<label>userVo2</label><br><br>
	  	<input type="text" name="userVo2.username"><br>
	  	<input type="text" name="userVo2.password"><br>
	  	<input type="text" name="userVo2.status"><br>
	  	
	  	
	  	<input type="submit" value="提交">
	  </form>
	 
  </body>
</html>
