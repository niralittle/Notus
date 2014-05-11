<%-- 
    Document   : groupMailSend-content
    Created on : May 11, 2014, 12:18:35 PM
    Author     : Roman Martynuyk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="nc.notus.states.UserRole"%>
<%

%>
<form action="GroupMailSend" method="post" action="GroupMailSend">
    <table border="1" id="table">
        <tbody>
            <tr>
                <td>Group</td>
                <td><select name="selectRole">
                        <%for (UserRole roles : UserRole.values()) {%>
                        <%if (!UserRole.ADMINISTRATOR.equals(roles)) {%>
                        <option><%=roles%></option>
                        <%}%>
                        <%}%>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Subject</td>
                <td><input type="text" name="subject" value="" /></td>
            </tr>
            <tr>
                <td>Message</td>
                <td><input type="text" name="text" value="" /></td>
            </tr>
            <tr>
                <td colspan="2">

                    <input type="submit" value="Send" />

                </td>
            </tr>
        </tbody>
    </table>
</form>


