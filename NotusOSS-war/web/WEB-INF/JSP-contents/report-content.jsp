<%-- 
    Document   : report view/download page content
    Created on : 18.04.2014, 16:00:48
    Author     : Andrey Ilin
--%>

<%=request.getAttribute("table")%>
<form method="post" action="downloadReport">
    <input type="submit" value="Download as XLS"/>
    <input type="hidden" name="object"
           value="<%=request.getAttribute("object")%>"/>
    <input type="hidden" name="type" value="xls"/>
</form>
<form method="post" action="downloadReport">
    <input type="submit" value="Download as CSV"/>
    <input type="hidden" name="object"
           value="<%=request.getAttribute("object")%>"/>
    <input type="hidden" name="type" value="csv"/>
</form>

