<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/import.jsp" %>
<!DOCTYPE html>
<html>
  <body>
	  <a href = "${path}/user/user!loadUser.action?userVo.id=8a80808f3d2b6cf0013d2b7112600000">Test load by OpenSessionInView</a>
	  <br>
	   ${user.username}  
	   ${user.password}
	   ${user.id}
	  <br>
  </body>
</html>
