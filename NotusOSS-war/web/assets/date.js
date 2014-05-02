//************************************************
// Author    - Jonathan Grant
// Copyright - Jonathan Grant, 2014
// E-mail    - Jonathan_Grant@jondaf.com
//************************************************

//************************************************
// This JavaScript code will enable three drop-down
// lists to be set up and displayed in a form to show
// dates and select them. It will check the selected
// year to see if it is a leap year and adjust the
// number of days in February to reflect this. It
// will also check the selected month and adjust
// the number of days available in it displayed.
//
// There are three SELECT statements required, for
// day, month and year. They can be in any order.
// The month and year SELECTs need an onChange
// event handler which calls the checkYear function
// to enable the corect number of days in the month
// to be available.
//
// The year SELECT can be one of three functions
// to display either a range of years from a given
// year to another given year, a range of years
// from a given year to a year calculated from the
// current year, or a range of years calculated
// from the current year.
//
// The reset function is necessary to reset the
// days in the month.
//************************************************


//************************************************
// Set up array of days in a month
//************************************************

monthDays = new Array(31,
    28,
    31,
    30,
    31,
    30,
    31,
    31,
    30,
    31,
    30,
    31);

//************************************************
// Set up array of month names
//************************************************

monthName = new Array("January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December");

//************************************************
// Get todays date and year
//************************************************

todayDate = new Date();
todayDay = todayDate.getDate();
todayMonth = todayDate.getMonth();
todayYear = todayDate.getFullYear();

var defaultDaySelected;

//************************************************
// Function to check if a year is a leap year
//************************************************

function checkLeap(inYear) {
    isLeap = 0;

    if ((inYear % 4) == 0) {
        isLeap = 1;
    }

    if ((inYear % 100) == 0) {
        isLeap = 0;
        if ((inYear % 400) == 0) {
            isLeap = 1;
        }
    }
    return isLeap;
}

//************************************************
// Functions to display a drop-down showing a list
// of years in different ways
//************************************************

//************************************************
// This function displays variable years from
// this year - start to this year + end
//************************************************

function displayYearVariable(firstYear, lastYear, presetDate, preDate) {
    if (presetDate && (preDate != null)) {
        startDate = new Date(preDate);
        startDay = startDate.getDate();
        startMonth = startDate.getMonth();
        startYear = startDate.getFullYear();
    }
    else if (presetDate) {
        startDay = todayDay;
        startMonth = todayMonth;
        startYear = todayYear;
    }

    for (count = (todayYear - firstYear); count <= (todayYear + lastYear); count++) {
        document.write("<OPTION VALUE='" + count + "'");
        if (presetDate && (count == startYear)) {
            document.write(" SELECTED");
        }
        document.write(">" + count + "</OPTION>");
    }
}

//************************************************
// This function displays variable years from
// a static start year to this year + end
//************************************************

function displayYearStaticStart(firstYear, lastYear, presetDate, preDate) {
    if (presetDate && (preDate != null)) {
        startDate = new Date(preDate);
        startDay = startDate.getDate();
        startMonth = startDate.getMonth();
        startYear = startDate.getFullYear();
    }
    else if (presetDate) {
        startDay = todayDay;
        startMonth = todayMonth;
        startYear = todayYear;
    }

    for (count = firstYear; count <= (todayYear + lastYear); count++) {
        document.write("<OPTION VALUE='" + count + "'");
        if (presetDate && (count == startYear)) {
            document.write(" SELECTED");
        }
        document.write(">" + count + "</OPTION>");
    }
}

//************************************************
// This function displays static years from
// a start year to end year
//************************************************

function displayYearStatic(firstYear, lastYear, presetDate, preDate) {
    if (presetDate && (preDate != null)) {
        startDate = new Date(preDate);
        startDay = startDate.getDate();
        startMonth = startDate.getMonth();
        startYear = startDate.getFullYear();
    }
    else if (presetDate) {
        startDay = todayDay;
        startMonth = todayMonth;
        startYear = todayYear;
    }

    for (count = firstYear; count <= lastYear; count++) {
        document.write("<OPTION VALUE='" + count + "'");
        if (presetDate && (count == startYear)) {
            document.write(" SELECTED");
        }
        document.write(">" + count + "</OPTION>");
    }
}

//************************************************
// Function to display a drop-down showing a list
// of months
//************************************************

function displayMonth(presetDate, preDate) {
    if (presetDate && (preDate != null)) {
        startDate = new Date(preDate);
        startDay = startDate.getDate();
        startMonth = startDate.getMonth();
        startYear = startDate.getFullYear();
    }
    else if (presetDate) {
        startDay = todayDay;
        startMonth = todayMonth;
        startYear = todayYear;
    }

    for (count = 1; count <= 12; count++) {
        document.write("<OPTION VALUE='");
        if (count < 10) {
            document.write("0");
        }
        document.write(count + "'");
        if (presetDate && ((count - 1) == startMonth)) {
            document.write(" SELECTED");
        }
        document.write(">" + monthName[count - 1] + "</OPTION>");
    }
}

//************************************************
// Function to display a drop-down showing a list
// of dates, 01 to 31
//************************************************

function displayDay(presetDate, preDate) {
    if (presetDate && (preDate != null)) {
        startDate = new Date(preDate);
        startDay = startDate.getDate();
        startMonth = startDate.getMonth();
        startYear = startDate.getFullYear();
        countDays = monthDays[startMonth];
        leapYear = checkLeap(startYear);

        defaultDaySelected = startDay;
    }
    else if (presetDate) {
        startDay = todayDay;
        startMonth = todayMonth;
        startYear = todayYear;
        countDays = monthDays[todayMonth];
        leapYear = checkLeap(startYear);

        defaultDaySelected = startDay;
    }
    else {
        startMonth = 0;
        countDays = 31;
        leapYear = 0;
    }

    if (startMonth == 1) {
        countDays = countDays + leapYear;
    }

    for (count = 1; count <= countDays; count++) {
        document.write("<OPTION VALUE='");
        if (count < 10) {
            document.write("0");
        }
        document.write(count + "'");
        if (presetDate && (count == startDay)) {
            document.write(" SELECTED");
        }
        document.write(">");
        if (count < 10) {
            document.write("0");
        }
        document.write(count + "</OPTION>");
    }
}

//************************************************
// Function to check the range of dates displayed
// in the drop-down lists and to ensure that they
// are valid, adjusting the available dates as
// necessary
//
// This function requires three arguments:
// dayObj - the drop-down day object (this.form.dayObjName)
// monthObj - the drop-down month object (this.form.monthObjName)
// yearObj - the drop-down year object (this.form.yearObjName)
//************************************************

function checkYear(dayObj, monthObj, yearObj) {
    yearValueInd = yearObj.selectedIndex;
    yearValue = yearObj[yearValueInd].value;
    monthValueInd = monthObj.selectedIndex;
    monthValue = monthObj[monthValueInd].value;
    dayValueInd = dayObj.selectedIndex;
    dayValue = dayObj[dayValueInd].value;

    dayValueSize = dayObj.length;

    defaultDay = 0;

    for (count = 1; count <= (dayValueSize - 1); count++) {
        if (dayObj.options[count].defaultSelected) {
            defaultDay = count;
        }
    }

    //************************************************
    // Check whether the selected year is a leap year
    //************************************************

    leapYear = checkLeap(yearValue);

    //************************************************
    // Get the number of days that the selected month
    // has in it
    //************************************************

    totalDays = monthDays[parseInt(monthValue, 10) - 1];

    //************************************************
    // Check whether the selected month is February
    // and if it is adjust the number of days in the
    // month if it is a leap year
    //************************************************

    if (monthValue == 2) {
        totalDays = totalDays + leapYear;
    }

    //************************************************
    // Adjust the day drop-down to show the correct
    // number of days available
    //************************************************

    dayObj.length = totalDays;
    for (count = 1; count <= totalDays; count++) {
        if (count < 10) {
            countText = "0" + count;
        }
        else {
            countText = count;
        }
        newOption = new Option(countText, count);
        dayObj.options[count - 1] = newOption;
    }

    //************************************************
    // Adjust the day currently displayed in the day
    // drop down if it would now be outside of the
    // number of actual days in the displayed month
    //************************************************

    if (totalDays <= dayValueInd) {
        dayValueInd = (totalDays - 1);
    }
    dayObj.selectedIndex = dayValueInd;

    if (totalDays <= defaultDay) {
        dayObj.options[totalDays - 1].defaultSelected = true;
    }
    else {
        dayObj.options[defaultDay].defaultSelected = true;
    }
}

/*********************************************
 * Reset function. This is necessary in case *
 * a default start date has been used        *
 *********************************************/

function formReset(formObj, dayObj, monthObj, yearObj, presetDate, preDate) {
    if (presetDate && (preDate != null)) {
        startDate = new Date(preDate);
        startDay = startDate.getDate();
        startMonth = startDate.getMonth();
        startYear = startDate.getFullYear();

        countDays = monthDays[startMonth];
        defaultDaySelected = startDay;
    }
    else if (presetDate) {
        startDay = todayDay;
        startMonth = todayMonth;
        startYear = todayYear;

        countDays = monthDays[startMonth];
        defaultDaySelected = startDay;
    }
    else {
        countDays = 31;
    }

    leapYear = checkLeap(startYear);
    if (startMonth == 1) {
        countDays = countDays + leapYear;
    }

    dayObj.length = countDays;
    for (count = 1; count <= countDays; count++) {
        if (count < 10) {
            countText = "0" + count;
        }
        else {
            countText = count;
        }
        newOption = new Option(countText, count);
        dayObj.options[count - 1] = newOption;
    }

    if (presetDate) {
        dayObj.options[defaultDaySelected - 1].defaultSelected = true;
    }

    return true;
}

function continueOrNot() {
    var tip = document.getElementById("tip");
    if (formDate()){
        tip.style.display = 'none';
        return true;
    } else {
        tip.innerHTML = 'Incorrect date! "From" date must be earlier than "To" date';
        tip.style.display = '';
        tip.style.color = "red";
        return false;
    }
}
function formDate() {
    var resultFrom;
    var resultTo;
    var flag = true;
//    var check = document.getElementById("year");
    //    if (check.disabled) {
        var selectedFrom = document.getElementById("yearFrom");
        var selectedTo = document.getElementById("yearTo");
        var from = parseInt(selectedFrom.options[selectedFrom.selectedIndex].value);
        var to = parseInt(selectedTo.options[selectedTo.selectedIndex].value);
        if (from > to) {
            flag = false;
        } else if (from == to) {
            selectedFrom = document.getElementById("monthFrom");
            selectedTo = document.getElementById("monthTo");
            from = selectedFrom.selectedIndex + 1;
            to = selectedTo.selectedIndex + 1;
            if (from > to) {
                flag = false;
            } else if (from == to) {
                selectedFrom = document.getElementById("dayFrom");
                selectedTo = document.getElementById("dayTo");
                from = parseInt(selectedFrom.options[selectedFrom.selectedIndex].value);
                to = parseInt(selectedTo.options[selectedTo.selectedIndex].value);
                if (from > to) {
                    flag = false;
                }
            }
        }
        if (flag == true) {
            selectedFrom = document.getElementById("yearFrom");
            selectedTo = document.getElementById("yearTo");
            from = parseInt(selectedFrom.options[selectedFrom.selectedIndex].value);
            to = parseInt(selectedTo.options[selectedTo.selectedIndex].value);

            resultFrom = from + '-';
            resultTo = to + '-';
       
            selectedFrom = document.getElementById("monthFrom");
            selectedTo = document.getElementById("monthTo");
            from = selectedFrom.selectedIndex + 1;
            to = selectedTo.selectedIndex + 1;
            if (from < 10){
                resultFrom += '0' + from + '-';
            } else {
                resultFrom += from + '-';
            }
            if (to < 10) {
                resultTo += '0' + to + '-';
            } else {
                resultTo += to + '-';
            }
        

            selectedFrom = document.getElementById("dayFrom");
            selectedTo = document.getElementById("dayTo");
            from = parseInt(selectedFrom.options[selectedFrom.selectedIndex].value);
            to = parseInt(selectedTo.options[selectedTo.selectedIndex].value);
            if (from < 10){
                resultFrom += '0' + from;
            } else {
                resultFrom += from;
            }
            if (to < 10) {
                resultTo += '0' + to;
            } else {
                resultTo += to;
            }
            document.getElementById("fromdate").value = resultFrom;
            document.getElementById("todate").value = resultTo;

        }
//    } else {
//        var month = document.getElementById("month");
//        var year = document.getElementById("year");
//        month = month.selectedIndex + 1;
//        year = parseInt(year.options[year.selectedIndex].value);
//        document.getElementById("bymonth").value = year + "-" + month + "-" + "01";
//    }
    return flag;
}
function disableDate() {
    var selectedOne = document.getElementById("yearFrom");
    var selectedTwo = document.getElementById("yearTo");
    selectedOne.disabled = true;
    selectedTwo.disabled = true;
    selectedOne = document.getElementById("monthFrom");
    selectedTwo = document.getElementById("monthTo");
    selectedOne.disabled = true;
    selectedTwo.disabled = true;
    selectedOne = document.getElementById("dayFrom");
    selectedTwo = document.getElementById("dayTo");
    selectedOne.disabled = true;
    selectedTwo.disabled = true;
    selectedOne = document.getElementById("year");
    selectedTwo = document.getElementById("month");
    selectedOne.disabled = true;
    selectedTwo.disabled = true;
}
function enableDate() {
    disableDate();
    var selectedOne = document.getElementById("yearFrom");
    var selectedTwo = document.getElementById("yearTo");
    selectedOne.disabled = false;
    selectedTwo.disabled = false;
    selectedOne = document.getElementById("monthFrom");
    selectedTwo = document.getElementById("monthTo");
    selectedOne.disabled = false;
    selectedTwo.disabled = false;
    selectedOne = document.getElementById("dayFrom");
    selectedTwo = document.getElementById("dayTo");
    selectedOne.disabled = false;
    selectedTwo.disabled = false;

}
function enableMonthSelect() {
    disableDate();
    selectedOne = document.getElementById("year");
    selectedTwo = document.getElementById("month");
    selectedOne.disabled = false;
    selectedTwo.disabled = false;
}