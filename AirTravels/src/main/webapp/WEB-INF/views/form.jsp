<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test form</title>
</head>
<body>
	<form:form method="get" commandName="user">
		<form:input path="mailID"/>
		<input type="submit" value="checkMailID"/>
	</form:form>
	{result}
	<%-- <form method="get" action="checkMe">
		<input type="text" id="name" name="username"/>
		<input type="submit" value="submit"/>
	</form>
	${result} --%>
</body>
</html>