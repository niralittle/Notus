<%--
    Document   : map
    Created on : Apr 11, 2014, 6:33:40 PM
    Author     : Roman Martynyuk
--%>


<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&language=en"></script>
<script src="assets/google_maps_api.js"></script>

<input type="text" id="address" value="" style="width: 500px;"/>
<select id="addressSelect" name="mySelect" onchange="selectFunction()" style="display:none;"></select>
<input type="button" value="Geocode" onclick="codeAddress()">
<input type="button" value="Remove Pointer" onclick="removePointer()">
<div id="map-canvas"></div>
