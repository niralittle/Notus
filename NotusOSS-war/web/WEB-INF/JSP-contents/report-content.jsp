<%-- 
    Document   : report view/download page content
    Created on : 18.04.2014, 16:00:48
    Author     : Andrey Ilin
--%>

<%=request.getSession().getAttribute("table")%>
<form method="post" action="reportPaging" <%=request.getAttribute("paging")%>>
    <input type="submit" name="prevpage" value="<< Previous" <%=request.getAttribute("prevpage")%> class="btn btn-info"/>
    <input type="submit" name="nextpage" value="Next >>" <%=request.getAttribute("nextpage")%> class="btn btn-info"/>
    <input type="hidden" name="objectId"
           value="<%=request.getSession().getAttribute("objectId")%>"/>
</form><br>

<form method="post" action="downloadReport">
    <input type="submit" value="Download as  XLS" class="btn btn-success"/>
    <input type="hidden" name="objectId"
           value="<%=request.getSession().getAttribute("objectId")%>"/>
    <input type="hidden" name="type" value="xls"/>
</form>
<form method="post" action="downloadReport" style="margin-top: 5px">
    <input type="submit" value="Download as CSV" class="btn btn-primary"/>
    <input type="hidden" name="objectId"
           value="<%=request.getSession().getAttribute("objectId")%>"/>
    <input type="hidden" name="type" value="csv"/>
</form>
