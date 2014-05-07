<%@page import="nc.notus.entity.OSSUser"%>
<%@page import="java.util.List"%>
<script>
	var show;
	function hidetxt(type) {
		//clean all fields if chosen other criteria to searching
		document.getElementById("get_users").reset();
		
		param = document.getElementById(type);
		if (param.style.display == "none") {
			if (show) {
				show.style.display = "none";
			}
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
 	boolean isAdmin = request.isUserInRole("ADMINISTRATOR");
 %>

<h3><%=request.getAttribute("errMessage") == null ? "" : request.getAttribute("errMessage")%></h3>
<h3><%=request.getAttribute("success") == null ? "" : request.getAttribute("success")%></h3>

<form id="get_users" method="get" action="GetUsers">
	<div id="passChange">
		<a onclick="hidetxt('div1'); return false;" href="#" rel="nofollow">Search by Last Name:</a>
		<div style="display: none;" id="div1">
                    <table id="table" style="margin:0">
				<tr>
					<td>Last Name:</td>
					<td><input type="text" id="lastName" name="lastName" value="" /></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="passChange">
		<a onclick="hidetxt('div2'); return false;" href="#" rel="nofollow">Search by login:</a>
		<div style="display: none;" id="div2">
			<table id="table" style="margin:0">
				<tr>
					<td>Login:</td>
					<td><input type="text" id="login" name="login" value="" /></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="passChange">
		<a onclick="hidetxt('div3'); return false;" href="#" rel="nofollow">Search by email:</a>
		<div style="display: none;" id="div3">
			<table id="table" style="margin:0">
				<tr>
			<td>Email:</td>
			<td><input type="text" id="email" name="email" value="" />
			
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


<table  border="1" id="table">
	<tr>
		<th>LOGIN</th>
		<th>EMAIL</th>
		<th>FIRST NAME</th>
		<th>LAST NAME</th>
		<th>STATUS</th>
		<th>NEW PASSWORD</th>
                <th colspan="2">ACTION</th>
	</tr>
	<%
		for (OSSUser user : users) {
	%>
	<form method="post" action="Support">
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
			<% if (!isAdmin){ %>
			<td><input type="text" name="newPassword" value="" /></td>
			<td><input type="submit" value="Change password" name="action"/></td>
			<td><a href="CustomerUser?userID=<%=user.getId()%>" target="_blank">View information about SO and SI</a></td>
			<% } else { %>
				<td><input type="submit" value="Block user" name="action"/></td>
			<% } %>
			<td><input type="hidden" value="<%=user.getId()%>" name="userId" /></td>
		</tr>

	</form>
	<%
		}
	if (request.getAttribute("noOfPages") != null && request.getAttribute("page") != null) {
			long noOfPages = (Long) request.getAttribute("noOfPages");
			if(noOfPages > 1) {
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
				} 
				}
	%>
</table>
<%
	}

	}
%>
