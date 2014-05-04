<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="assets/registerValidator.js"></script>

<title>Registrate new employee</title>
</head>
<body>
<br><a href="administratorPage.jsp">Go to main administrator page</a>
<br> 
       <%= request.getAttribute("errMessage") == null ? "" : request.getAttribute("errMessage")%>
        <form name="Register" method="post" onsubmit="return validate()" action="Register">
        <table border="0" cellpadding="5">
            <tr>
                <td>Login: </td>
                <td><input required type="text" name="login" id="login" oninput="loginValidate()" value="" />
                    <div id="loginMsg"></div>
                </td>
            </tr>
            <tr>
                <td>Password: </td>
                <td><input required type="password" name="password" id="pass" value="" />

                    <div id="passMsg"></div>
                </td>
            </tr>
            <tr>
                <td>Repeat password: </td>
                <td><input required type="password" name="passwordConf" id="pass2" value="" />

                    <div id="passMs2"></div>
                </td>
            </tr>
            <tr>
                <td>Email: </td>
                <td><input required type="text" name="email" oninput="emailValidate()" id="email"  value="" />
                    <div id="emailMsg"></div>
                </td>
            </tr>

            <tr>
                <td>First name: </td>
                <td><input required type="text" name="firstName" id="fname" value="" />
                    <div id="fnameMsg"></div>
                </td>
            </tr>
            <tr>
                <td>Last name: </td>
                <td><input required type="text" name="lastName" id="lname" value="" />
                    <div id="lnameMsg"></div>
                </td>
            </tr>
            <tr>
            	<td>Choose employee group</td>
            	<td>
            		<select name="employeeGroup">
            			<option value="2">Supporting</option>
            			<option value="3">Provisioning</option>
  						<option value="4">Installation</option>
					</select>
            	</td>
            </tr>
        </table>
            <input type="submit" value="Register" />
         </form> 

</body>
</html>
