var req;
var isIE;
var contents;


function getAvailableServices(){
        var location = escape(address.value);
        var url = "ServicesServlet?providerLocationID="+location;
        req = initRequest();
        req.open("GET", url, true);
        req.onreadystatechange = callback;
        req.send(null);
}
function initRequest() {
    if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    } else{
        return new XMLHttpRequest();
    }
}
function callback() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            clear();
            parseMessages(req.responseXML);
        }
    }
}

function clear() {
    var servicesTable = document.getElementById("services");
    servicesTable.innerHTML = "";
}
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

function getSelected(){
    var radios = document.getElementsByName("serv");
    for (var I = 0 ; I < radios.length ; I++) {
        if(radios[I].checked){
            return radios[I];
        }
    }
    return false;
}