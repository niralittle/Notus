<%-- 
    Document   : tasksAssignment-content
    Created on : Apr 27, 2014, 10:33:06 AM
    Author     : Vladimir Ermolenko
--%>


<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="nc.notus.entity.Task"%>
<%@page import="nc.notus.entity.OSSUser"%>

        <%List<Map<String, String>> tasksEngFull = (List<Map<String, String>>) request.getAttribute("tasksEngFull");
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
        <%
        if (request.getAttribute("actionStatus") != null) { %>
        <%=request.getAttribute("actionStatus")%>
        <% } %>

        
        
        
        <table class='table table-striped table-hover'>
            <thead>
                <tr>
                    <th>
                        Task Number
                    </th>
                    <th>
                        Task name
                    </th>
                    <th>
                        Service Location
                    </th>
                    <th>
                        Type of Service
                    </th>
                    <th>
                        Price
                    </th>
                    <th>
                        Action
                    </th>
                </tr>
            </thead>
            <tbody>
                <%for (Map<String, String> m: tasksEngFull) {%>
                    <tr>
                        <td>
                            <%=m.get("taskID")%>
                        </td>
                        <td>
                            <%= m.get("taskName") %>
                        </td>
                        <td>
                            <%= m.get("serviceLocation") %>
                        </td>
                        <td>
                            <%= m.get("serviceDescription") %>
                        </td>
                        <td>
                            <%= m.get("price") %>
                        </td>
                        <td>
                        <form action="TasksAssignment" method="POST">
                            <input type="hidden" name="taskid" value="<%=m.get("taskID")%>" />
                            <input type="hidden" name="login" value="<%=request.getUserPrincipal().getName()%>"/>
                            <input type="hidden" name="serviceDescription" value="<%=m.get("serviceDescription")%>" />
                            <input type="hidden" name="price" value="<%=m.get("price")%>" />
                            <% if(personal) { %>
                            <input type="hidden" name="type" value="personal"/>
                            <input type="submit" value="Execute" name="action" class="btn btn-primary">
                            <% } else { %>
                            <input type="submit" value="Assign to myself" name="action" class="btn btn-primary">
                            <% }  %>
                        </form>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>

<div style="text-align: center;"><ul class="pagination" style="margin: 0 0">
        <%
			if (request.getAttribute("noOfPages") != null && request.getAttribute("page") != null) {
			long noOfPages = (Long) request.getAttribute("noOfPages");
			if(noOfPages > 1) {

			int currentPage = (Integer) request.getAttribute("page");
			String url;
			if(personal) {
				url = "TasksAssignment?type=personal&";
			} else {
				url = "TasksAssignment?";
			}
	%>
	<li<%=currentPage > 1 ? "" : " class=\"disabled\""%>>
        <a href="<%=currentPage > 1 ? url + "page=" + (currentPage-1) : "#"%>">&laquo;</a></li>
	<%
			boolean previousPageIsEllipsis = false;

			for (long i = 1; i <= noOfPages; i++) {
				if (i == currentPage) {
			%>
             <li class="active"><a href="#">&nbsp;&nbsp;<%=i%>&nbsp;&nbsp;</a></li>

			<%
		} else {
		if( i == 1
		            || i == 2
		            || i == currentPage - 2
		            || i == currentPage - 1
		            || i == currentPage + 1
		            || i == currentPage + 2
		            || i == noOfPages - 1
		            || i == noOfPages)
		        {
		         previousPageIsEllipsis = false;
	%>
	<li><a  href="<%=url%>page=<%=i%>">&nbsp;&nbsp;<%=i%>&nbsp;&nbsp;</a></li>
	<%
		} else {
									if (previousPageIsEllipsis) {
										//an ellipsis was already added. Do not add it again. Do nothing.
										continue;
									} else { %>
										<li><a href="#">&nbsp;&nbsp;...&nbsp;&nbsp;</a></li>
									<%
										previousPageIsEllipsis = true;
									}
								}
							}
						}
				%>
    <li<%=currentPage < noOfPages ? "" : " class=\"disabled\""%>>
        <a href="<%=currentPage < noOfPages ? url + "page=" + (currentPage+1) : "#"%>">&raquo;</a></li>
			<%


					}
				}
	%>
</ul></div>
