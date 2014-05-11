<%-- 
    Document   : reassignTaskEngineer-content
    Created on : 01.05.2014, 17:28:21
    Author     : Alina Vorobiova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.List"%>
<%@page import="nc.notus.entity.OSSUser"%>
<%@page import="nc.notus.entity.Task"%>

<%
	List<OSSUser> engineers = (List) request.getAttribute("listOfEngineers");
	Integer numberOfPages = (Integer) request.getAttribute("pages");
	Integer taskId = Integer.parseInt(request.getParameter("taskID"));
        String role = (String)request.getAttribute("role");
%>
<h3 align="center">You are reassigning task #<%=taskId%> for <%=role%>s</h3>
<form action="Reassign" method="post">
	<table id="table">
		<tr>
			<th>Employee ID</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Action</th>
		</tr>

		<%
			if (engineers != null) {
				for (OSSUser engineer : engineers) {
		%>
		<tr>

			<td><%=engineer.getId()%></td>
			<td><%=engineer.getFirstName()%></td>
			<td><%=engineer.getLastName()%></td>
			<td><input type="submit" id="button" value="Assign"
				onclick="document.getElementById('engineerID').setAttribute('value', <%=engineer.getId()%>)"></td>
		</tr>
		<%
			}
			}
		%>
	</table>
	<input type="hidden" id="engineerID" name="engineerID"> <input
		type="hidden" name="taskID"
		value="<%=request.getParameter("taskID")%>">
</form>

<div style="text-align: center"><ul class="pagination">
	<%
		if (numberOfPages > 1) {
			int currentPage = (Integer) request.getAttribute("page");
			boolean isPrevious = false;

			if (currentPage > 1) {
				isPrevious = true;
	%>
	<li><a href="ReassigTaskToEngineerServlet?page=<%=(currentPage - 1)%>&taskID=<%=taskId%>">&laquo;</a></li>
	<%
		}

			boolean previousPageIsEllipsis = false;

			for (long i = 1; i <= numberOfPages; i++) {
				if (i == (currentPage)) {
					if (!isPrevious) {
	%>
    <li class="disabled"><a href="#">&laquo;</a></li>
	<%
		}
	%>
	<li class="active"><a href="#"><%=i%></a></li>
	<%
		} else {
					if (i == 1 || i == 2 || i == currentPage - 2
							|| i == currentPage - 1 || i == currentPage + 1
							|| i == currentPage + 2
							|| i == numberOfPages - 1 || i == numberOfPages) {
						previousPageIsEllipsis = false;
	%>
	<li><a href="ReassigTaskToEngineerServlet?page=<%=(i)%>&taskID=<%=taskId%>">
		<%=i%>
	</a></li>
	<%
		} else {
						if (previousPageIsEllipsis) {
							//an ellipsis was already added. Do not add it again. Do nothing.
							continue;
						} else {
	%>
	<li class="disabled"><a href="#">...</a></li>
	<%
		previousPageIsEllipsis = true;
						}
					}
				}
			}

			if (currentPage != numberOfPages) {
	%>
	<li><a
		href="ReassigTaskToEngineerServlet?page=<%=(currentPage + 1)%>&taskID=<%=taskId%>">&raquo;</a></li>
	<%
		} else {
	%>
	<li class="disabled"><a href="#">&raquo</a></li>
	<%
		}
		}
	%>

</ul></div>


