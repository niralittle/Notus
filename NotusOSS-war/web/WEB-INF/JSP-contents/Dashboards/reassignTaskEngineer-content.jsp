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

<%List<OSSUser> engineers = (List) request.getAttribute("listOfEngineers");
Integer numberOfPages = (Integer)request.getAttribute("pages");
%>

<form action="Reassign" method="post">
<table id="table">
    <tr>
        <th>Employee ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Action</th>
    </tr>

<%if (engineers != null){
      for(OSSUser engineer : engineers){
%>
<tr>

    <td><%=engineer.getId()%></td>
    <td><%=engineer.getFirstName()%></td>
    <td><%=engineer.getLastName()%></td>
    <td><input type="submit" id="button" value="Assign"
               onclick="document.getElementById('engineerID').setAttribute('value', <%=engineer.getId()%>)"></td>
</tr>
<%    }
  }%>
</table>
<input type="hidden" id="engineerID" name="engineerID">
<input type="hidden" name="taskID" value="<%=request.getParameter("taskID")%>">
</form>
<form action="ReassigTaskToEngineerServlet" method="GET" id="pagesForm">
    <% for(int i=1;i<=numberOfPages;i++){%>
    <a href="" onclick="document.getElementById('page').setAttribute('value', <%=i%>);this.parentNode.submit(); return false;">
    <%=i%>
    </a>
    <% }%>
    <input type="hidden" name="page" id="page">
    <input type="hidden" name="taskID" value="<%=request.getParameter("taskID")%>">
</form>