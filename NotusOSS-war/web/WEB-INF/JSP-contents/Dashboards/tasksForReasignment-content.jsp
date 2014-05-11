<%-- 
    Document   : tasksForReasignment-content
    Created on : 01.05.2014, 16:21:06
    Author     : Alina Vorobiova
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="nc.notus.entity.Task"%>

<%
    List<Map<String,String>> tasksInfo = (List) request.getAttribute("listOfTasks");
    Integer numberOfPages = (Integer) request.getAttribute("pages");
%>

<form action="ReassigTaskToEngineerServlet" method="post" id="sendTask">
    <table class="table table-hover table-striped">
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

<% if (tasksInfo != null) {
        for (Map<String,String> task : tasksInfo) { %>
        <tr>
            <td><%=task.get("taskID")%></td>
            <td><%=task.get("soAdr")%></td>
            <td><%=task.get("taskName")%></td>
            <td><%=task.get("empID")%></td>
            <td><%=task.get("name")%></td>
            <td><%=task.get("surname")%></td>
            <td><%=task.get("role")%></td>
            <td><button type="submit" class="btn btn-primary" onclick="document
                    .getElementById('taskID').setAttribute('value',
                <%=task.get("taskID")%>)">Reassign</button>
            </td>
                <%-- <td id="send"></td> --%>
        </tr>
<%
        }
    }
%>
	</table>
	<input type="hidden" name="taskID" id="taskID">
</form>

<div style="text-align: center">
    <ul class="pagination">
<%          if (numberOfPages > 1) {
                int currentPage = (Integer) request.getAttribute("page");
                boolean isPrevious = false;
                if (currentPage > 1) {
                        isPrevious = true;
%>
        <li><a href="Reassign?page=<%=(currentPage - 1)%>">&laquo;</a></li>
<% }
            boolean previousPageIsEllipsis = false;

                    for (long i = 1; i <= numberOfPages; i++) {
                        if (i == (currentPage)) {
                            if (!isPrevious) {
%>
        <li class="disabled"><a href="#">&laquo;</a></li>
                         <% } %>
        <li class="active"><a href="#"><%=i%></a></li>
<%                      } else {
                            if (i == 1 || i == 2 || i == currentPage - 2
                                            || i == currentPage - 1
                                            || i == currentPage + 1
                                            || i == currentPage + 2
                                            || i == numberOfPages - 1
                                            || i == numberOfPages) {
                                    previousPageIsEllipsis = false;
%>
        <li><a href="Reassign?page=<%=(i)%>"><%=i%></a></li>
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
        <li><a href="Reassign?page=<%=(currentPage + 1)%>">&raquo;</a></li>
<%
                    } else {
%>
        <li class="disabled"><a href="#">&raquo</a></li>
<%
                    }
            }
%>
    </ul>
</div>