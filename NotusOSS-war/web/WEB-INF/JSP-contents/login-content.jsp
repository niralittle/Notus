<%--
    Document   : login-content
    Created on : 12 04 2014, 19:32:43
    Author     : Panchenko Dmytro
--%>


<h4>Please enter your username and password to access the system.</h4>


<form method="POST" action="Auth">
    <div class="form-group" style="width: 300px;">
        <label for="exampleInputEmail1">Username:</label>
        <input required="required" type="text" name="j_username" class="form-control" id="exampleInputEmail1" placeholder="Enter username">
    </div>
    <div class="form-group" style="width: 300px;">
        <label for="exampleInputPassword1">Password:</label>
        <input required="required" type="password" name="j_password" class="form-control" id="exampleInputPassword1" placeholder="Password">
    </div>
    <button type="submit" class="btn btn-primary">Login</button>
    <input id="serviceLocationID" name="serviceLocationID" type='hidden' value ="<%=request.getParameter("serviceLocationID") == null ? "" : request.getParameter("serviceLocationID")%>">
    <input id="serviceCatalogID" name="serviceCatalogID" type='hidden' value ="<%=request.getParameter("serviceCatalogID") == null ? "" : request.getParameter("serviceCatalogID")%>">

</form>
