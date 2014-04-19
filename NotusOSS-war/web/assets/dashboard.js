function tabSwitch(new_tab, new_content) {

        document.getElementById('dash-left').style.display = 'none';
        document.getElementById('dash-center').style.display = 'none';
        document.getElementById('dash-right').style.display = 'none';
        document.getElementById(new_content).style.display = 'block';

        document.getElementById('tab_1').className = '';
        document.getElementById('tab_2').className = '';
        document.getElementById('tab_3').className = '';
        document.getElementById(new_tab).className = 'active';

}