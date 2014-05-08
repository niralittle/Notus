<%-- 
    Document   : provisioningEngineerWorkflow-content
    Created on : Apr 23, 2014, 12:20:17 PM
    Author     : Panchenko Dmytro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="nc.notus.entity.Port"%>
<%@page import="nc.notus.entity.Task"%>
<%@page import="nc.notus.entity.Cable"%>
<%@page import="nc.notus.entity.ServiceOrder"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h3 style="color:red;"><%=request.getAttribute("errMessage") == null ? "" : request.getAttribute("errMessage")%></h3>
<h3 style="color:green;"><%=request.getAttribute("success") == null ? "" : request.getAttribute("success")%></h3>
	
	<form action="TasksAssignment?type=personal" method="POST">
		<input type="submit" name="action" value="Back to Tasks" />
	</form>
	
	<%
		if ("NEW".equalsIgnoreCase((String) request.getAttribute("wfScenario"))) {
			Task task = (Task) request.getAttribute("task");
			Integer taskID = task.getId();
			Integer soID = task.getServiceOrderID();
	%>

	<form action="ProvisionEngineerTasksServlet" method="POST">
		<table border="1" id="table">
			<caption>NEW SCENARIO TASK</caption>
			<tbody>
				<tr>
					<th>Task ID</th>
					<th>Service Order ID</th>
					<th>Circuit</th>
				</tr>
				<tr>
					<td><%=taskID%></td>
					<td><%=soID%></td>
					<td><input type="text" name="circuit" /></td>
				</tr>
			</tbody>
		</table>

		<input type="hidden" name="taskid" value="<%=taskID%>" /> <input
			type="hidden" name="serviceorderid" value="<%=soID%>" /> <input
			type="submit" name="action" value="Create Circuit" />
	</form>
	<%
		} else if ("DISCONNECT".equalsIgnoreCase((String) request.getAttribute("wfScenario"))) {
			
			Task task = (Task) request.getAttribute("task");
			Integer taskID = task.getId();
			Integer soID = task.getServiceOrderID();
	%>
	
	<form method="Post" action="ProvisionEngineerTasksServlet">
		<table id="tablename2">
			<caption>DISCONNECT SCENARIO TASK</caption>
			<tr>
				<td>Task Number</td>
				<td>Service Order ID</td>
			</tr>

			<tr>
				<td><input type="text" name="d_taskid"
					value="<%=task.getId()%>" readonly="readonly" /></td>
				<td><input type="text" name="d_orderid"
					value="<%=task.getServiceOrderID()%>" readonly="readonly" /></td>
				<td><input type="submit" value="Delete Circuit from SI"
					name="action" /></td>
			</tr>

		</table>

	</form>

	<%
		}
	%>
</body>
</html>
