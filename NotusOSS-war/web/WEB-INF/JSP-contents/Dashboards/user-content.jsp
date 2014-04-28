<%--
    Document   : user-content
    Created on : 25 apr 2014, 18:31:22
    Author     : Katya Atamanchuk <nira@niralittle.name>
--%>
<%String html = (String) request.getAttribute("userDashboardHtml");
    if (html != null) out.print(html);
    else out.println("<h4>You don't have any orders at this moment. </h4>");
%>