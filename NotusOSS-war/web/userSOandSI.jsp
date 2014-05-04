<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User SO and SI</title>
</head>
<body>
	<%  List<Map<String, String>> activeInstances =
        (List<Map<String, String>>) request.getAttribute("activeInstances");
    List<Map<String, String>> processingOrders =
            (List<Map<String, String>>) request.getAttribute("processingOrders");

    if (activeInstances != null && activeInstances.size() > 0) { %>
        <h2>Active services:</h2>
        <table class='activeInstances' border='1'>
            <thead>
               <tr>
                    <td>Location</td>
                    <td>Type of Service</td>
                    <td>Since</td>
                    <td>Price</td>
                </tr>
            </thead>
            <tbody>
    <% for (Map<String, String> m: activeInstances) { %>
            <tr>
                <td><%= m.get("serviceLocation") %></td>
                <td><%= m.get("serviceDescription") %></td>
                <td><%= m.get("orderDate") %></td>
                <td><%= m.get("price") %></td>
            </tr>
    <%  } /*end of 'for' statement*/ %>
            </tbody>
        </table>
 <%  } else { out.write("Any SI found!"); } /*end of 'if' statement */%>

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
 <%  } else { out.write("Any SO found!"); } %>
</body>
</html>
