<%@page import="nc.notus.entity.OSSUser"%>
<%@page import="java.util.List"%>

<script>
    var show;
    function hidetxt(type) {
        //clean all fields if chosen other criteria to searching
        document.getElementById("get_users").reset();

        param = document.getElementById(type);
        if (param.style.display == "none") {
            if (show) {
                show.style.display = "none";
            }
            param.style.display = "block";
            show = param;
        } else
            param.style.display = "none";
    }
</script>

<%
    String login = request.getParameter("login") == null ? "" : request
                    .getParameter("login");
    String lastName = request.getParameter("lastName") == null ? ""
                    : request.getParameter("lastName");
    String email = request.getParameter("email") == null ? "" : request
                    .getParameter("email");
    boolean isAdmin = request.isUserInRole("ADMINISTRATOR");
%>

<h3 style="color: red;">
    <%=request.getAttribute("errMessage") == null ? "" :
        request.getAttribute("errMessage")%>
</h3>
<h3 style="color: green;">
    <%=request.getAttribute("success") == null ? "" :
        request.getAttribute("success")%>
</h3>

<form id="get_users" method="get" action="GetUsers">
    <div id="passChange">
        <a onclick="hidetxt('div1'); return false;" href="#" rel="nofollow">
            Search by Last Name:
        </a>
        <div style="display: none;" id="div1">
            <table id="table" style="margin: 0">
                <tr>
                    <td>Last Name:</td>
                    <td><input type="text" id="lastName" name="lastName" value="" /></td>
                </tr>
            </table>
        </div>
    </div>
    <div id="passChange">
        <a onclick="hidetxt('div2'); return false;" href="#" rel="nofollow">
            Search by login:
        </a>
        <div style="display: none;" id="div2">
            <table id="table" style="margin: 0">
                <tr>
                    <td>Login:</td>
                    <td><input type="text" id="login" name="login" value="" /></td>
                </tr>
            </table>
        </div>
    </div>
    <div id="passChange">
        <a onclick="hidetxt('div3'); return false;" href="#" rel="nofollow">
            Search by email:
        </a>
        <div style="display: none;" id="div3">
            <table id="table" style="margin: 0">
                <tr>
                    <td>Email:</td>
                    <td><input type="text" id="email" name="email" value="" />
                </tr>
            </table>
        </div>
    </div>
    <button type="submit" class="btn btn-sm btn-warning">Find user</button>
</form>

<%
if (request.getAttribute("findedUsers") != null) {
    List<OSSUser> users = (List<OSSUser>) request.getAttribute("findedUsers");
        if (users.size() < 1) {
                out.print("<h3>User with the specified parameter was not found!</h3>");
        } else {
%>

<table class="table table-striped table-hover">
    <tr>
        <th>LOGIN</th>
        <th>EMAIL</th>
        <th>FIRST NAME</th>
        <th>LAST NAME</th>
<%      if (isAdmin) { %>
        <th>BLOCK USER</th>
<%      } else { %>
        <th>NEW PASSWORD</th>
        <th></th>
        <th colspan="2">ACTION</th>
<% } %>
    </tr>
<%
            for (OSSUser user : users) {
%>
<form method="post" action="Support">
    <tr>
        <td><%=user.getLogin()%></td>
        <td><%=user.getEmail()%></td>
        <td><%=user.getFirstName()%></td>
        <td><%=user.getLastName()%></td>
                <% if (!isAdmin) { %>
        <td><input class="form-control"type="text" name="newPassword" value="" />
                </td><td><button type="submit" class="btn btn-warning" name="action">Apply</button></td>
        <td><a href="CustomerUser?userID=<%=user.getId()%>"
                target="_blank">View information about SO and SI</a></td>
                <% } else { %>
        <td><button type="submit" class="btn btn-danger" name="action" >Block user</button></td>
            <% } %>
    </tr>
     <input type="hidden" value="<%=user.getId()%>" name="userId" />
</form>
<%
            }

        }
%>
</table>

<div style="text-align: center;">
    <ul class="pagination">
<%  }
if (request.getAttribute("noOfPages") != null 
		 && request.getAttribute("page") != null) {
    long noOfPages = (Long) request.getAttribute("noOfPages");
    if (noOfPages > 1) {
        int currentPage = (Integer) request.getAttribute("page");

        boolean isPrevious = false;
        if (currentPage > 1) {
            isPrevious = true;
%>
    <li>
        <a href="GetUsers?page=<%=(currentPage - 1)%>&lastName=<%=lastName%>&login=<%=login%>&email=<%=email%>">
            &laquo;
        </a>
    </li>
<%
	}

        boolean previousPageIsEllipsis = false;
        for (long i = 1; i <= noOfPages; i++) {
            if (i == (currentPage)) {
                if (!isPrevious) {
%>
    <li class="disabled"><a href="#">&laquo;</a></li>
<%
                }
%>
    <li class="active"><a href="#"><%=i%></a></li>
<%
            } else {
		if (i == 1 || i == 2 || i == currentPage - 2
                                    || i == currentPage - 1
                                    || i == currentPage + 1
                                    || i == currentPage + 2
                                    || i == noOfPages - 1
                                    || i == noOfPages) {
			previousPageIsEllipsis = false;
%>
    <li>
        <a href="GetUsers?page=<%=i%>&lastName=<%=lastName%>&login=<%=login%>&email=<%=email%>">
            <%=i%>
        </a>
    </li>
<%
                } else {
                    if (previousPageIsEllipsis) {
                        //an ellipsis was already added. Do not add it again. Do nothing.
                        continue;
                    } else {
%>
    <li class="disabled"><a href="#">...</a></li>
<%
                        previousPageIsEllipsis = true;
                    }
                }
            }
        }

        if (currentPage != noOfPages) {
%>
    <li>
        <a href="GetUsers?page=<%=(currentPage + 1)%>&lastName=<%=lastName%>&login=<%=login%>&email=<%=email%>">
            &raquo;
        </a>
    </li>
<%      } else { %>
    <li class="disabled"><a href="#">&raquo</a></li>
<%
        }
    }
} %>
    </ul>
</div>