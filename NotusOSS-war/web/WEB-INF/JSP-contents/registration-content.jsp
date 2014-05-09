<%--
    Document   : register-content
    Created on : 12 04 2014, 19:53:58
    Author     : Panchenko Dmytro
--%>

<script type="text/javascript" src="assets/registerValidator.js"></script>
<form name="Register" method="post" onsubmit="return validate()" action="Register">
    <input id="serviceLocationID" name="serviceLocationID" type='hidden' value ="<%=request.getParameter("serviceLocationID") == null ? "" : request.getParameter("serviceLocationID")%>">
    <input id="serviceCatalogID" name="serviceCatalogID" type='hidden' value ="<%=request.getParameter("serviceCatalogID") == null ? "" : request.getParameter("serviceCatalogID")%>">
    <h4>To join our happy customers, please, fill in the form below: </h4>

    <%= request.getAttribute("errMessage") == null ? "" : request.getAttribute("errMessage")%>

    <table border="0" cellpadding="5">
        <tr>
            <td>Login: </td>
            <td>
                <input required type="text" name="login" id="login" oninput="loginValidate()" 
                       value="<%=request.getParameter("login") == null ? "" : request.getParameter("login")%>" />
            </td>
            <td>
                <div id="loginMsg"></div>
            <td>
        </tr>
        <tr>
            <td>Password (6 characters minimum): </td>
            <td>
                <input required type="password" name="password" id="pass" value="" />
            </td>
            <td>
                <div id="passMsg"></div>
            </td>
        </tr>
        <tr>
            <td>Repeat password: </td>
            <td>
                <input required type="password" name="passwordConf" id="pass2" value="" />
            </td>
            <td>
                <div id="passMsg2"></div>
            </td>
        </tr>
        <tr>
            <td>Email: </td>
            <td>
                <input required type="text" name="email" oninput="emailValidate()" id="email"
                       value="<%=request.getParameter("email") == null ? "" : request.getParameter("email")%>" />
            </td>
            <td>
                <div id="emailMsg"></div>
            </td>
        </tr>

        <tr>
            <td>First name: </td>
            <td><input required type="text" name="firstName" id="fname"
                       value="<%=request.getParameter("firstName") == null ? "" : request.getParameter("firstName")%>" />
            </td>
            <td>
                <div id="fnameMsg"></div>
            </td>
        </tr>
        <tr>
            <td>Last name: </td>
            <td><input required type="text" name="lastName" id="lname"
                       value="<%=request.getParameter("lastName") == null ? "" : request.getParameter("lastName")%>" />
            </td>
            <td>
                <div id="lnameMsg"></div>
            </td>
        </tr>
		<tr>
			<td>Input text on image below: </td>
			<td><input type="text" name="code"></td>
		</tr>
		<tr>
			<td></td>
			<td><img  src="Captcha"></td>
		</tr>
	</table>
	

    <input type="submit" value="Register" />

    <a href="index.jsp"><input type="button" value="Home" name="submit"/> </a>

</form>
<form id="login" method="post" action="CustomerUser">
    <input id="serviceLocationID" name="serviceLocationID" type='hidden' value ="<%=request.getParameter("serviceLocationID") == null ? "" : request.getParameter("serviceLocationID")%>">
    <input id="serviceCatalogID" name="serviceCatalogID" type='hidden' value ="<%=request.getParameter("serviceCatalogID") == null ? "" : request.getParameter("serviceCatalogID")%>">
    <h4>Already registered!? </h4><a href="" onclick="this.parentNode.submit(); return false;">Login Here</a>

</form>
