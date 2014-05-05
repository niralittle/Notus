var url = "ServiceCatalogServlet";
    req = new XMLHttpRequest();
    req.open("POST", url, true);
    req.onreadystatechange = callback;
    req.send(null);
var content;
 function callback() {
    if (req.readyState == 4) {
        if (req.status == 200) {//if request is ready
            parseMessages(req.responseXML);
        }
    }
}
function parseMessages(responseXML) {
    if (responseXML == null) {
        return false;
    } else {
        var locations = responseXML.getElementsByTagName("location");
        content = document.getElementById("content");
        for(var i = 0; i < locations.length; i++){
            createRef(locations[i],i);
        }
    }
}
function createRef(location,i){
    var name = location.getElementsByTagName("name")[0].firstChild.nodeValue;
    var div = document.createElement("div");
    div.setAttribute("style", "margin:0 auto;height:50px;width:500px;background-color:#E9F2FF;text-align:center;");
    var a = document.createElement("a");
    a.setAttribute("href", "#open"+i);
    var showComand = "show('table"+i+"',200,5)";
    a.setAttribute("onclick", showComand);
    a.setAttribute("style", "font-size: 150%;color: #000;text-decoration: none;");
    a.appendChild(document.createTextNode(name));
    div.appendChild(a);
    content.appendChild(div);
    createTable(location,i);
}
function createTable(location,i){
    var div = document.createElement("div");
    div.setAttribute("id", "table"+i);
    div.setAttribute("style", "font-size: 120%;margin:0 auto;display:none;height:200px;width:540px;");
    var address = location.getElementsByTagName("address")[0].firstChild.nodeValue;
    address = "Location Address: " + address;
    div.appendChild(document.createTextNode(address));
    var table = document.createElement("table");
    table.setAttribute("style", "width:500px;font-size: 120%;text-align: center;");
    var tHead = createTHead();
    table.appendChild(tHead);
    var catalogs = location.getElementsByTagName("catalog");
    for(var j = 0; j < catalogs.length; j++){
        var tRow = createTBody(catalogs[j]);
        table.appendChild(tRow);
    }
    
    div.appendChild(table);
    content.appendChild(div);
}
function createTHead(){
    var tr = document.createElement("tr");
    tr.setAttribute("style", "background-color:#E9F2FF");
    var th1 = document.createElement("th");
    th1.appendChild(document.createTextNode("Name of service"));
    th1.setAttribute("style", "border: 2px solid #dce7e7; padding: 2px;");
    var th2 = document.createElement("th");
    th2.appendChild(document.createTextNode("Price"));
    th2.setAttribute("style", "border: 2px solid #dce7e7; padding: 2px;");
    tr.appendChild(th1);
    tr.appendChild(th2);
    return tr;
}
function createTBody(catalog){
    var tr = document.createElement("tr");
    var td1 = document.createElement("td");
    var td2 = document.createElement("td");
    var name = catalog.getElementsByTagName("name")[0].firstChild.nodeValue;
    var price = catalog.getElementsByTagName("price")[0].firstChild.nodeValue;
    td1.appendChild(document.createTextNode(name));
    td1.setAttribute("style", "border: 2px solid #dce7e7; padding: 2px;");
    td2.appendChild(document.createTextNode(price));
    td2.setAttribute("style", "border: 2px solid #dce7e7; padding: 2px;");
    tr.appendChild(td1);
    tr.appendChild(td2);
    return tr;
}
var s=[],s_timer=[];
function show(id,h,spd) {
    if(s[id]==spd){
        s[id]=-spd;
    }else{
        s[id]=spd;
    }
    s_timer[id]=setTimeout(function() {
        var obj=document.getElementById(id);
        if(obj.offsetHeight+s[id]>=h){
            obj.style.height=h+"px";
            obj.style.overflow="auto";
        } else if(obj.offsetHeight+s[id]<=0){
            obj.style.height=0+"px";
            obj.style.display="none";
        } else {
            obj.style.height=(obj.offsetHeight+s[id])+"px";
            obj.style.overflow="hidden";
            obj.style.display="block";
            setTimeout(arguments.callee, 10);
        }
    }, 10);
}
function goToSelectLocation(){
    location = "selectLocation.jsp";
}
