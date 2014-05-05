<%-- 
    Document   : tasksForReasignment-content
    Created on : 01.05.2014, 16:21:06
    Author     : Alina Vorobiova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.List"%>
<%@page import="nc.notus.entity.Task"%>


<form action="ReassigTaskToEngineerServlet" method="post" id="sendTask">
<table>
    <tr>
        <th>Task ID</th>
        <th>Service order ID</th>
        <th>Name</th>
        <th>Employee ID</th>
        <th>Role ID</th>
        <th>Action</th>
    </tr>

<%
if(tasks != null){
    for(Task task : tasks){
%>
<tr>

    <td><%=task.getId()%></td>
    <td><%=task.getServiceOrderID()%></td>
    <td><%=task.getName()%></td>
    <td><%=task.getEmployeeID()%></td>
    <td><%=task.getRoleID()%></td>
    <td><input type="submit" value="Reassign" id="btn-style" onclick="document.getElementById('taskID').setAttribute('value', <%=task.getId()%>)"></td>
   <%-- <td id="send"></td>  --%>
</tr>
<%  }
}%>
</table>
<input type="hidden" name="taskID" id="taskID">
</form>

<%List<Task> tasks = (List) request.getAttribute("listOfTasks");
Integer numberOfPages = (Integer)request.getAttribute("pages");
%>
<form action="Reassign" method="get" id="pagesForm">
    <% for(int i=1;i<=numberOfPages;i++){%>
    <a href="" onclick="document.getElementById('page').setAttribute('value', <%=i%>);this.parentNode.submit(); return false;"><%=i%></a>
    <% }%>
    <input type="hidden" name="page" id="page">
</form>