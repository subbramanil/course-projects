<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<script language="javascript">
    function forceHttpsOnSubmit(objForm) {
        objForm.action = objForm.action.replace("http:", "https:").replace("localhost:8080","localhost:8443");
    }
</script>
</head>
<body>
Login page
<!-- <form action="/j_spring_security_check" method="post" onsubmit="forceHttpsOnSubmit(this)"> -->
<form action="login" method="post">
	<label for="userName">User Name</label>
	<input type="text" id="userName" name="userName"/><br/>
	<label for="password">Password</label>
	<input type="password" id="pwd" name="password"/>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<input type="submit" value="login"/>
</form>
</body>
</html>