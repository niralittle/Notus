<%--
    Document   : map
    Created on : Apr 11, 2014, 6:33:40 PM
    Author     : Roman Martynyuk, Alina Vorobiova
--%>


<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?libraries=geometry&sensor=false&language=en"></script>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript" src="assets/google_maps_api.js"></script>
<script type="text/javascript" src="assets/getServices.js"></script>

<h2>To make an order for a new connection, select location by placing a pin on
        the map or typing the desired address into the textbox and choose from
        the list of available services.</h2>
<div id ="map-container">
    <div id="map-canvas"></div>

    <div id ="rightblock">
        <h3></h3>
        <input type="text" id="address" name="address" value="" 
               style="width:489px;"/>
        <select id="addressSelect" name="mySelect" onchange="selectFunction()" 
                style="display:none; padding-left: 5px; "/>
        <input type="button" value="Show on the map" onclick="codeAddress()" style="">
        <input type="button" value="Remove Pointer" onclick="removePointer()">
        
         <%-- form for the table of services --%>
         <form id="servicesForm" name="getservices" action="ServisesServlet">
             <input id="see" type="button" value="Show available services for this location" onclick="getServices()"/>
             <img id="loader" src="assets/ajax-loader.gif" alt="Loading..." style="display:none;"/>
             <div id="errorPanel" style="padding-top:20px;"></div>
             <table style="padding-top:20px;">
                <thead><tr><th id="header"></th></tr></thead>
                <tbody id ="services"><tr><td></td></tr></tbody>
            </table>
        </form>
         <form id='postForm' method='post' action='registration.jsp'>
            <input id="serviceLocationID" name="serviceLocationID" type='hidden'>
            <input id="serviceCatalogID" name="serviceCatalogID" type='hidden'>
         </form>
    </div>
</div>

