<p>Hello admin, <%= request.getUserPrincipal()%> </p>
    <form method="get" action="ViewReport">
        <input type="submit" value="View report"/>
    </form>

<a href="selectLocation.jsp">Map</a>

<form method="get" action="logout">
    <input type="submit" value="Logout"/>
</form>

<p>Email</p>
<form name="Email" action="MailServlet">
    <table border="0">
        <tbody>
            <tr>
                <td>Adress</td>
                <td><input type="text" name="address" value="" /></td>
            </tr>
            <tr>
                <td>Subject</td>
                <td><input type="text" name="subject" value="" /></td>
            </tr>
            <tr>
                <td>Text</td>
                <td><input type="text" name="text" value="" style="height:200px;"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Send" name="Send Email" /></td>
            </tr>
        </tbody>
    </table>
</form>

