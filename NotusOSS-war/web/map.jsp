



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
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&language=en"></script>

        <script>
            var map;
            var marker;
            var geocoder;
            var objSel; //HTML <select> object for multiple addresses

            //Map initialization
            function initialize() {
                var haightAshbury = new google.maps.LatLng(37.7699298, 0.4469157);
                geocoder = new google.maps.Geocoder();
                var mapOptions = {
                    zoom: 3,
                    center: haightAshbury,
                    mapTypeId: google.maps.MapTypeId.HYBRID
                };
                map = new google.maps.Map(document.getElementById('map-canvas'),mapOptions);
                marker = new google.maps.Marker({map: map});;
                google.maps.event.addListener(map, 'click', function(event) {
                    addMarker(event.latLng);
                });
            }

            // Add a marker to the map
            function addMarker(location) {
                marker.setPosition(location);
                codeLatLng(location);
            }

            //geocode from coordinates to address
            function codeLatLng(input) {
                var latlng = getLatLng(input);
                geocoder.geocode({'latLng': latlng}, function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        if (results[1]) {
                            document.getElementById('address').value = results[0].formatted_address;
                        } else {
                            alert('No results found');
                        }
                    } else {
                        alert('Wrong place');
                    }
                });
            }

            //geocode from address to coordinates
            function codeAddress() {
                var address = document.getElementById('address').value;
                var latlng;
                geocoder.geocode( { 'address': address}, function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {

                        if(results.length>1){
                            objSel = document.getElementById("addressSelect");
                            objSel.style.display = "block";
                            for(i=0;i<results.length;i++){
                                 objSel.options[i] = new Option(results[i].formatted_address, results[i].geometry.location);
                            }
                        latlng = getLatLng(objSel.options[0].value);
                        map.setCenter(latlng);
                        marker.setPosition(latlng);
                        }else{
                            latlng = getLatLng(results[0].geometry.location);
                            marker.setPosition(latlng);
                            map.setCenter(latlng);
                        }
                        map.setZoom(8);
                    } else {
                        alert('Geocode was not successful for the following reason: ' + status);
                    }
                });
            }

            //Set marker on select option. Changes the value of input address
            function selectFunction(){
                var select = document.getElementById("addressSelect");
                var position =  getLatLng(select.value);
                marker.setPosition(position);
                map.setCenter(position);
                document.getElementById("address").value = select.options[select.selectedIndex].text;
            }

            function getLatLng(loc){
                var position =  String(loc).slice(1, -1);
                var latlngStr = position.split(',', 2);
                var lat = parseFloat(latlngStr[0]);
                var lng = parseFloat(latlngStr[1]);
                var latlng = new google.maps.LatLng(lat, lng);
                return latlng;
            }

            google.maps.event.addDomListener(window, 'load', initialize);

        </script>
    </head>
    <body>
        <input type="text" id="address" value="" style="width: 500px;"/>
        <select id="addressSelect" name="mySelect" onchange="selectFunction()" style="display:none;"></select>
        <input type="button" value="Geocode" onclick="codeAddress()">
        <div id="map-canvas"></div>
        <p>Click on the map to add markers.</p>

    </body>
</html>
