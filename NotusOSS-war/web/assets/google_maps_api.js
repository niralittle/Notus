var map;
var marker;
var geocoder;
var objSel; //HTML <select> object for multiple addresses
var destination = [];
var minimumDistance;
var minLngLat;

//ADDRESSES OF PROVIDER LOCATIONS!!!
destination[0] = 'Stockholm+Sweden';
destination[1] = '25 Craven Street, London WC2N, UK';
destination[2] = 'Kiev, Ua';

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
    geocoder.geocode({'latLng': latlng}, function(results, status) {
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
        geocoder.geocode( {'address': address}, function(results, status) {
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
    marker.setMap(null);
    document.getElementById("address").value = "";
    objSel.style.display = "none";
}

function showZoomMessage(){
    document.getElementById("spoiler_body").style.display = "block";
    window.setTimeout(function(){
        document.getElementById("spoiler_body").style.display = "none";
    },5000);
}
/*
 *Google api. DONT WORK IF points are lie across the ocean
function calculateDistances() {
  var service = new google.maps.DistanceMatrixService();
  service.getDistanceMatrix(
    {
      origins: [marker.getPosition()],
      destinations: destination,
      unitSystem: google.maps.UnitSystem.METRIC,
      avoidHighways: false,
      avoidTolls: false
    }, callback);
}

function callback(response, status) {
  if (status != google.maps.DistanceMatrixStatus.OK) {
    alert('Error was: ' + status);
  } else {
    var origins = response.originAddresses;
    var destinations = response.destinationAddresses;
    var outputDiv = document.getElementById('outputDiv');
    outputDiv.innerHTML = '';
    var min;
    for (var i = 0; i < origins.length; i++) {
      var results = response.rows[i].elements;
      min = results[i].distance.text;
      for (var j = 0; j < results.length; j++) {
          if(results[j].distance.text<min){
              min = results[j].distance.text;
              minimumDistance = j;
          }
        outputDiv.innerHTML += origins[i] + ' to ' + destinations[j]
            + ': ' + results[j].distance.text+ '<br>';

      }
       outputDiv.innerHTML += 'Minimal location: '+ destination[minimumDistance];
    }
  }
}
*/

function getMinDistance(){
    var dis = 100000;
    for(var i=0; i<destination.length;i++){
        geocode(destination[i]);
        var distance = google.maps.geometry.spherical.computeDistanceBetween(
           getLatLng(marker.getPosition()),
           minLngLat).toFixed(2);
        if(distance<dis){
            dis = i;
        }
    }
    alert(dis);
}


function geocode(address){
        geocoder.geocode( {'address': address}, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                minLngLat = getLatLng(results[0].geometry.location);
            } else {
                alert('Wrong address. Please input another one');
            }
        });
}


google.maps.event.addDomListener(window, 'load', initialize);