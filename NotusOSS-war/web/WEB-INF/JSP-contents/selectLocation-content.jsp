<%--
    Document   : map
    Created on : Apr 11, 2014, 6:33:40 PM
    Author     : Roman Martynyuk
--%>


<script src="https://maps.googleapis.com/maps/api/js?libraries=geometry&sensor=false&language=en"></script>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="assets/google_maps_api.js"></script>

<div id ="map-container">
    <div id="map-canvas"></div>


    <div id ="rigtblock">
        <input type="text" id="address" name="address" value="" style="width: 500px;"/>
        <select id="addressSelect" name="mySelect" onchange="selectFunction()" style="display:none;"></select>
        <input type="button" value="Geocode" onclick="codeAddress()">
        <input type="button" value="Remove Pointer" onclick="removePointer()">
        <div id="spoiler_body" style="display:none;">
            You should zoom more
        </div>
<%--
        <form action="GetLocationsServlet">
            <input type="submit" value="getLocations" />
        </form>
--%>
        <input type="button" value="Calc min distance" onclick="getMinDistance()">
         <div id="outputDiv"></div>
    </div>
</div>

