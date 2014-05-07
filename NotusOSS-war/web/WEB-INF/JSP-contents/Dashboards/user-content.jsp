<%--
    Document   : content page for user dashboard
    Created on : 25 apr 2014, 18:31:22
    Author     : Katya Atamanchuk <nira@niralittle.name>
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>



<!-- Processing orders block -->

<%  List<Map<String, String>> activeInstances =
        (List<Map<String, String>>) request.getAttribute("activeInstances");
    List<Map<String, String>> processingOrders =
            (List<Map<String, String>>) request.getAttribute("processingOrders");

    if (activeInstances != null && activeInstances.size() > 0) { %>
        <table class='activeInstances' border='1'>
            <thead>
        `       <tr>
                    <td>Location</td>
                    <td>Type of Service</td>
                    <td>Since</td>
                    <td>Price</td>
                    <td>Options</td>
                </tr>
            </thead>
            <tbody>
    <% for (Map<String, String> m: activeInstances) { %>
            <tr>
                <td><%= m.get("serviceLocation") %></td>
                <td><%= m.get("serviceDescription") %></td>
                <td><%= m.get("orderDate") %></td>
                <td><%= m.get("price") %></td>
                <td>  
                    <form action="DisconnectOrderProceed" method="POST">
                        <input type="hidden" name="serviceInstanceID"
                               value="<%= m.get("instanceID") %>"/> 
                        <input type="submit" name="action" value="Disconnect" />
                    </form>
                </td>
            </tr>
    <%  } /*end of 'for' statement*/ %>
            </tbody>
        </table>
 <%  } /*end of 'if' statement */%>

<!-- End of active instances block -->
<!-- Processing orders block -->

<%  if (processingOrders != null && processingOrders.size() > 0) { %>
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