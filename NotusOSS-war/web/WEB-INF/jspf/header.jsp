<div id="top_menu">

    <div class="dashboard_links_etc" width="610px" align="right" style="padding: 6px; margin-right: 3px;">

        <%  if (request.isUserInRole("CUSTOMER_USER")) {%>
        <a href="CustomerUser">Go to Cabinet</a>
        <div class="separator"></div>
        <%  } else if (request.isUserInRole("ADMINISTRATOR")) {%>
        <a href="Reassign">Reassign task</a>
        <div class="separator"></div>

        <a href="registerEngineer.jsp">Register new engineer</a>
        <div class="separator"></div>

        <a href="passwordChanging.jsp">Block user</a>
        <div class="separator"></div>

        <a href="adminDashboard.jsp">Reports</a>
        <div class="separator"></div>
        <%  } else if (request.isUserInRole("PROVISION_ENGINEER") ||
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

        <%  if (request.getUserPrincipal() == null) {%>
        <a href="login.jsp">Login</a>
        <%  } else {%>
        <a href="logout">Logout</a>
        <%  }%>

    </div>

    <div id="pages_list">
        <ul>
            <li>
                <div id="home">
                    <a href="index.jsp" id="home">
                        <h1>NOTUS</h1>
                    </a>
                </div>
            </li>
            <%  if (!(request.isUserInRole("SUPPORT_ENGINEER") ||
                    request.isUserInRole("PROVISION_ENGINEER") ||
                    request.isUserInRole("INSTALLATION_ENGINEER") ||
                    request.isUserInRole("ADMINISTRATOR"))) {%>
            <li><a href="selectLocation.jsp">SELECT SERVICE</a></li>
            <li><a href="serviceCatalog.jsp">OUR SERVICES</a> </li>
            <%  }%>
            <!-- Don't display this links 
	            <li><a href="#">OUR TEAM</a></li>
	            <li><a href="#">PROMOTIONS</a></li>
	            <li><a href="#">SOCIAL</a></li>
            -->
        </ul>
    </div>
</div>
