/**
 * 
 */

function validate() {
	// getting inputed values in fields
	var login = document.getElementById("login");
	var pass = document.getElementById("pass");
	var pass2 = document.getElementById("pass2");
	var email = document.getElementById("email");
	var fname = document.getElementById("fname");
	var lname = document.getElementById("lname");

	// Regex for validation inputed data
	var email_check = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var login_check =/^[A-Za-z0-9_-]{3,40}$/;
	var password_check = /^[A-Za-z0-9!@#$%^&*()_]{6,40}$/;

	// value to return. If all data valid - all OK!
	var valid = true;

	// check equals of inputed passwords

	if (pass.value !== pass2.value) {
		document.getElementById("passMsg2").innerHTML = "passwords don't match!";
		valid = false;
	} else {
		document.getElementById("passMsg2").innerHTML = " ";
	}

	// check for valid email using filter
	if (!email_check.test(email.value)) {
		document.getElementById("emailMsg").innerHTML = "Please provide a valid email address";
		valid = false;
	} else {
		document.getElementById("emailMsg").innerHTML = " ";
	}


	if (!login_check.test(login.value)) {
		document.getElementById("loginMsg").innerHTML = "Please provide a valid login. min 3 chars, max 30 chars, without spaces";
		valid = false;
	} else {
		document.getElementById("loginMsg").innerHTML = " ";
	}

	if (!password_check.test(pass.value)) {
		document.getElementById("passMsg").innerHTML = "You must enter a valid Password, min 6 char";
		valid = false;
	} else {
		document.getElementById("passMsg").innerHTML = " ";
	}

	if (!login_check.test(lname.value)) {	
		document.getElementById("lnameMsg").innerHTML = "You must input valid last name.";
		valid = false;
	} else {
		document.getElementById("lnameMsg").innerHTML = " ";
	}

	if (!login_check.test(fname.value)) {
		document.getElementById("fnameMsg").innerHTML = "You must input valid first name.";
		valid = false;
	} else {
		document.getElementById("fnameMsg").innerHTML = "";
	}
	
	// send page to server side only if inputed data is correct
	return valid;
};

// used to oninput validate
function emailValidate () {
	var email = document.getElementById("email");

	// Regex for validation inputed data
	var email_check = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	
	// check for valid email using filter
	if (!email_check.test(email.value)) {
		document.getElementById("emailMsg").innerHTML = "Please provide a valid email address";
	} else {
		document.getElementById("emailMsg").innerHTML = " ";
	}

};

function loginValidate() {
		// getting inputed values in fields
		var login = document.getElementById("login");

		// Regex for validation inputed data
		var login_check =/^[A-Za-z0-9_-]{3,40}$/;

		if (!login_check.test(login.value)) {
			document.getElementById("loginMsg").innerHTML = "Please provide a valid login. min 3 chars, max 30 chars, without spaces";
		} else {
			document.getElementById("loginMsg").innerHTML = " ";
		}

};
