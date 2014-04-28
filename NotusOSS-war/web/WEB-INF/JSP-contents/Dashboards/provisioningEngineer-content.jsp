<%-- 
    Document   : provisioningEngineer-content
    Created on : Apr 23, 2014, 12:19:59 PM
    Author     : Vladimir Ermolenko & Panchenko Dmytro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="nc.notus.entity.Task"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%-- 
    Document   : provisioningEngineer-content
    Created on : Apr 23, 2014, 12:19:59 PM
    Author     : Vladimir Ermolenko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="nc.notus.entity.Task"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Provisionoing Engineer page</title>
    </head>
    <body>
 <%   if(request.getAttribute("newScenarioTasks") == null
		&& request.getAttribute("disconnectScenarioTasks") == null) {
			out.print("\n\nAny taks was found! You are free at this moment!");
		} else {
		if(request.getAttribute("newScenarioTasks") != null) {
        	List<Task> newTasks = (List) request.getAttribute("newScenarioTasks");
        int userID = (Integer) request.getAttribute("userid");%>
        <table border="1" id="tablename1">
        <caption>NEW SCENARIO TASKS</caption>
            <tbody>
                <tr>
                    <td>
                        Task Number
                    </td>
                    <td>
                        Service Order ID
                    </td>
                    <td>
                        Action
                    </td>
                </tr>
                <%for (Task task : newTasks) {%>
                <form action="CreateCircuit" method="POST">
                    <tr>
                        <td>
                            <input type="text" name="taskid" value="<%=task.getId()%>" readonly="readonly"/>
                        </td>
                        <td>
                            <input type="text" name="serviceorderid" value="<%=task.getServiceOrderID()%>" readonly="readonly"/>
                        </td>
                        <td>
                            <input type="hidden" name="username" value="<%=request.getUserPrincipal().getName()%>"/>
                            <input type="hidden" name="userid" value="<%=userID%>"/>
                            <input type="submit" name="action" value="Submit" />
                        </td>
                    </tr>
                    </form>
                    <% } %>
                </tbody>
            </table>
<%
}
%>

<%
if(request.getAttribute("disconnectScenarioTasks") != null) {
        	List<Task> disconTasks = (List) request.getAttribute("disconnectScenarioTasks");

%>
	<form method="Post" action="ProvisionEngineerTasksServlet">
	<table id="tablename2">
	<caption>DISCONNECT SCENARIO TASKS</caption>
		<tr>
			<td>Task Number</td>
			<td>Service Order ID</td>
		</tr>
		<%
			for (Task task : disconTasks) {
		%>

		<tr>
			<td><input type="text" name="d_taskid" value="<%=task.getId()%>"
				readonly="readonly" /></td>
			<td><input type="text" name="d_orderid"
				value="<%=task.getServiceOrderID()%>" readonly="readonly" /></td>
			<td><input type="submit" value="Delete Circuit from SI" name="action" /></td>
		</tr>

		<%
			}
		%>
	</table>
	
</form>
<%
}
}
%>
    </body>
</html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Provisionoing Engineer page</title>
    </head>
    <body>
        <%List<Task> tasks = (List) request.getAttribute("tasks");
        int userID = (Integer) request.getAttribute("userid");%>
        <table border="1" id="tablename1">
            <tbody>
                <tr>
                    <td>
                        Task Number
                    </td>
                    <td>
                        Service Order ID
                    </td>
                    <td>
                        Action
                    </td>
                </tr>
                <%for (Task task : tasks) {%>
                <form action="CreateCircuit" method="POST">
                    <tr>
                        <td>
                            <input type="text" name="taskid" value="<%=task.getId()%>" readonly="readonly"/>
                        </td>
                        <td>
                            <input type="text" name="serviceorderid" value="<%=task.getServiceOrderID()%>" readonly="readonly"/>
                        </td>
                        <td>
                            <input type="hidden" name="username" value="<%=request.getUserPrincipal().getName()%>"/>
                            <input type="hidden" name="userid" value="<%=userID%>"/>
                            <input type="submit" name="action" value="Submit" />
                        </td>
                    </tr>
                    </form>
                    <% } %>
                </tbody>
            </table>

    </body>
</html>
