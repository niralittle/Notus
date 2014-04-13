<%--
    Document   : map
    Created on : Apr 11, 2014, 6:33:40 PM
    Author     : Roman Martynyuk
--%>


<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&language=en"></script>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="assets/google_maps_api.js"></script>

<form name="serviceChoice" action="service" method="POST">
    <input type="text" id="address" name="address" value="" style="width: 500px;"/>

    <div id="spoiler_body" style="display:none;">
        You should zoom to maximum
    </div>

    <select id="addressSelect" name="mySelect" onchange="selectFunction()" style="display:none;"></select>
    <input type="button" value="Geocode" onclick="codeAddress()">
    <input type="button" value="Remove Pointer" onclick="removePointer()">
    <div id="map-canvas"></div>


    <input type="checkbox" name="serviceInternet" value="Internet" /> Internet
    <input type="checkbox" name="serviceIPTV" value="IP-TV" /> IP-TV
    <input type="checkbox" name="serviceVoIP" value="VoIP" /> VoIP
</form>
