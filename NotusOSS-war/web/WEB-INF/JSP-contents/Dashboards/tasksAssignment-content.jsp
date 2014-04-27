<%-- 
    Document   : tasksAssignment-content
    Created on : Apr 27, 2014, 10:33:06 AM
    Author     : Vladimir Ermolenko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="nc.notus.entity.Task"%>
<%@page import="nc.notus.entity.OSSUser"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tasks Assignment page</title>
    </head>
    <body>
        <%List<Task> tasksEng = (List) request.getAttribute("tasksEng");
        OSSUser user = (OSSUser) request.getAttribute("user");%>
        <h2>
            Tasks assignmnet for <%=user.getFirstName() + " " + user.getLastName()%>
        </h2>
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
                <%for (Task task : tasksEng) {%>
                <form action="TasksAssignment" method="POST">
                    <tr>
                        <td>
                            <input type="text" name="taskid" value="<%=task.getId()%>" readonly="readonly"/>
                        </td>
                        <td>
                            <input type="text" name="serviceorderid" value="<%=task.getServiceOrderID()%>" readonly="readonly"/>
                        </td>
                        <td>
                            <input type="hidden" name="login" value="<%=request.getUserPrincipal().getName()%>"/>
                            <input type="hidden" name="user" value="<%=user%>"/>
                            <input type="submit" name="action" value="Assign" />
                        </td>
                    </tr>
                    </form>
                    <% } %>
                </tbody>
            </table>

    </body>
</html>
