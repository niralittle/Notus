<%--
    Document   : register-content
    Created on : 12 04 2014, 19:53:58
    Author     : Katya Atamanchuk <nira@niralittle.name>
--%>

<script type="text/javascript" src="assets/registerValidator.js"></script>
<form name="register" method="post" onsubmit="return validate()">
    <h4>To join our happy customers, please, fill in the form below: </h4>
    <table border="0" cellpadding="5">
        <tr>
            <td>Login: </td>
            <td><input required type="text" name="login" id="login" value="" />
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
            <td><input required type="password" name="password2" id="pass2" value="" />

                    <div id="passMs2"></div>
            </td>
        </tr>
        <tr>
            <td>Email: </td>
            <td><input required type="text" name="email" id="email" value="" />
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
    </table>

    <input type="submit" value="Register" />

    <a href="index.jsp"><input type="button" value="Home" name="submit"/> </a>

</form>

<h4>Already registered!? <a href="login.jsp">Login Here</a></h4>
