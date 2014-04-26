<%--
    Document   : installationEngineer-content.jsp
    Created on : Apr 21, 2014, 6:48:00 PM
    Author     : Roman Martyniuk & Vladimir Ermolenko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="nc.notus.entity.Task"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Installation Engineer page</title>
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
                <form action="SubmitTask" method="POST">
                    <tr>
                        <td>
                            <input type="text" name="taskid" value="<%=task.getId()%>" readonly="readonly"/>
                        </td>
                        <td>
                            <input type="text" name="serviceorderid" value="<%=task.getServiceOrderID()%>" readonly="readonly"/>
                        </td>
                        <td>
                            <input type="hidden" name="login" value="<%=request.getUserPrincipal().getName()%>"/>
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
