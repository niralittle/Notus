function checkFormat(id) {
    var checkdate = true;
    var textfield = document.getElementById(id);
    var v = textfield.value;
    var tip = document.getElementById("tip");
    if (!v.match(/^\d{4}-\d{2}-\d{2}$/)){
        tip.innerHTML = "Fit the format: yyyy-mm-dd";
        tip.style.display = '';
        tip.style.fontWeight = 'bold';
        tip.style.color = 'red';
        textfield.focus();
    } else {
        tip.style.display = 'none';
    }
    var array = v.split("-", 3);
    var day = parseInt(array[2], 10);
    var month = parseInt(array[1], 10);
    var year = parseInt(array[0], 10);
    if (day < 1 || day > 31 || month > 12 || month < 1 || year > 2050 ||
        year < 2000) {
        checkdate = false;
    }
    if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 31) {
        checkdate = false;
    }
    if (month == 2){
        if ((year % 4 == 0) && day > 29) {
            checkdate = false;
        }
        if ((year % 4 != 0) && day > 28) {
            checkdate = false;
        }
    }
    if (checkdate == false) {
        tip.innerHTML = "Incorrect date";
        tip.style.display = '';
        tip.style.fontWeight = 'bold';
        tip.style.color = 'red';
        textfield.focus();
    }
}

