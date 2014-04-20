var map;
var marker;
var geocoder;
var objSel; //HTML <select> object for multiple addresses
var destination = [];
var minPosition = 0; // The nearest provider!!!!
var req; //request to servlet

//Map initialization: map, marker and clock listener
function initialize() {
    var haightAshbury = new google.maps.LatLng(37.7699298, 0.4469157);
    geocoder = new google.maps.Geocoder();
    var mapOptions = {
        zoom: 3,
        center: haightAshbury,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById('map-canvas'),mapOptions);
    marker = new google.maps.Marker({
        map: map
    });
    google.maps.event.addListener(map, 'click', function(event) {
        addMarker(event.latLng);
        window.setTimeout(getMinDistance(),1000);
    });
}

// Add a marker to the map if ZOOM is more than 15
function addMarker(location) {
    if (marker.getMap()==null){
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
                    showZoomMessage();
                }
            } else {
                alert('No results found');
            }
        } else {
            alert('Wrong place. Please choose another one');
        }
    });
}

//geocode from address to coordinates
function codeAddress() {
    if(document.getElementById('address').value==""){
        alert('Please, input the address');
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
                alert('Wrong address. Please input another one');
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
    clean();
    marker.setMap(null);
    document.getElementById("address").value = "";
//    objSel.style.display = "none";
}

function showZoomMessage(){
    document.getElementById("spoiler_body").style.display = "block";
    window.setTimeout(function(){
        document.getElementById("spoiler_body").style.display = "none";
    },5000);
}
function showFarMessage(){
    document.getElementById("far").style.display = "block";
    window.setTimeout(function(){
        document.getElementById("far").style.display = "none";
    },5000);
}
//makes request and implements the ajax
function getMinDistance(){
    addLoad();
    var url = "GetLocationsServlet";
    req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = call;
    req.send(null);
}
function addLoad(){
    document.getElementById("loader").style.display = "block";
}
function removeLoad(){
    document.getElementById("loader").style.display = "none";
}
//initializes request
function initRequest() {
    if (window.ActiveXObject) {
        return new ActiveXObject("Microsoft.XMLHTTP");
    } else{
        return new XMLHttpRequest();
    }
}
//callback function
function call() {
    clean();
    if (req.readyState == 4) {
        if (req.status == 200) {
            parseMessage(req.responseXML);
        }
    }
}
function clean() {
    clear();
    var id = document.getElementById("providerLocation");
    id.removeAttribute("name");
}
var dis;
var flag = false;
//parses the responseXML and get neccessary data
function parseMessage(responseXML) {
    if (responseXML == null) {
       clean();
    } else {
        var locations = responseXML.getElementsByTagName("providerLocation");
        for(var k=0; k<locations.length;k++){
            destination[k] = locations[k].getElementsByTagName("location")[0].firstChild.nodeValue;
        }
        dis = 10000000000;
        calcMinDistance();
        
        window.setTimeout(function(){
            calcMinDistance();
            if(parseFloat(dis) < 50000){
                var pl = document.getElementById("providerLocation");
                var minID = getID(locations);
                pl.setAttribute("name", minID);
            }else{
                showFarMessage();
            }
            removeLoad();
        },10000);
       
    }
}
function getID(locations){
     for(var k=0; k<locations.length;k++){
        if(minPosition == locations[k].getElementsByTagName("location")[0].firstChild.nodeValue){
           return locations[k].getElementsByTagName("id")[0].firstChild.nodeValue;
        }
     }
}
/*THIS function calculate distance*/
function calcMinDistance(){
    var k;
    for(k=0; k<destination.length;k++){
        geocoder.geocode( {'address': destination[k]}, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                minLngLat = getLatLng(results[0].geometry.location);
                var distance = google.maps.geometry.spherical.computeDistanceBetween(
                    getLatLng(marker.getPosition()),minLngLat).toFixed(2);
                if(parseFloat(distance)<parseFloat(dis)){
                    dis = distance;
                    minPosition = results[0].formatted_address;
                    flag = true;
                }
            } else {
                alert('Wrong address. Please input another one');
            }
        });
    }
}

function geocode(address){
    geocoder.geocode( {
        'address': address
    }, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            this.minLngLat = getLatLng(results[0].geometry.location);
            alert(minLngLat);
        } else {
            alert('Wrong address. Please input another one');
        }
    });
}
google.maps.event.addDomListener(window, 'load', initialize);

