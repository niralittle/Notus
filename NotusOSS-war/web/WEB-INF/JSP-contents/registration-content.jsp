<%--
    Document   : register-content
    Created on : 12 04 2014, 19:53:58
    Author     : Katya Atamanchuk <nira@niralittle.name>
--%>

<script type="text/javascript" src="assets/registerValidator.js"></script>
<form name="Register" method="post" onsubmit="return validate()" action="Validate">

    <h4>To join our happy customers, please, fill in the form below: </h4>
    
    <%= request.getAttribute("errors") == null ? "" : request.getAttribute("errors")%>
						
    <table border="0" cellpadding="5">
        <tr>
            <td>Login: </td>
            <td>
                <input required type="text" name="login" id="login" oninput="loginValidate()" 
                value="<%=request.getParameter("login") == null ? "" : request.getParameter("login")%>" />
            </td>
            <td>
				<div id="loginMsg"></div>
				<%= request.getAttribute("loginMsg") == null ? "" 
						:request.getAttribute("loginMsg")%>
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
                <input required type="password" name="password2" id="pass2" value="" />
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
                <%= request.getAttribute("emailMsg") == null ? "" 
						:request.getAttribute("emailMsg")%>
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
    </table>

    <input type="submit" value="Register" />

    <a href="index.jsp"><input type="button" value="Home" name="submit"/> </a>

</form>

<h4>Already registered!? <a href="login.jsp">Login Here</a></h4>
