<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<script src="js/registerValidator.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Registration</title>
</head>

<body>
	<form name="register" method="post" onsubmit="return validate()">
		<h3>This page allows you to create a new profile</h3>
		<table border="1" width="30%" cellpadding="5">

			<tbody>
				<tr>
					<td>Login</td>
					<td><input type="text" name="login" id="login" value="" />
						<div id="loginMsg"></div></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="password" id="pass" value="" />

						<div id="passMsg"></div></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><input type="text" name="email" id="email" value="" />
						<div id="emailMsg"></div></td>
				</tr>

				<tr>
					<td style="width: 81px;">First Name</td>
					<td><input type="text" name="firstName" id="fname" value="" />
						<div id="fnameMsg"></div></td>
				</tr>
				<tr>
					<td>Last Name</td>
					<td><input type="text" name="lastName" id="lname" value="" />
						<div id="lnameMsg"></div></td>
				</tr>

			</tbody>
		</table>

		<input type="submit" value="Register" /> <a href="index.jsp"><input
			type="button" value="Home" name="submit" /></a>

		<p>
			Already registered!? <a href="login.jsp"> Login Here</a>
		</p>

	</form>
</body>
</html>
