<%@page import="java.util.List"%>
<%@page import="nc.notus.entity.Task"%>

<a href="passwordChanging.jsp">Change user password</a> 

<%
	if (request.getAttribute("success") != null) {
		out.print(request.getAttribute("success") + "\n\n");
		request.setAttribute("success", null);
	}
	if (request.getAttribute("tasks") == null) {
		if (request.getAttribute("notification") != null)
			out.print(request.getAttribute("notification"));
	} else {
		List<Task> tasksEng = (List<Task>) request.getAttribute("tasks");
%>

<form method="Post" action="Support">
	<table id="tablename1">
		<tr>
			<td>Task Number</td>
			<td>Service Order ID</td>
		</tr>
		<%
			for (Task task : tasksEng) {
		%>

		<tr>
			<td><input type="text" name="taskid" value="<%=task.getId()%>"
				readonly="readonly" /></td>
			<td><input type="text" name="serviceorderid"
				value="<%=task.getServiceOrderID()%>" readonly="readonly" /></td>
		</tr>

		<%
			}
		%>
	</table>
	<input type="submit" value="Approve bills" />
</form>

<%
	}
%>
