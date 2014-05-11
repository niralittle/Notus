<%--
    Document   : installationEngineer-content.jsp
    Created on : Apr 21, 2014, 6:48:00 PM
    Author     : Roman Martyniuk & Vladimir Ermolenko
--%>

<%@page import="java.util.List"%>
<%@page import="nc.notus.entity.Task"%>

<script src="assets/dashboard.js"></script>
<div id="content">
    <div id ="content-menu">
        <ul>
            <li>
                <a href="javascript:void(0)" onclick="showTask()">Take Task</a>
            </li>
            <div id="showMenu">
                <li><a href="javascript:void(0)" onclick="showCrRouter()">Create Router</a></li>
                <li><a href="javascript:void(0)" onclick="showCrCable()">Create Cable</a></li>
                <li><a href="javascript:void(0)" onclick="showDelete()">Delete Cable</a></li>
            </div>
        </ul>
    </div>
    <div id ="contents">
        <div id="selectTask">
            <h3>Please, choose Task:</h3>
        <% List<Task> tasks = (List) request.getAttribute("tasks");
           int userID = (Integer) request.getAttribute("userid"); %>
            <form action="SubmitTask" method="POST">
                <table border="1" id="tablename1">
                    <tr>
                        <th>Task Number</th>
                        <th>Service Order ID</th>
                        <th>Action</th>
                    </tr>
                <%for (Task task : tasks) {%>
                    <tr>
                        <td>
                            <input type="text" name="taskid"
                                   value="<%=task.getId()%>" readonly="readonly"/>
                        </td>
                        <td>
                            <input type="text" name="serviceorderid"
                                   value="<%=task.getServiceOrderID()%>"
                                   readonly="readonly"/>
                        </td>
                        <td>
                            <input type="hidden" name="login"
                                   value="<%=request.getUserPrincipal().getName()%>"/>
                            <input type="hidden" name="userid"
                                   value="<%=userID%>"/>
                            <input type="submit" name="action" value="Submit" />
                        </td>
                    </tr>
                </form>
              <% } %>
            </table>
        <input type="submit" value="Action" onclick="show('showMenu',200,5)" />
        </div>
        <div id="createRouter">
            <h3>Create Router</h3>
            <table border="1" id="tablename1">
                <tbody>
                    <tr>
                        <td>Input Device Name:</td>
                        <td><input type="text" name="routerName" value="" /></td>
                    </tr>
                    <tr>
                        <td>Input Port Quantity</td>
                        <td><input type="text" name="portQuantity" value="" /></td>
                    </tr>
                    <tr>
                        <td colspan="2"><a href="#" class="button28">Create</a></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div id="createCable">
            <h3>Create Cable</h3>
            <p>Press Create button</p>
            <a href="javascript:void(0)" class="button28"onclick="f(id)">Create</a>
            <div id="cableCreateDiv"></div>
            <p>Now, connect the Cable to Port.</p>
            <h3>Connect to Port:</h3>
            <select name="PortsList"></select><br>
            <a href="javascript:void(0)" class="button28">Connect</a>
        </div>
        <div id="deleteRouter">
            <h3>Delete Cable</h3>
            <p>Choose Cable from the list below</p>
            <select name="deleteCabelList"></select>
            <a href="javascript:void(0)" class="button28">Delete</a>
        </div>
    </div>
</div>