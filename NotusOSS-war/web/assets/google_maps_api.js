var map;
var marker;
var geocoder;
var objSel; //HTML <select> object for multiple addresses

//Map initialization: map, marker and clock listener
function initialize() {
    var haightAshbury = new google.maps.LatLng(37.7699298, 0.4469157);
    geocoder = new google.maps.Geocoder();
    var mapOptions = {
        zoom: 3,
        center: haightAshbury,
        mapTypeId: google.maps.MapTypeId.HYBRID
    };
    map = new google.maps.Map(document.getElementById('map-canvas'),mapOptions);
    marker = new google.maps.Marker({
        map: map
    });
    google.maps.event.addListener(map, 'click', function(event) {
        addMarker(event.latLng);
    });
}

// Add a marker to the map if ZOOM is max (19)!!
function addMarker(location) {
    if (marker.getMap()==null){
        marker = new google.maps.Marker({
            map: map
        });
    }
    if(map.getZoom()==19){
        marker.setPosition(location);
    }
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
    geocoder.geocode( {'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            if(results.length>1){
                objSel = document.getElementById("addressSelect");
                objSel.style.display = "block";
                for(i=0;i<results.length;i++){
                    objSel.options[i] = new Option(results[i].formatted_address, results[i].geometry.location);
                }
            }
            latlng = getLatLng(results[0].geometry.location);
            marker.setPosition(latlng);
            map.setCenter(latlng);
            document.getElementById('address').value = results[0].formatted_address;
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

//Converts String to Google location Object
function getLatLng(loc){
    var position =  String(loc).slice(1, -1);
    var latlngStr = position.split(',', 2);
    var lat = parseFloat(latlngStr[0]);
    var lng = parseFloat(latlngStr[1]);
    var latlng = new google.maps.LatLng(lat, lng);
    return latlng;
}

//remove marker froma map
function removePointer(){
    marker.setMap(null);
    objSel.style.display = "none";
    document.getElementById("address").value = "";
}

google.maps.event.addDomListener(window, 'load', initialize);