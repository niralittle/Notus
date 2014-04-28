<%--
    Document   : installationEngineer-content.jsp
    Created on : Apr 21, 2014, 6:48:00 PM
    Author     : Roman Martyniuk & Vladimir Ermolenko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="nc.notus.entity.Task"%>
<script src="assets/dashboard.js"></script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

    <body>
        <div id="content">

            <div id ="content-menu">
                <ul>
                    <a href="javascript:void(0)" onclick="showTask()"><li>Take Task</li></a>
                    <div id="showMenu">
                        <a href="javascript:void(0)" onclick="showCrRouter()"><li>Create Router</li></a>
                        <a href="javascript:void(0)" onclick="showCrCable()"><li>Create Cable</li></a>
                        <a href="javascript:void(0)" onclick="showConnect()"><li>Connect Cable to Port</li></a>
                        <a href="javascript:void(0)" onclick="showDelete()"><li>Delete Cable</li></a>
                    </div>
                </ul>
            </div>
            <div id ="contents">

                <div id="selectTask">
                    <%List<Task> tasks = (List) request.getAttribute("tasks");
            int userID = (Integer) request.getAttribute("userid");%>
                    <table border="1" id="tablename1">

                        <thead>
                            <tr>
                                <th>Task Number</th>
                                <th>Service Order ID</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
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
                        <% }%>
                        </tbody>
                    </table>
                        <input type="submit" value="Action" onclick="show('showMenu',200,5)" />
                </div>

                <div id="createRouter">
                    2
                </div>

                <div id="createCable">
                    3
                </div>

                <div id="connectCabel">
                    4
                </div>

                <div id="deleteRouter">

                </div>
            </div>



        </div>



    </body>
