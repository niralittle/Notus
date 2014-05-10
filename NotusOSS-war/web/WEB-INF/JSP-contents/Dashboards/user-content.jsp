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
   String userIDparam =  request.getParameter("userID") != null
           ? "&userID=" + request.getParameter("userID") : "";

   int numbOfSIPages = (Integer) request.getAttribute("numbOfSIPages");
   int numbOfSOPages = (Integer) request.getAttribute("numbOfSOPages");
%>

<!-- End of Paging -->
<!-- Processing orders block -->

<%  List<Map<String, String>> activeInstances =
        (List<Map<String, String>>) request.getAttribute("activeInstances");
    List<Map<String, String>> processingOrders =
            (List<Map<String, String>>) request.getAttribute("processingOrders");

    if (activeInstances != null && !activeInstances.isEmpty()) { %>
    <h2>Currently active connections:</h2>

        <table class='activeInstances table table-striped table-hover'>
            <thead>
               <tr>
                    <th>Location</th>
                    <th>Type of Service</th>
                    <th>Since</th>
                    <th>Price</th>
                <% boolean showOptions = request.isUserInRole("CUSTOMER_USER");
                if (showOptions) { %>
                    <th>Options</th>
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
            <% if (showOptions) { %>
                <td>  
                    <form action="CustomerUser" method="POST">
                        <input type="hidden" name="serviceInstanceID"
                               value="<%= m.get("instanceID") %>"/> 
                        <input class="btn btn-danger" type="submit" name="action" value="Disconnect" />
                    </form>
                </td>
            <% } %>
            </tr>
    <%  } /*end of 'for' statement*/ %>
            </tbody>
        </table>
<ul class="pagination">
<%  if (numbOfSIPages > 1) {
        String buttonTemplate = "<li><a href='CustomerUser?siPage="
                    + "%d&soPage="+ currentSOPage + userIDparam +"'>%d</a></li>";

        for (int i = 1; i < currentSIPage; i++) {
            out.println(String.format(buttonTemplate, i, i));
        }
%><li class ="active"><a href="#"><span><%=currentSIPage%></span></a></li> <%
        for (int i = currentSIPage + 1; i <= numbOfSIPages; i++) {
            out.println(String.format(buttonTemplate, i, i));
        }
    }
%>
</ul>
 <%  } /*end of 'if' statement */%>

<!-- End of active instances block -->
<!-- Processing orders block -->

<%  if (processingOrders != null && !processingOrders.isEmpty()) { %>
<hr style="height: 5px;
  background-image: -webkit-linear-gradient(left, rgba(0,0,0,0), rgba(0,0,0,.1), rgba(0,0,0,0));
  background-image:    -moz-linear-gradient(left, rgba(0,0,0,0), rgba(0,0,0,.1), rgba(0,0,0,0));
  background-image:     -ms-linear-gradient(left, rgba(0,0,0,0), rgba(0,0,0,.1), rgba(0,0,0,0));
  background-image:      -o-linear-gradient(left, rgba(0,0,0,0), rgba(0,0,0,.1), rgba(0,0,0,0));">

<h2>Orders being processed:</h2>
        <table class='processingOrders table table-striped table-hover'>
            <thead>
                <tr>
                    <th>Status</th>
                    <th>Location</th>
                    <th>Type of Service</th>
                    <th>Order Date</th>
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
<ul class="pagination">
<%  if (numbOfSOPages > 1) {
        String buttonTemplate = "<li><a href='CustomerUser?siPage="
                    + currentSIPage + userIDparam + "&soPage=%d'>%d</a></li>";

        for (int i = 1; i < currentSOPage; i++) {
            out.println(String.format(buttonTemplate, i, i));
        }
%><li class ="active"><a href="#"><span><%=currentSOPage%></span></a></li> <%
        for (int i = currentSOPage + 1; i <= numbOfSOPages; i++) {
            out.println(String.format(buttonTemplate, i, i));
        }
    }
%>
</ul>
 <%  } %>