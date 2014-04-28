function tabSwitch(new_tab, new_content) {

    document.getElementById('dash-left').style.display = 'none';
    document.getElementById('dash-center').style.display = 'none';
    document.getElementById('dash-right').style.display = 'none';
    document.getElementById('dash-report').style.display = 'none';
    document.getElementById(new_content).style.display = 'block';

    document.getElementById('tab_1').className = '';
    document.getElementById('tab_2').className = '';
    document.getElementById('tab_3').className = '';
    document.getElementById('tab_4').className = '';
    document.getElementById(new_tab).className = 'active';

}

function showInstEngTask(){
    document.getElementById('IEtask').style.display = "none";
    document.getElementById('instEngContent').style.display = "block";
    document.getElementById('anotherTask').style.display = "block";
}

function showInstAllTasks(){
    document.getElementById('IEtask').style.display = "block";
    document.getElementById('instEngContent').style.display = "none";
}

function showCableText(){
    document.getElementById('cableText').style.display = "block";
}

function checkAvailablePorts(num){
    if(num>1){
        document.getElementById('somePortsAreAvailable').style.display = "block";
    }else{
        document.getElementById('createRouter').style.display = "block";
    }

}

function showTask(){
    document.getElementById("selectTask").style.display = "block";
    document.getElementById("createRouter").style.display = "none";
    document.getElementById("createCable").style.display = "none";
    document.getElementById("connectCabel").style.display = "none";
    document.getElementById("deleteRouter").style.display = "none";
}

function showCrRouter(){
    document.getElementById("selectTask").style.display = 'none';
    document.getElementById("createRouter").style.display = 'block';
    document.getElementById("createCable").style.display = 'none';
    document.getElementById("connectCabel").style.display = 'none';
    document.getElementById("deleteRouter").style.display = 'none';
}
function showCrCable(){
    document.getElementById("selectTask").style.display = "none";
    document.getElementById("createRouter").style.display = "none";
    document.getElementById("createCable").style.display = "block";
    document.getElementById("connectCabel").style.display = "none";
    document.getElementById("deleteRouter").style.display = "none";
}
function showConnect(){
    document.getElementById("selectTask").style.display = "none";
    document.getElementById("createRouter").style.display = "none";
    document.getElementById("createCable").style.display = "none";
    document.getElementById("connectCabel").style.display = "block";
    document.getElementById("deleteRouter").style.display = "none";
}
function showDelete(){
    document.getElementById("selectTask").style.display = "none";
    document.getElementById("createRouter").style.display = "none";
    document.getElementById("createCable").style.display = "none";
    document.getElementById("connectCabel").style.display = "none";
    document.getElementById("deleteRouter").style.display = "block";
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