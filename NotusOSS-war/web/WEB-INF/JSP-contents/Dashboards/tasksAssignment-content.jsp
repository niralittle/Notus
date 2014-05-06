<%-- 
    Document   : tasksAssignment-content
    Created on : Apr 27, 2014, 10:33:06 AM
    Author     : Vladimir Ermolenko
--%>


<%@page import="java.util.List"%>
<%@page import="nc.notus.entity.Task"%>
<%@page import="nc.notus.entity.OSSUser"%>

        <%List<Task> tasksEng = (List) request.getAttribute("tasksEng");
        boolean personal = (Boolean) request.getAttribute("type");
        String title = "Group";
        if (personal){
            title = "Personal";
        }
        OSSUser user = (OSSUser) request.getAttribute("user");
       %>
        <h2>
            <%=title+" "%> tasks assignmnet for <%=user.getFirstName() + " " + user.getLastName()%>
        </h2>
        

        
        
        
        <table border="1" id="table">
            <tbody>
                <tr>
                    <td>
                        Task Number
                    </td>
                    <td>
                        Service Order ID
                    </td>
                    <td>
                        Task name
                    </td>
                    <td>
                        Action
                    </td>
                </tr>
                <%for (Task task : tasksEng) {%>
                <form action="TasksAssignment" method="POST">
                    <tr>
                        <td>
                            <input type="text" name="taskid" value="<%=task.getId()%>" readonly/>
                        </td>
                        <td>
                            <input type="text" name="serviceorderid" value="<%=task.getServiceOrderID()%>" readonly/>
                        </td>
                        <td>
                        	<input type="text"  value="<%=(task.getName() != null) ? task.getName() : ""%>" readonly/>
                        </td>
                        <td>
                            <input type="hidden" name="login" value="<%=request.getUserPrincipal().getName()%>"/>
                            <% if(personal) { %>
                            <input type="hidden" name="type" value="personal"/>
                            <% } %>
                            <input type="submit" name="action" id="button" value="Submit" />
                        </td>
                    </tr>
                    </form>
                    <% } %>
                </tbody>
            </table>
            

        <%
		if (request.getAttribute("noOfPages") != null && request.getAttribute("page") != null) {
			String url;
			long noOfPages = (Long) request.getAttribute("noOfPages");
			if(noOfPages > 1) {
			int currPage = (Integer) request.getAttribute("page");
			if(personal) {
				url = "TasksAssignment?type=personal&";
			} else {
				url = "TasksAssignment?";
			}
			for (long i = 1; i <= noOfPages; i++) {
				if (i == (currPage )) {
		%>
		<a href="<%=url%>page=<%=i%>" style="font-size: 12pt; font-weight: bold;"><%=i%>&nbsp;&nbsp;&nbsp;&nbsp;</a>
	<%
		} else {
	%>
		<a href="<%=url%>page=<%=i%>"><%=i%>&nbsp;&nbsp;&nbsp;&nbsp;</a>
	<%
		}
					}
					}
					}
					 %>
