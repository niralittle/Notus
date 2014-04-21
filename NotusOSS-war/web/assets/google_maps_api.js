var map;
var marker;
var geocoder;
var objSel; //HTML <select> object for multiple addresses
var destination = [];
var minPosition = 0; // The nearest provider!!!!

//Map initialization: map, marker and clock listener
function initialize() {
    var startPosition = new google.maps.LatLng(50.464580, 30.523078);
    geocoder = new google.maps.Geocoder();
    var mapOptions = {
        zoom: 10,
        center: startPosition,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById('map-canvas'),mapOptions);
    marker = new google.maps.Marker({
        map: map
    });
    getProviderLocations();
    window.setTimeout(function(){
        getProviderMarkers();
    },500);
    google.maps.event.addListener(map, 'click', function(event) {
        addMarker(event.latLng);
    });
}
//makes request and implements the ajax
function getProviderLocations(){
    var url = "GetLocationsServlet";
    req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = call;
    req.send(null);
}

//callback function
function call() {
    clear();
    if (req.readyState == 4) {
        if (req.status == 200) {
            parseMessage(req.responseXML);
        }
    }
}
//parses the responseXML and get neccessary data
function parseMessage(responseXML) {
    if (responseXML == null) {
       clear();
    } else {
        locations = responseXML.getElementsByTagName("providerLocation");
        for(var k=0; k<locations.length;k++){
            destination[k] = locations[k].getElementsByTagName("location")[0].firstChild.nodeValue;
        }
    }
}
// Add a marker to the map if ZOOM is more than 15
function addMarker(location) {
    if (marker == null){
        marker = new google.maps.Marker({
            map: map
        });
    }
    codeLatLng(location);
}

//geocode from coordinates to address
function codeLatLng(input) {
    var latlng = getLatLng(input);
    geocoder.geocode({
        'latLng': latlng
    }, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            if (results[1]) {
                if(map.getZoom()>15){
                    marker.setPosition(input);
                    document.getElementById('address').value = results[0].formatted_address;
                }else{
                    showErrorMessage("You should zoom more");
                }
            } else {
                showErrorMessage('No results found');
            }
        } else {
            showErrorMessage('Wrong place. Please choose another one');
        }
    });
}

//geocode from address to coordinates
function codeAddress() {
    if(document.getElementById('address').value==""){
        showErrorMessage('Please, input the address');
    }else{
        var address = document.getElementById('address').value;
        objSel = document.getElementById("addressSelect");
        var latlng;
        geocoder.geocode( {
            'address': address
        }, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                if(results.length>1){
                    objSel.style.display = "block";
                    for(i=0;i<results.length;i++){
                        objSel.options[i] = new Option(results[i].formatted_address, results[i].geometry.location);
                    }
                }
                

                latlng = getLatLng(results[0].geometry.location);
                map.setCenter(latlng);
                document.getElementById('address').value = results[0].formatted_address;
                map.setZoom(16);
            } else {
                showErrorMessage('Wrong address. Please input another one');
            }
        });
    }
}

//Set marker on select option. Changes the value of input address
function selectFunction(){
    var select = document.getElementById("addressSelect");
    var position =  getLatLng(select.value);
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
    clear();
    marker.setMap(null);
    marker = null;
    document.getElementById("address").value = "";
//    objSel.style.display = "none";
}

/*THIS function calculate distance*/
function calcMinDistance(){
    var k;
    var minPosition;
    var dis = 10000000000;
    for(k=0; k<destination.length;k++){
        geocoder.geocode({'address': destination[k]}, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                minLngLat = getLatLng(results[0].geometry.location);
                var distance = google.maps.geometry.spherical.computeDistanceBetween(
                    getLatLng(marker.getPosition()),minLngLat).toFixed(2);
                if(parseFloat(distance)<parseFloat(dis)){
                    dis = distance;
                    minPosition = results[0].formatted_address;
                }
            }else{
                showErrorMessage('Wrong address. Please input another one');
            }
        });
    }
    window.setTimeout(function(){
        if(parseFloat(dis) < 50000){
            minPos = minPosition;
        }else{
            showErrorMessage("You are too far");
        }
    },500);
}

function geocode(address){
    geocoder.geocode( {
        'address': address
    }, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            this.minLngLat = getLatLng(results[0].geometry.location);
            showErrorMessage(minLngLat);
        } else {
            showErrorMessage('Wrong address. Please input another one');
        }
    });
}

function getProviderMarkers(){
    for(var i=0;i<destination.length;i++){
        geocoder.geocode( {'address': destination[i]}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            var marker = new google.maps.Marker({
                position: results[0].geometry.location,
                map: map
            });
            marker.setMap(map);
        } else {
            showErrorMessage('Wrong address. Please input another one');
        }
    });
    }
}

google.maps.event.addDomListener(window, 'load', initialize);

