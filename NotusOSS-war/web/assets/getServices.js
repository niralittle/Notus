var req; //request to servlet
var contents; //content of responseXML

//makes request and implements the ajax
function getAvailableServices(){
        var pl = document.getElementById("providerLocation");
        var location = pl.getAttribute("name");
        var url = "ServicesServlet?providerLocationID="+location;
        req = initRequest();
        req.open("GET", url, true);
        req.onreadystatechange = callback;
        req.send(null);
}
//initializes request
function initRequest() {
    if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    } else{
        return new XMLHttpRequest();
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
    req.open("GET", url, true);
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