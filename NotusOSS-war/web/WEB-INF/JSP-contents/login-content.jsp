<%--
    Document   : login-content
    Created on : 12 04 2014, 19:32:43
    Author     : Katya Atamanchuk <nira@niralittle.name>
--%>


<p>Please enter your username and password to access the system.</p>

<form method="POST" action="j_security_check">
    <table cellpadding="0" cellspacing="0" border="0">
        <tr>
            <td align="right">Username:&nbsp;</td>
            <td><input type="text" name="j_username"></td>
        </tr>
        <tr>
            <td align="right">Password:&nbsp;</td>
            <td><input type="password" name="j_password"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Login"></td>
        </tr>
    </table>
</form>
