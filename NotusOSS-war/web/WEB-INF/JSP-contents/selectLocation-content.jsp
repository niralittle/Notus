<%--
    Document   : map
    Created on : Apr 11, 2014, 6:33:40 PM
    Author     : Roman Martynyuk, Alina Vorobiova
--%>


<script src="https://maps.googleapis.com/maps/api/js?libraries=geometry&sensor=false&language=en"></script>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="assets/google_maps_api.js"></script>
<script src="assets/getServices.js"></script>

<div id ="map-container">
    <div id="map-canvas"></div>


    <div id ="rigtblock">
        <input type="text" id="address" name="address" value="" style="width: 500px;"/>
        <select id="addressSelect" name="mySelect" onchange="selectFunction()" style="display:none;"/>
        <input type="button" value="Show on the map" onclick="codeAddress()">
        <input type="button" value="Remove Pointer" onclick="removePointer()">
        
         <%-- form for the table of services --%>
         <form id="servicesForm" name="getservices" action="ServisesServlet">
             <input id="see" type="button" value="See available services for my location" onclick="getServices()"/>
             <img id="loader" src="assets/ajax-loader.gif" alt="Loading, Loading!" style="display:none;"/>
             <div id="errorPanel"></div>
            <table>
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

