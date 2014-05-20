/*   This js allow to use google maps, by using google maps API. */

var map; //Google Map
var marker;  //customer marker
var geocoder; //object, that allow to use geocode Google Geocode API
var objSel; //HTML <select> object for multiple addresses
var destination = []; //array of provider locations addresses
var minPosition; // The nearest provider!!!!
var delayTime = 500; //time to perform the functions request to Google


/**
 * This function initialize map and marker using specific parameters describe in it
 * Also fill array of provider locations address, and place them on the map
 */
function initialize() {

    //Initialize parameters
    var startPositionLat = 50.464580; //Kyiv
    var startPositionLng = 30.523078;
    var startZoom = 10;
    var startPosition = new google.maps.LatLng(startPositionLat, startPositionLng); 
    var mapOptions = {
        zoom: startZoom,
        center: startPosition,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };


    map = new google.maps.Map(document.getElementById('map-canvas'),mapOptions);
   
    marker = new google.maps.Marker({
        map: map
    });

    geocoder = new google.maps.Geocoder();


    getProviderLocations();
    window.setTimeout(function(){
        getProviderMarkers();
    },delayTime);
    
    google.maps.event.addListener(map, 'click', function(event) {
        addMarker(event.latLng);
    });
    
}


/*
 *makes request and implements the ajax
 */

function getProviderLocations(){
    var url = "GetLocationsServlet";
    req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = call;
    req.send(null);
}

/*
 *callback function
 */
function call() {
    clear();
    var requestFinishState = 4;
    var requestFinishStatus = 200;
    
    if (req.readyState == requestFinishState) {
        if (req.status == requestFinishStatus) {
            parseMessage(req.responseXML);
        }
    }
}


/*
 *parses the responseXML and get service location data
 */
function parseMessage(responseXML) {
    if (responseXML == null) {
        clear();
        window.location = "selectServiceNotAvailable.jsp";
    } else {
        locations = responseXML.getElementsByTagName("providerLocation");
        for(var k=0; k<locations.length;k++){
            destination[k] = locations[k].getElementsByTagName("location")[0].firstChild.nodeValue;
        }
    }
}

/*
 *  Add a marker to the map
 *  Hide a list of similar addresses
 */

function addMarker(location) {
    if (marker == null){
        marker = new google.maps.Marker({
            map: map
        });
    }
    codeLatLng(location);
    document.getElementById("addressSelect").style.display = "none";
   
}

/*
 * Function allow to get address from coordinates of marker
 * Place a marker on the map, if address is define, and zoom level is correct
 */

function codeLatLng(input) {

    //this variable show that result from geocode is define
    var resultExists = 0;
    //this variable define the minimum level of map zooming, which can allows you to place marker
    var zoomLevel = 15;
    var latlng = getLatLng(input);

    geocoder.geocode({'latLng': latlng}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            if (results[resultExists]) {
                if(map.getZoom()>zoomLevel){
                    marker.setPosition(input);
                    document.getElementById('address').value = results[resultExists].formatted_address;
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

/*
 * Function allow to get coordinates from address
 * objSel object is created when there are some objects with same address
 */

function codeAddress() {
    if(""==document.getElementById('address').value){
        showErrorMessage('Please, input the address');
    }else{
        //this variable show that strict result from geocode is define
        var strictResultExists = 0;
        var zoomLevel = 16;
        var address = document.getElementById('address').value;
        
        objSel = document.getElementById("addressSelect");
        var latlng;
        geocoder.geocode( {'address': address}, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                if(results.length>1){
                    objSel.style.display = "block";
                    for(i=0;i<results.length;i++){
                        objSel.options[i] = new Option(results[i].formatted_address, results[i].geometry.location);
                    }
                }
                latlng = getLatLng(results[strictResultExists].geometry.location);
                map.setCenter(latlng);
                document.getElementById('address').value = results[strictResultExists].formatted_address;
                map.setZoom(zoomLevel);
            } else {
                showErrorMessage('Wrong address. Please input another one');
            }
        });
    }
}

/*
 *Set marker on select option. Changes the value of input address
 */
function selectFunction(){
    var select = document.getElementById("addressSelect");
    var position =  getLatLng(select.value);
    map.setCenter(position);
    document.getElementById("address").value = select.options[select.selectedIndex].text;
}

/*
 *Converts String to Google location Object
 */
function getLatLng(loc){
    var position =  String(loc).slice(1, -1);
    var latlngStr = position.split(',', 2);
    var lat = parseFloat(latlngStr[0]);
    var lng = parseFloat(latlngStr[1]);
    var latlng = new google.maps.LatLng(lat, lng);
    return latlng;
}

/*
 *remove marker from map
 */
function removePointer(){
    clear();
    marker.setMap(null);
    marker = null;
    document.getElementById("address").value = "";
}

/*
 *THIS function calculate distance between marker and service locations
 */
function calcMinDistance(){
    
    var minPosition;
    //value is created for comparing distances
    var dis = Number.MAX_VALUE;
    //this variable show that strict result from geocode is define
    var strictResultExists = 0;
    //maximum distance between 2 locations.
    var maximumDistance = 50000;
    for(var k=0; k<destination.length;k++){
        geocoder.geocode({'address': destination[k]}, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                minLngLat = getLatLng(results[strictResultExists].geometry.location);
                var distance = google.maps.geometry.spherical.computeDistanceBetween(
                    getLatLng(marker.getPosition()),minLngLat).toFixed(2);
                if(parseFloat(distance)<parseFloat(dis)){
                    dis = distance;
                    minPosition = results[strictResultExists].formatted_address;
                }
            }else{
                showErrorMessage('Wrong address. Please input another one');
            }
        });
    }
    window.setTimeout(function(){
        var m = marker.getPosition();
        if(m != undefined){
            if(parseFloat(dis) < maximumDistance){
                minPos = minPosition;
            }else{
                showErrorMessage("You are too far");
            }
        }else{
            showErrorMessage("Choose location, please");
        }
    },delayTime);
}

/*
 * Function display providerlocations markers on map
 */
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


// block creates Google Map by initialize it with specific function
// if maps are not available redirect to error page
try{
    google.maps.event.addDomListener(window, 'load', initialize);
}catch(e){
    document.location.href = 'selectServiceNotAvailable.jsp';
}
