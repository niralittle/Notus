<%-- 
    Document   : report view/download page content
    Created on : 18.04.2014, 16:00:48
    Author     : Andrey Ilin
--%>

<%=request.getSession().getAttribute("table")%>
<form method="post" action="reportPaging" <%=request.getAttribute("paging")%>>
    <input type="submit" name="prevpage" value="<< Previous" <%=request.getAttribute("prevpage")%>/>
    <input type="submit" name="nextpage" value="Next >>" <%=request.getAttribute("nextpage")%>/>
    <input type="hidden" name="objectId"
           value="<%=request.getSession().getAttribute("objectId")%>"/>
</form><br>

<form method="post" action="downloadReport">
    <input type="submit" value="Download as  XLS"/>
    <input type="hidden" name="objectId"
           value="<%=request.getSession().getAttribute("objectId")%>"/>
    <input type="hidden" name="type" value="xls"/>
</form>
<form method="post" action="downloadReport">
    <input type="submit" value="Download as CSV"/>
    <input type="hidden" name="objectId"
           value="<%=request.getSession().getAttribute("objectId")%>"/>
    <input type="hidden" name="type" value="csv"/>
</form>
