var req; //request to servlet
var contents; //content of responseXML
var locations;
var minPos;

//makes request and implements the ajax
function getServices(){
    addLoad();
    getMinDistance();
    if(minPos != undefined){
        var minID = getID();
        getAvailableServices(minID);
        var head = document.getElementById("header");
        head.appendChild(document.createTextNode(minPos));
    }else{
        showFarMessage();
    }
    removeLoad();
}
function getAvailableServices(minID){
    if(minID != undefined){
        var url = "ServicesServlet?providerLocationID="+minID;
        req = initRequest();
        req.open("POST", url, true);
        req.onreadystatechange = callback;
        req.send(null);
    }else{
        alert("Choose location, please");
    }
}
//initializes request
function initRequest() {
    if (window.ActiveXObject) {
        return new ActiveXObject("Microsoft.XMLHTTP");
    } else{
        return new XMLHttpRequest();
    }
}
function getID(){
    for(var k=0; k<locations.length;k++){
        if(minPos == locations[k].getElementsByTagName("location")[0].firstChild.nodeValue){
            return locations[k].getElementsByTagName("id")[0].firstChild.nodeValue;
        }
    }
}
//callback function
function callback() {
    if (req.readyState == 4) {
        if (req.status == 200) {//if request is ready
            clear();
            parseMessages(req.responseXML);
        }
    }
}
//makes request and implements the ajax
function getMinDistance(){
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

        minPos = calcMinDistance();
    }
}

//clears the table of services
function clear() {
    var servicesTable = document.getElementById("services");
    servicesTable.innerHTML = "";
}
//parses the responseXML and outputs the table of services
function parseMessages(responseXML) {
    if (responseXML == null) {
        clear();
    } else {
        var services = responseXML.getElementsByTagName("service");
        contents = document.getElementById("services");
        for (var I = 0 ; I < services.length ; I++) {
            var service = services[I];
            var id = service.getElementsByTagName("id")[0].firstChild.nodeValue;
            var name = service.getElementsByTagName("name")[0].firstChild.nodeValue;
            var price = service.getElementsByTagName("price")[0].firstChild.nodeValue;
            appendService(id,name,price);
        }
        if(services.length>0){
            addButton();
        }
    }
}
//forms the table of services
function appendService(id,name,price) {
    var tr = document.createElement("tr");
    var td = document.createElement("td");
    var radio = document.createElement("input");
    radio.setAttribute("id", id);
    radio.setAttribute("type", "radio");
    radio.setAttribute("name", "serv");
    td.appendChild(radio);
    td.appendChild(document.createTextNode(name+" "+price));
    tr.appendChild(td);
    contents.appendChild(tr);
}
//forms the "Proceed the order" button
function addButton() {
    var tr = document.createElement("tr");
    var td = document.createElement("td");
    var ok = document.createElement("input");
    ok.setAttribute("id", "proceed");
    ok.setAttribute("type", "button");
    ok.setAttribute("value", "Proceed to order");
    ok.setAttribute("onclick", "goToRegistration()");
    td.appendChild(ok);
    tr.appendChild(td);
    contents.appendChild(tr);
}
//redirects to the Registration
//send the serviceLocationID and serviceCatalogID parameters
function goToRegistration(){
    var selected = getSelected();
    var selectedID = selected.getAttribute("id");
    var location = escape(address.value);
    var url = "Register?serviceLocationID="+location+"&serviceCatalogID="+selectedID;
    req = initRequest();
    req.open("POST", url, true);
    req.send(null);
    window.location = "registration.jsp";
}
//finds, which service is selected
function getSelected(){
    var radios = document.getElementsByName("serv");
    for (var I = 0 ; I < radios.length ; I++) {
        if(radios[I].checked){
            return radios[I];
        }
    }
    return false;
}
function addLoad(){
    document.getElementById("loader").style.display = "block";
}
function removeLoad(){
    document.getElementById("loader").style.display = "none";
}