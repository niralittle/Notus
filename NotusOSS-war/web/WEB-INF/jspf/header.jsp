<div id="top_menu">
    <%  if (request.getUserPrincipal() == null && request.getParameter("serviceLocationID") == null
        && !request.getRequestURL().toString().contains("login.jsp")){%>
    <form method="POST" action="Auth" class="navbar-form navbar-right" role="form">
        <div class="form-group">
            <input required type="text" name="j_username"  placeholder="Login" class="form-control">
        </div>
        <div class="form-group">
            <input required type="password" name="j_password" placeholder="Password" class="form-control">
        </div>
        <button type="submit" class="btn btn-primary" value="Login">Login</button>
    </form>
    <%  } %>
        <div class="dashboard_links_etc" width="610px" align="right" style="height: 37px; padding: 6px; margin-right: 3px;">

        <%  if (request.isUserInRole("CUSTOMER_USER")) {%>
        <a href="CustomerUser">Go to Cabinet</a>
        <div class="separator"></div>
        <%  } else if (request.isUserInRole("ADMINISTRATOR")) {%>
        <a href="Reassign">Reassign task</a>
        <div class="separator"></div>
        <a href="groupMailSend.jsp">Send group mail</a>
        <div class="separator"></div>
        <a href="registerEngineer.jsp">Register new engineer</a>
        <div class="separator"></div>

        <a href="passwordChanging.jsp">Block user</a>
        <div class="separator"></div>

        <a href="adminDashboard.jsp">Reports</a>
        <div class="separator"></div>
        <%  } else if (request.isUserInRole("PROVISIONING_ENGINEER") ||
          request.isUserInRole("SUPPORT_ENGINEER") ||
          request.isUserInRole("INSTALLATION_ENGINEER")) {%>

        <a href="TasksAssignment">Group Tasks</a>
        <div class="separator"></div>
        <a href="TasksAssignment?type=personal">Personal Tasks</a>
        <div class="separator"></div>
        <% if (request.isUserInRole("SUPPORT_ENGINEER")) {%>
        <a href="passwordChanging.jsp">View user information</a>
        <div class="separator"></div>
        <%  }%>
        <%  }%>

        <%  if (request.getUserPrincipal() != null) {%>
        <a href="logout">Logout</a>
        <%  }%>

        <%  if (request.getUserPrincipal() != null) {%>
        <div style="text-align: right; margin-top: 30px;  height: 36px">
            Welcome, <%=request.getUserPrincipal().getName()%> !
        </div>
        <%  }%>

    </div>

    <div id="pages_list">
        <div id="home">
            <div style="width: 200px;float:left;margin-top: -30px;">
                <a href="index.jsp">
                    <h1>NOTUS</h1>
                </a>
            </div>
        </div>
        <div style="text-align: right; margin-top: 50px; height: 36px">
            <ul>

    <%  if (!(request.isUserInRole("SUPPORT_ENGINEER") ||
            request.isUserInRole("PROVISIONING_ENGINEER") ||
            request.isUserInRole("INSTALLATION_ENGINEER") ||
            request.isUserInRole("ADMINISTRATOR"))) {%>
                <li><a href="selectLocation.jsp">SELECT SERVICE</a></li>
                <li><a href="ServiceCatalogServlet">SERVICE CATALOG</a> </li>
    <%  }%>
            </ul>
       </div>
    </div>
</div>
