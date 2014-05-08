<%--
    Document   : content page for user dashboard
    Created on : 25 apr 2014, 18:31:22
    Author     : Katya Atamanchuk <nira@niralittle.name>
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<script src="assets/user-content.js"></script>

<% if (request.getParameter("user") != null) {
    out.println("<h2>Viewing info of " + request.getParameter("user") +
            "</h2>");
    }
%>
<!-- Paging -->
<%
   int currentSIPage = 1;
   int currentSOPage = 1;

   if (request.getParameter("siPage") != null) {
       currentSIPage = Integer.parseInt((String) request.getParameter("siPage"));
   }
   if (request.getParameter("soPage") != null) {
       currentSOPage = Integer.parseInt((String) request.getParameter("soPage"));
   }
%>

<!-- End of Paging -->
<!-- Processing orders block -->

<%  List<Map<String, String>> activeInstances =
        (List<Map<String, String>>) request.getAttribute("activeInstances");
    List<Map<String, String>> processingOrders =
            (List<Map<String, String>>) request.getAttribute("processingOrders");

    if (activeInstances != null && !activeInstances.isEmpty()) { %>
<div class="paging" style="width: 100px; float: left;">
<%
   String currentSI = "<strong>" + currentSIPage + "</strong>\t|\t";
   out.println("Page number: <br>");
   if (currentSIPage != 1) {
       out.println("<a href='CustomerUser?siPage=" + (currentSIPage - 1) +
           "&soPage=" + currentSOPage + "'>" + (currentSIPage - 1) + "</a>\t|\t");
   }
   out.println(currentSI);
   out.println("<a href='CustomerUser?siPage=" + (currentSIPage + 1) +
           "&soPage=" + currentSOPage + "'>" + (currentSIPage + 1) + "</a>");
%>
</div>
    <h2>Currently active connections:</h2>
        <table class='activeInstances' border='1'>
            <thead>
               <tr>
                    <td>Location</td>
                    <td>Type of Service</td>
                    <td>Since</td>
                    <td>Price</td>
                <% if (request.isUserInRole("CUSTOMER_USER")) { %>
                    <td>Options</td>
                <% } %>
                </tr>
            </thead>
            <tbody>
    <% for (Map<String, String> m: activeInstances) { %>
            <tr>
                <td><%= m.get("serviceLocation") %></td>
                <td><%= m.get("serviceDescription") %></td>
                <td><%= m.get("orderDate") %></td>
                <td><%= m.get("price") %></td>
            <% if (request.isUserInRole("CUSTOMER_USER")) { %>
                <td>  
                    <form action="CustomerUser" method="POST">
                        <input type="hidden" name="serviceInstanceID"
                               value="<%= m.get("instanceID") %>"/> 
                        <input type="submit" name="action" value="Disconnect" />
                    </form>
                </td>
            <% } %>
            </tr>
    <%  } /*end of 'for' statement*/ %>
            </tbody>
        </table>
 <%  } /*end of 'if' statement */%>

<!-- End of active instances block -->
<!-- Processing orders block -->

<%  if (processingOrders != null && !processingOrders.isEmpty()) { %>

<div class="paging" style="width: 100px; float: left;">
<% 
   String currentSO = "<strong>" + currentSOPage + "</strong>\t|\t";
   out.println("Page number: <br>");
   if (currentSOPage != 1) {
       out.println("<a href='CustomerUser?siPage=" + currentSIPage +
           "&soPage=" + (currentSOPage - 1) + "'>" +
            (currentSOPage - 1) + "</a>\t|\t");
   }
   out.println(currentSO);
   out.println("<a href='CustomerUser?siPage=" + currentSIPage +
           "&soPage=" + (currentSOPage + 1) + "'>" +
            (currentSOPage + 1) + "</a>");
%>
</div>
        <h2>Orders being processed:</h2>
        <table class='processingOrders' border="1">
            <thead>
                <tr>
                    <td>Scenario</td>
                    <td>Location</td>
                    <td>Type of Service</td>
                    <td>Order Date</td>
                </tr>
            </thead>
            <tbody>
     <% for (Map<String, String> m: processingOrders) { %>
            <tr>
                <td><%= m.get("scenario") %></td>
                <td><%= m.get("serviceLocation") %></td>
                <td><%= m.get("serviceDescription") %></td>
                <td><%= m.get("orderDate") %></td> 
            </tr>
    <%  } %>
            </tbody>
        </table>
<!-- End of rocessing orders block -->
 <%  } %>