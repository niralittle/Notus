<div id="top_menu">

    <div class="dashboard_links_etc" width="610px" align="right" style="padding: 6px; margin-right: 3px;">
       	<%
			if (request.getUserPrincipal() == null) {
		%>
		        <a href="login.jsp">Login</a>
		<%
			} else {
		%>
		        <a href="logout">Logout</a>
		<%
			}
		%>
            <div class="separator"></div>
        <a href="/NotusOSS-war/InstallationEngineerTasks">Installation Engineer Page</a>
            <div class="separator"></div>
        <a href="/NotusOSS-war/ProvisionEngineerTasksServlet">Provisioning Engineer Page</a>
            <div class="separator"></div>
        <a href="/NotusOSS-war/Support">Support Engineer Page</a>
            <div class="separator"></div>
        <a href="admin.jsp">Admin page</a>
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
            <li><a href="selectLocation.jsp">SELECT SERVICE</a></li>
            <li><a href="serviceCatalog.jsp">OUR SERVICES</a> </li>
            <li><a href="#">OUR TEAM</a></li>
            <li><a href="#">PROMOTIONS</a></li>
            <li><a href="#">SOCIAL</a></li>
        </ul>
    </div>
</div>
