<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Oracle ACS</title>
	<link rel="stylesheet" href="css/droplinetabs.css">
</head>

<body>

<table border = "0" width=100%>
	<tr>
		<td> <img src="images/2000px-Oracle_logo.svg.png" height="30"/> </td>
		<td align="right"> 
				<%	String user = (String)session.getAttribute("user");	
		    		if(user == null)
						{ out.print("<a href='login.jsp'> Sign In </a>"); }
					else{out.print(" Hello : " + user); }
					%>		
		</td>
	</tr>
</table>


<hr>
	
<ul class="droplinetabs">
<li><a href="index.jsp">Home</a></li>
<li><a href="javascript:vold(0)">User</a>
  <ul>
	  <li><a href="login.jsp">User Login</a></li>
	  <li><a href="logout.jsp">User Logout</a></li>
  </ul>
</li>
<li><a href="javascript:vold(0)">Activity</a>
  <ul>
	  <li><a href="submitActivity.jsp">Submit Activity</a></li>
	  <li><a href="findActivity.jsp">Find Activity</a></li>
	  <li><a href="#">View Activities</a></li>
  </ul>
</li>
<li><a href="javascript:vold(0)">Admin</a>
  <ul>
	  <li><a href="#">Add User</a></li>
	  <li><a href="#">Add Account</a></li>
	  <li><a href="#">View Report</a></li>
  </ul>
</li>
<li><a href="javascript:vold(0)">Folder 3</a>
  <ul>
	  <li><a href="#">Sub Item 3.1</a></li>
	  <li><a href="javascript:vold(0)">Folder 3.2</a>
	    <ul>
		    <li><a href="#">Sub Item 3.2.1</a></li>
		    <li><a href="#">Sub Item 3.2.2</a></li>
		    <li><a href="javascript:vold(0)">Folder 3.2.3</a>
					<ul>
			    		<li><a href="#">Sub Item 3.2.3.1</a></li>
			    		<li><a href="#">Sub Item 3.2.3.2</a></li>
			    		<li><a href="#">Sub Item 3.2.3.3</a></li>
			    		<li><a href="#">Sub Item 3.2.3.4</a></li>
			    		<li><a href="#">Sub Item 3.2.3.5</a></li>
					</ul>
		    </li>
		    <li><a href="#">Sub Item 3.2.4</a></li>
	    </ul>
	  </li>
  </ul>
</li>
<li><a href="javascript:vold(0)">About</a>
  <ul>
	  <li><a href="#">Contact</a></li>
	  <li><a href="help.jsp">Help</a></li>
  </ul>
</li>
</ul>

<br>

</body>
</html>