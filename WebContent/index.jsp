<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Troy, Oracle ACS</title>
</head>
<img src="2000px-Oracle_logo.svg.png" height="30"/>
<hr>
<h1> <center> Welcome to Mbean Server State Monitroing by Troy.</h1>
<hr>
<%	if(request.getAttribute("Message")!=null)
		{ out.print(request.getAttribute("Message")); } %>

<form name="login" method="post" action="MonitorHealth">
	SERVER_HOST : <input type="text" name="serverhost"/> <br>
	SERVER_PORT : <input type="text" name="serverport"/> <br> <br>
	USERNAME    : <input type="text" name="username"/> <br>
	PASSWORD    :<input type="password" name="password"/> <br>
    <input type="submit" value="Server Status" />
</form>

    <div class="foot"> 
		<%@ include file="jsp/footer.jsp" %>
    </div>
<body>
</body>
</html>