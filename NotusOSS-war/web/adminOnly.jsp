<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>Insert title here</title>
    </head>
    <body>
        <p>Hello admin, <%= request.getUserPrincipal()%> </p>
        <form method="get" action="getreport">
            <input type="submit" value="Get Report"/>
        </form>

        <form method="get" action="logout">
            <input type="submit" value="Logout"/>
        </form>
        <p>EMail</p>
        <form name="Email" action="MailServlet">
            <table border="1">
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
                        <td></td>
                    </tr>
                </tbody>
            </table>

        </form>

    </body>
</html>
