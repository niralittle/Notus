<%-- 
    Document   : tasksForReasignment-content
    Created on : 01.05.2014, 16:21:06
    Author     : Alina Vorobiova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="nc.notus.entity.Task"%>

<%
	List<Map<String,String>> tasksInfo = (List) request.getAttribute("listOfTasks");
	Integer numberOfPages = (Integer) request.getAttribute("pages");
%>

<form action="ReassigTaskToEngineerServlet" method="post" id="sendTask">
	<table id="table">
		<tr>
			<th>Task ID</th>
			<th>Service order address</th>
			<th>Task Name</th>
			<th>Employee ID</th>
                        <th>Name</th>
                        <th>Surname</th>
			<th>Role</th>
			<th>Action</th>
		</tr>

		<%
			if (tasksInfo != null) {
				for (Map<String,String> task : tasksInfo) {
		%>
                
		<tr>

			<td><%=task.get("taskID")%></td>
			<td><%=task.get("soAdr")%></td>
			<td><%=task.get("taskName")%></td>
			<td><%=task.get("empID")%></td>
			<td><%=task.get("name")%></td>
                        <td><%=task.get("surname")%></td>
                        <td><%=task.get("role")%></td>
			<td><input type="submit" id="button" value="Reassign"
				onclick="document.getElementById('taskID').setAttribute('value', <%=task.get("taskID")%>)"></td>
			<%-- <td id="send"></td> --%>
		</tr>
		<%
			}
			}
		%>
	</table>
	<input type="hidden" name="taskID" id="taskID">
</form>
<div id="pagination">
	<%
		if (numberOfPages > 1) {
			int currentPage = (Integer) request.getAttribute("page");
			boolean isPrevious = false;

			if (currentPage > 1) {
				isPrevious = true;
	%>
	<a href="Reassign?page=<%=(currentPage - 1)%>">Previous</a>
	<%
		}

			boolean previousPageIsEllipsis = false;

			for (long i = 1; i <= numberOfPages; i++) {
				if (i == (currentPage)) {
					if (!isPrevious) {
	%>
	<label style="font-size: 14pt;">Previous</label>
	<%
		}
	%>
	<label>&nbsp;&nbsp;<%=i%>&nbsp;&nbsp;
	</label>
	<%
		} else {
					if (i == 1 || i == 2 || i == currentPage - 2
							|| i == currentPage - 1 
							|| i == currentPage + 1
							|| i == currentPage + 2
							|| i == numberOfPages - 1 
							|| i == numberOfPages) {
						previousPageIsEllipsis = false;
	%>
	<a href="Reassign?page=<%=(i)%>">&nbsp;&nbsp;<%=i%>&nbsp;&nbsp;
	</a>
	<%
		} else {
			if (previousPageIsEllipsis) {
				//an ellipsis was already added. Do not add it again. Do nothing.
				continue;
			} else {
	%>
	<label style="font-size: 12pt;">&nbsp;&nbsp;...&nbsp;&nbsp;</label>
	<%
		previousPageIsEllipsis = true;
						}
					}
				}
			}

			if (currentPage != numberOfPages) {
	%>
	<a href="Reassign?page=<%=(currentPage + 1)%>">Next</a>
	<%
		} else {
	%>
	<label style="font-size: 14pt;">&nbsp;&nbsp;Next&nbsp;&nbsp;</label>
	<%
		}
		}
	%>

</div>

<!--  
<form action="Reassign" method="post" id="pagesForm">
    <%for (int i = 1; i <= numberOfPages; i++) {%>
    <a href="" onclick="document.getElementById('page').setAttribute('value', <%=i%>);this.parentNode.submit(); return false;"><%=i%></a>
    <%}%>
    <input type="hidden" name="page" id="page">
</form>
-->