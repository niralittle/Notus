<%-- 
    Document   : serviceCatalog-content
    Created on : 25.04.2014, 14:00:54
    Author     : Katya Atamanchuk
--%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Collection"%>
<% Map<String, Map<String, Integer>> map = (Map<String,
        Map<String, Integer>>) request.getAttribute("providerLocations");
   Collection<String> types = (Collection<String>)
           request.getAttribute("serviceTypes");%>
<table class="table table-striped table-hover">
    <tbody>
    <tr>
        <th><center><h4>Address</h4></center></th>
        <%for (String service : types) {%>
        <th><center><h4><%=service%></h4></center></th>
        <%}%>
    </tr>
<%for (String location : map.keySet()){%>
    <tr>
        <td><h4 style="margin-left: 9px;"><strong><%=location%></strong></h4></td>
        <%Map<String, Integer> prices = map.get(location);
        for (String service : types) {
             Integer price = prices.get(service);%>
        <td><center><h3><%=price == null ? "&mdash;" : "$ " + price%></h3></center></td>
        <%}%>
    </tr>
<%}%>
    </tbody>
</table>

    <a class="btn btn-info btn-lg btn-block" href="selectLocation.jsp">Make an order</a>

