<%--
    Document   : map
    Created on : Apr 11, 2014, 6:33:40 PM
    Author     : Roman Martynyuk
--%>


<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
<script src="assets/google_maps_api.js"></script>

<input type="text" id="address" value="" style="width: 500px;"/>
<input type="button" value="Geocode" onclick="codeAddress()">
<div id="map-canvas"></div>
<p>Click on the map to add markers.</p>
