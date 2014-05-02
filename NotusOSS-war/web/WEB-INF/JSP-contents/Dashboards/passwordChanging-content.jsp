<%@page import="nc.notus.entity.OSSUser"%>
<%@page import="java.util.List"%>


<form id="get_users" method="get" action="GetUsers">
	<h3>Write login or email for search user</h3>
	<table>
		<tr>
			<td>Last Name:</td>
			<td><input type="text" name="lastName"
				value="<%=request.getParameter("lastName") == null ? "" : request
					.getParameter("lastName")%>" /></td>
		</tr>
		<tr>
			<td>Login:</td>
			<td><input type="text" name="login"
				value="<%=request.getParameter("login") == null ? "" : request
					.getParameter("login")%>" /></td>
		</tr>
		<tr>
			<td>Email:</td>
			<td><input type="text" name="email"
				value="<%=request.getParameter("email") == null ? "" : request
					.getParameter("email")%>" />
			<td>
		</tr>
	</table>

	<input type="submit" value="Find user" />
</form>

<h3><%=request.getAttribute("success") == null ? "" : request
					.getAttribute("success")%></h3>
<%
	if (request.getAttribute("findedUsers") != null) {
		List<OSSUser> users = (List<OSSUser>) request
				.getAttribute("findedUsers");
		if (users.size() < 1) {
			out.print("<h4>User for the specified parameter not found!</h4>");
		} else {
%>


<table border="1">
	<tr>
		<td>LOGIN</td>
		<td>EMAIL</td>
		<td>FIRST NAME</td>
		<td>LAST NAME</td>
		<td>STATUS</td>	
		<td>NEW PASSWORD</td>
	</tr>

		<%
			for (OSSUser user : users) {
		%>
		<form method="post" action="ChangeUserPassword">		
			<tr>
				<td><%=user.getLogin()%></td>
				<td><%=user.getEmail()%></td>
				<td><%=user.getFirstName()%></td>
				<td><%=user.getLastName()%></td>
				<td>
					<%
					if (user.getBlocked() == 1) {
						out.print("BLOCKED");
					} else {
						out.print("ACTIVE");
					}
					%>
				</td>

				<td><input type="text" name="newPassword" value="" /></td>
				<td><input type="submit" value="Change password" /></td>
				<td><input type="hidden" value="<%=user.getId()%>"name="userId" /></td>
			</tr>
			
		</form>
				<%
			}
		%>
</table>
<%
	}

	}
%>
