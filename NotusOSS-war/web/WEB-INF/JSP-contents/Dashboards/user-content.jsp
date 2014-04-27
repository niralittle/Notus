<%--
    Document   : user-content
    Created on : 25 apr 2014, 18:31:22
    Author     : Katya Atamanchuk <nira@niralittle.name>
--%>

    <%@page import="nc.notus.entity.ServiceOrder"%>
    <%@page import="nc.notus.entity.ProviderLocation"%>
    <%@page import="nc.notus.entity.ServiceCatalog"%>
    <%@page import="nc.notus.dbmanager.DBManager"%>
    <%@page import="nc.notus.entity.ServiceCatalog"%>
    <%@page import="nc.notus.entity.ServiceType"%>
    <%@page import="nc.notus.entity.Scenario"%>
    <%@page import="nc.notus.dao.impl.ServiceCatalogDAOImpl"%>
    <%@page import="nc.notus.dao.impl.ServiceTypeDAOImpl"%>
    <%@page import="nc.notus.dao.impl.ScenarioDAOImpl"%>
    <%@page import="nc.notus.dao.ServiceCatalogDAO"%>
    <%@page import="nc.notus.dao.ServiceTypeDAO"%>
    <%@page import="nc.notus.dao.ScenarioDAO"%>
    <%@page import="java.util.List"%>
<%
   List<ServiceOrder> completeOrders = (List<ServiceOrder>)
           request.getAttribute("completedOrders");
   List<ServiceOrder> enteringOrders = (List<ServiceOrder>)
           request.getAttribute("enteringOrders"); 
   List<ServiceOrder> processingOrders = (List<ServiceOrder>)
           request.getAttribute("processingOrders");
   DBManager dbManager = new DBManager();
   ServiceCatalogDAO catalogDAO = new ServiceCatalogDAOImpl(dbManager);
   ServiceTypeDAO typeDAO = new ServiceTypeDAOImpl(dbManager);
   ScenarioDAO scenarioDAO = new ScenarioDAOImpl(dbManager);
try {

   if (completeOrders.size() > 0) {
        int catalogID;
        ServiceCatalog sc;
        ServiceType st;
%>
<table class="completeOrders">
     <thead>
        <tr>
            <td>Location</td>
            <td>Type of Service</td>
            <td>Since</td>
            <td>Price</td>
            <td>Options</td>
        </tr>
     </thead>

        <% for (ServiceOrder o: completeOrders) {
            catalogID = o.getServiceCatalogID();
            sc = catalogDAO.find(catalogID);
            st = typeDAO.find(sc.getServiceTypeID()); %>
    <tr>
        <td><% out.print(o.getServiceLocation()); %></td>
        <td><% out.print(st.getService()); %></td>
        <td><% out.print(o.getServiceOrderDate()); %></td>
        <td><% out.print(sc.getPrice()); %></td>
        <td>
            <button>Modify</button>
            <button>Disconnect</button>
        </td>
    </tr>
        <% } /*closing for() statement*/%>
</table>
    <% } /*closing if() statement*/%>

<%    if (completeOrders.size() > 0) {
        int catalogID;
        ServiceCatalog sc;
        ServiceType st;
        Scenario s;
%>
<br>
<h2>Your new orders:</h2>
<table class="enteringOrders">
    <thead>
        <tr>
            <td>Scenario</td>
            <td>Location</td>
            <td>Type of Service</td>
            <td>Order Date</td>
        </tr>
     </thead>
         <% for (ServiceOrder o: enteringOrders) {
                catalogID = o.getServiceCatalogID();
                sc = catalogDAO.find(catalogID);
                st = (ServiceType)typeDAO.find(sc.getServiceTypeID());
                s = scenarioDAO.find(o.getScenarioID());
         %>
    <tr>
        <td><% out.print(s.getScenario()); %></td>
        <td><% out.print(o.getServiceLocation()); %></td>
        <td><% out.print(st.getService()); %></td>
        <td><% out.print(o.getServiceOrderDate()); %></td>
    </tr>
        <% } /*closing for() statement*/%>
</table>
    <% } /*closing if() statement*/%>

<%    if (processingOrders.size() > 0) {
        int catalogID;
        ServiceCatalog sc;
        ServiceType st;
        Scenario s;
%>

<h2>Orders being processed:</h2>
<table class="processingOrders">
     <thead>
        <tr>
            <td>Scenario</td>
            <td>Location</td>
            <td>Type of Service</td>
            <td>Order Date</td>
        </tr>
     </thead>
         <% for (ServiceOrder o: processingOrders) {
            catalogID = o.getServiceCatalogID();
            sc = catalogDAO.find(catalogID);
            st = typeDAO.find(sc.getServiceTypeID());
            s = scenarioDAO.find(o.getScenarioID());%>
        <tr>
            <td><% out.print(s.getScenario()); %></td>
            <td><% out.print(o.getServiceLocation()); %></td>
            <td><% out.print(st.getService()); %></td>
            <td><% out.print(o.getServiceOrderDate()); %></td>
        </tr>
        <% } /*closing for() statement*/%>
</table>
    <% } /*closing if() statement*/%>
 
<% } finally {
            dbManager.close();
        } %>
