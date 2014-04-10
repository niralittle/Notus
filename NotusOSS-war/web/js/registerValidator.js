/**
 * 
 */

function validate() {
		//getting inputed values in fields
		var login = document.getElementById("login");
		var pass = document.getElementById("pass");
		var email = document.getElementById("email");
		var fname = document.getElementById("fname");
		var lname = document.getElementById("lname");

		//Regex for validation email input
		var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

		// value to return. If all data valid - all OK!
		var valid = true;

		//validate not empty and attach message to appropriate html-tag
		if (login.value.length <= 0) {
			document.getElementById("loginMsg").innerHTML = "login can't be empty; ";
			valid = false;
		}
		if (pass.value.length <= 0) {
			document.getElementById("passMsg").innerHTML = "password can't be empty; ";
			valid = false;
		}
		if (lname.value.length <= 0) {
			document.getElementById("lnameMsg").innerHTML = "last name can't be empty; ";
			valid = false;
		}
		if (fname.value.length <= 0) {
			document.getElementById("fnameMsg").innerHTML = "first name can't be empty; ";
			valid = false;
		}

		//check for valid email using filter
		if (!filter.test(email.value)) {
			document.getElementById("emailMsg").innerHTML = "Please provide a valid email address";
			valid = false;
		}
		
		//send page to server side only if inputed data is correct
		return valid;
	};
