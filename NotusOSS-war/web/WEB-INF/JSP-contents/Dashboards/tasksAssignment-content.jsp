<%-- 
    Document   : tasksAssignment-content
    Created on : Apr 27, 2014, 10:33:06 AM
    Author     : Vladimir Ermolenko
--%>


<%@page import="java.util.List"%>
<%@page import="nc.notus.entity.Task"%>
<%@page import="nc.notus.entity.OSSUser"%>

        <%List<Task> tasksEng = (List) request.getAttribute("tasksEng");
        OSSUser user = (OSSUser) request.getAttribute("user");%>
        <h2>
            Tasks assignmnet for <%=user.getFirstName() + " " + user.getLastName()%>
        </h2>
        <form action="TasksAssignment" method="POST">
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
                
                    <tr>
                        <td>
                            <input type="text" name="taskid" value="<%=task.getId()%>" readonly="readonly"/>
                        </td>
                        <td>
                            <input type="text" name="serviceorderid" value="<%=task.getServiceOrderID()%>" readonly="readonly"/>
                        </td>
                        <td>
                            <input type="hidden" name="login" value="<%=request.getUserPrincipal().getName()%>"/>
                            <input type="submit" name="action" value="Submit" />
                        </td>
                    </tr>
                    
                    <% } %>
                </tbody>
            </table>
            </form>

