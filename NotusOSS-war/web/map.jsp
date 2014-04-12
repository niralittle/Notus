<%-- 
    Document   : map
    Created on : Apr 11, 2014, 6:33:40 PM
    Author     : Roman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<!DOCTYPE html>
<html>
    <head>
        <title>Google Map</title>
        <style>
            html, body, #map-canvas {
                height: 600px;
                width: 500px;
                margin: 0px;
                padding: 0px
            }
            #panel {
                position: absolute;
                top: 5px;
                left: 50%;
                margin-left: -180px;
                z-index: 5;
                background-color: #fff;
                padding: 5px;
                border: 1px solid #999;
            }
        </style>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
        <script>
            // In the following example, markers appear when the user clicks on the map.
            // The markers are stored in an array.
            // The user can then click an option to hide, show or delete the markers.
            var map;
            var markers = [];
            var marker;
            var locations;
            var geocoder;

            function initialize() {
                var haightAshbury = new google.maps.LatLng(37.7699298, -122.4469157);
                geocoder = new google.maps.Geocoder();
                var mapOptions = {
                    zoom: 12,
                    center: haightAshbury,
                    mapTypeId: google.maps.MapTypeId.TERRAIN
                };
                map = new google.maps.Map(document.getElementById('map-canvas'),
                mapOptions);
                marker = new google.maps.Marker({map: map});;
                google.maps.event.addListener(map, 'click', function(event) {
                    addMarker(event.latLng);
                });
            }

            // Add a marker to the map and push to the array.
            function addMarker(location) {

                marker.setPosition(location);
                locations = String(location).slice(1, -1);
                codeLatLng(locations);
            }

            function codeLatLng(input) {
                var latlngStr = input.split(',', 2);
                var lat = parseFloat(latlngStr[0]);
                var lng = parseFloat(latlngStr[1]);
                var latlng = new google.maps.LatLng(lat, lng);
                geocoder.geocode({'latLng': latlng}, function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        if (results[1]) {
                            document.getElementById('address').value = results[1].formatted_address;
                        } else {
                            alert('No results found');
                        }
                    } else {
                        alert('Geocoder failed due to: ' + status);
                    }
                });
            }

            function codeAddress() {
                var address = document.getElementById('address').value;
                geocoder.geocode( { 'address': address}, function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        map.setCenter(results[0].geometry.location);
                        marker.setPosition(results[0].geometry.location);
                    } else {
                        alert('Geocode was not successful for the following reason: ' + status);
                    }
                });
            }
            google.maps.event.addDomListener(window, 'load', initialize);

        </script>
    </head>
    <body>
        <input type="text" id="address" value="" style="width: 500px;"/>
        <input type="button" value="Geocode" onclick="codeAddress()">
        <div id="map-canvas"></div>
        <p>Click on the map to add markers.</p>
    </body>
</html>
