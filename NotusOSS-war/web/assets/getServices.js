var req; //request to servlet
var contents; //content of responseXML
var locations;
var minPos;

//makes request and implements the ajax
function getServices(){
    minPos = undefined;
    if(marker != null){
        addLoad();
        calcMinDistance();
        window.setTimeout(function(){
            var minID = getID();
            getAvailableServices(minID);
            removeLoad();
        }, 1000);
    }else{
        showErrorMessage("Choose location, please");
    }
}
function getAvailableServices(minID){
    if(minID != undefined){
        var url = "ServicesServlet?providerLocationID="+minID;
        req = initRequest();
        req.open("POST", url, true);
        req.onreadystatechange = callback;
        req.send(null);
    }else{
        showErrorMessage("Choose location, please");
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


//clears the table of services
function clear() {
    var servicesTable = document.getElementById("services");
    servicesTable.innerHTML = "";
    removeHeader();
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
            addHeader();
            addButton();
        }
    }
}
function addHeader(){
    var head = document.getElementById("header");
    head.appendChild(document.createTextNode(minPos));
}
function removeHeader(){
    var head = document.getElementById("header");
    head.innerHTML = "";
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
    td.appendChild(document.createTextNode(name+", $"+price+"/month"));
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
    window.location = "registration.jsp?serviceLocationID="+location+"&serviceCatalogID="+selectedID;
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
    var button = document.getElementById("see");
    button.setAttribute("disabled", "true");
}
function removeLoad(){
    document.getElementById("loader").style.display = "none";
    var button = document.getElementById("see");
    button.removeAttribute("disabled");
}
function showErrorMessage(message){
    removeErrorMessage();
    var errorPanel = document.getElementById("errorPanel");
    errorPanel.appendChild(document.createTextNode(message));
    window.setTimeout(function(){removeErrorMessage();}, 2000);
}
function removeErrorMessage(){
    var errorPanel = document.getElementById("errorPanel");
    errorPanel.innerHTML = "";
}