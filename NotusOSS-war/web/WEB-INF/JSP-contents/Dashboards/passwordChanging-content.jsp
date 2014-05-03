<%@page import="nc.notus.entity.OSSUser"%>
<%@page import="java.util.List"%>
<script>
	var show;
	function hidetxt(type) {
		param = document.getElementById(type);
		if (param.style.display == "none") {
			if (show)
				show.style.display = "none";
			param.style.display = "block";
			show = param;
		} else
			param.style.display = "none";
	}
</script>

<% 
	String login = request.getParameter("login") == null ? "" : request.getParameter("login");
	String lastName = request.getParameter("lastName") == null ? "" : request.getParameter("lastName");
	String email = request.getParameter("email") == null ? "" : request.getParameter("email");
 %>

<h3><%=request.getAttribute("errMessage") == null ? "" : request.getAttribute("errMessage")%></h3>

<form id="get_users" method="get" action="GetUsers">
	<div>
		<a onclick="hidetxt('div1'); return false;" href="#" rel="nofollow">Search by Last Name</a>
		<div style="display: none;" id="div1">
			<table>
				<tr>
					<td>Last Name:</td>
					<td><input type="text" name="lastName" value="" /></td>
				</tr>
			</table>
		</div>
	</div>
	<div>
		<a onclick="hidetxt('div2'); return false;" href="#" rel="nofollow">Search by login</a>
		<div style="display: none;" id="div2">
			<table>
				<tr>
					<td>Login:</td>
					<td><input type="text" name="login" value="" /></td>
				</tr>
			</table>
		</div>
	</div>
	<div>
		<a onclick="hidetxt('div3'); return false;" href="#" rel="nofollow">Search by email</a>
		<div style="display: none;" id="div3">
			<table>
				<tr>
			<td>Email:</td>
			<td><input type="text" name="email" value="" />
			<td>
		</tr>
			</table>
		</div>
	</div>

	<input type="submit" value="Find user" />
</form>

<h3><%=request.getAttribute("success") == null ? "" : request.getAttribute("success")%></h3>
<%
	if (request.getAttribute("findedUsers") != null) {
		List<OSSUser> users = (List<OSSUser>) request.getAttribute("findedUsers");
		if (users.size() < 1) {
			out.print("<h4>User for the specified parameter not found!</h4>");
		} else {
%>


<table  border="1" id="tablename1">
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
			<td><input type="submit" name="action" value="View info" /></td>
			<td><input type="hidden" value="<%=user.getId()%>" name="userId" /></td>
		</tr>

	</form>
	<%
		}
	if (request.getAttribute("noOfPages") != null && request.getAttribute("page") != null) {
			long noOfPages = (Long) request.getAttribute("noOfPages");
			int currPage = (Integer) request.getAttribute("page");

			for (long i = 1; i <= noOfPages; i++) {
				if (i == (currPage + 1)) {
	%>
	<a
		href="GetUsers?page=<%=i%>&lastName=<%=lastName%>&login=<%=login%>&email=<%=email%>"
				style="font-size: 12pt; font-weight: bold;"><%=i%>&nbsp;&nbsp;&nbsp;&nbsp;</a>
	<%
		} else {
	%>


	<a
		href="GetUsers?page=<%=i%>&lastName=<%=lastName%>&login=<%=login%>&email=<%=email%>"><%=i%>&nbsp;&nbsp;&nbsp;&nbsp;</a>
	<%
		}
					}
				} else {
					out.print("something bad");
				}
	%>
</table>
<%
	}

	}
%>
