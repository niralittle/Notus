<%-- 
    Document   : groupMailSend-content
    Created on : May 11, 2014, 12:18:35 PM
    Author     : Roman Martynuyk
--%>

<script>
    window.onload = function(){
        document.getElementById("text").value = '';
    };
    function test(form) {
        if (form.text.value.length==0){
           alert("Input Message");
           return false;
        }
        if (form.subject.value.length==0){
           alert("Input Subject");
           return false;
        }
        
    }
</script>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="nc.notus.states.UserRole"%>
<%

%>
<form action="GroupMailSend" method="post" action="GroupMailSend" >
    <div class="form-group" style="width: 250px">
        <label for="select">Group</label>
        <select class="form-control" id="select" name="selectRole"><%
            for (UserRole roles : UserRole.values()) {
                if (!UserRole.ADMINISTRATOR.equals(roles)) {%>
            <option><%=roles%></option><%
            }
        }%>
        </select>
    </div>
    <div class="form-group" style="width: 250px">
        <label for="subject">Subject</label>
        <input type="text" name="subject" class="form-control" id="subject">
    </div>        
    <div class="form-group" style="width: 400px">
        <label for="text">Message</label>
        <textarea name="text" rows="4" cols="3" class="form-control" id="text" style="width: 450px;">
        </textarea>
    </div>
    <button type="submit" class="btn btn-info" onClick="return test(this.form)">Send</button>
</form>


