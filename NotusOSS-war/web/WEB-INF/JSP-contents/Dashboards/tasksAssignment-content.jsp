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
        

        
        
        
        <table border="1" id="table">
            <tbody>
                <tr>
                    <td>
                        Task Number
                    </td>
                    <td>
                        Task name
                    </td>
                    <td>
                        Service Location
                    </td>
                    <td>
                        Type of Service
                    </td>
                    <td>
                        Price
                    </td>
                    <td>
                        Action
                    </td>
                </tr>
                <%for (Map<String, String> m: tasksEngFull) {%>
                <form action="TasksAssignment" method="POST">
                    <tr>
                        <td>
                            <input type="text" name="taskid" value="<%=m.get("taskID")%>" readonly/>
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
            
<div id="pagination">
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
			boolean isPrevious = false;
			if(currentPage > 1) {
                            isPrevious = true;
	%>
	<a style="font-size: 14pt;"  href="<%=url%>page=<%=(currentPage-1)%>">Previous</a>
	<%
	}
			boolean previousPageIsEllipsis = false;
			
			for (long i = 1; i <= noOfPages; i++) {
				if (i == currentPage) {
					if(!isPrevious) {
					%>
					<label style="font-size: 14pt;">Previous</label>
					<%
					}
			%>

			<label id="pagination_label" style="font-size: 12pt;">&nbsp;&nbsp;<%=i%>&nbsp;&nbsp;</label>
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
	<a  href="<%=url%>page=<%=i%>">&nbsp;&nbsp;<%=i%>&nbsp;&nbsp;</a>
	<%
		} else {
									if (previousPageIsEllipsis) {
										//an ellipsis was already added. Do not add it again. Do nothing.
										continue;
									} else { %>
										<label style="font-size: 12pt;">&nbsp;&nbsp;...&nbsp;&nbsp;</label>
									<%
										previousPageIsEllipsis = true;
									}
								}
							}
						}
					if(currentPage != noOfPages) {
				%>
			<a style="font-size: 14pt;"  href="<%=url%>page=<%=(currentPage+1)%>">Next</a>
			<%
			} else {
			%>
			<label style="font-size: 14pt;">&nbsp;&nbsp;Next&nbsp;&nbsp;</label>
			<%
			}
				
					
					}
				}
	%>
</div>