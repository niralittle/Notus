<%--
    Document   : login-content
    Created on : 12 04 2014, 19:32:43
    Author     : Panchenko Dmytro
--%>


<h2>Please enter your username and password to access the system.</h2>


<form method="POST" action="Auth">
    <table cellpadding="0" cellspacing="0" border="0">
        <tr>
            <td align="right">Username:&nbsp;</td>
            <td><input required="required" type="text" name="j_username"></td>
        </tr>
        <tr>
            <td align="right">Password:&nbsp;</td>
            <td><input required="required" type="password" name="j_password"></td>
        </tr>
        <tr>
            <td></td> 
            <td><input type="submit" value="Login" id="btn-style"></td>
        </tr>
    </table>
    <input id="serviceLocationID" name="serviceLocationID" type='hidden' value ="<%=request.getParameter("serviceLocationID") == null ? "" : request.getParameter("serviceLocationID")%>">
    <input id="serviceCatalogID" name="serviceCatalogID" type='hidden' value ="<%=request.getParameter("serviceCatalogID") == null ? "" : request.getParameter("serviceCatalogID")%>">
</form>
