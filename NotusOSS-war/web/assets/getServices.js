var req; //request to servlet
var contents; //content of responseXML
var locations; //service provider locations
var minPos; //minimal position between marker and provider location
var delayTime = 1000; //time to perform the functions request to Google

/*
 * makes request and implements the ajax
 */
function getServices(){
    minPos = undefined;
    if(marker != null){
        addLoad();
        calcMinDistance();
        window.setTimeout(function(){
            var minID = getID();
            getAvailableServices(minID);
            removeLoad();
        }, delayTime);
    }else{
        showErrorMessage("Choose location, please");
    }
}


function getAvailableServices(minID){
    var url = "ServicesServlet?providerLocationID="+minID;
    req = initRequest();
    req.open("POST", url, true);
    req.onreadystatechange = callback;
    req.send(null);
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
        alert("Error was occured, contact an administrator!");
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
    head.appendChild(document.createTextNode("Nearest provider at: "+minPos));
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
    ok.setAttribute("value", "Choose and proceed to order");
    ok.setAttribute("onclick", "goToRegistration()");
    ok.setAttribute("class", "btn btn-success");
    td.appendChild(ok);
    tr.appendChild(td);
    contents.appendChild(tr);
}
//redirects to the Registration
//send the serviceLocationID and serviceCatalogID parameters
function goToRegistration(){
    var selected = getSelected();
    if(selected != false){
        var selectedID = selected.getAttribute("id");
        var location = encodeURI(address.value);
        var postForm=document.getElementById('postForm');
        document.getElementById("serviceLocationID").setAttribute("value", location);
        document.getElementById("serviceCatalogID").setAttribute("value", selectedID);
        postForm.submit();
    }else{
        addErrorMessage("Choose service, please");
    }
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
    clear();
    addErrorMessage(message);
}
function addErrorMessage(message){
    removeErrorMessage();
    var errorPanel = document.getElementById("errorPanel");
    errorPanel.appendChild(document.createTextNode(message));
    window.setTimeout(function(){removeErrorMessage();}, 2000);
}
function removeErrorMessage(){
    var errorPanel = document.getElementById("errorPanel");
    errorPanel.innerHTML = "";
}