<%--
    Document   : register-content
    Created on : 12 04 2014, 19:53:58
    Author     : Panchenko Dmytro
--%>

<script type="text/javascript" src="assets/registerValidator.js"></script>

<form method="post" action="CustomerUser">
    <input id="serviceLocationID" name="serviceLocationID" type='hidden' 
           value ="<%=request.getParameter("serviceLocationID") == null
                   ? "" : request.getParameter("serviceLocationID")%>">
    <input id="serviceCatalogID" name="serviceCatalogID" type='hidden' 
           value ="<%=request.getParameter("serviceCatalogID") == null ?
               "" : request.getParameter("serviceCatalogID")%>">
    <h4>Already registered!?
        <button type="submit" class="btn btn-sm btn-warning"
             onclick="this.parentNode.submit(); return false;">Login here</button>
    </h4>
</form>

<form name="Register" method="post" onsubmit="return validate()" action="Register">
    <input id="serviceLocationID" name="serviceLocationID" type='hidden' 
           value="<%=request.getParameter("serviceLocationID") == null ?
               "" : request.getParameter("serviceLocationID")%>">
    <input id="serviceCatalogID" name="serviceCatalogID" type='hidden' 
           value="<%=request.getParameter("serviceCatalogID") == null ?
               "" : request.getParameter("serviceCatalogID")%>">
    <h4>To join our happy customers, please, fill in the form below: </h4>

<%= request.getAttribute("errMessage") == null ? "" : request.getAttribute("errMessage")%>

    <table border="0" cellpadding="5">
        <tr>
            <td>
                <div class="form-group" style="width: 300px;">
                    <label for="login">Username:</label>
                    <input class="form-control" required type="text" name="login"
                           id="login" oninput="loginValidate()"
                       value="<%=request.getParameter("login") == null ? ""
                       : request.getParameter("login")%>" />
                </div>
            </td>
            <td>
                <div id="loginMsg"></div>
            <td>
        </tr>
        <tr>
            <td>
                <div class="form-group" style="width: 300px;">
                    <label for="pass">Password (6 characters minimum):</label>
                    <input class="form-control" required type="password"
                           name="password" id="pass"/>
                </div>
            </td>
            <td>
                <div id="passMsg"></div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="form-group" style="width: 300px;">
                    <label for="pass2">Repeat password:</label>
                    <input class="form-control" required type="password"
                           name="passwordConf" id="pass2"/>
                </div>
            </td>
            <td>
                <div id="passMsg2"></div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="form-group" style="width: 300px;">
                    <label for="email">Email: </label>
                    <input class="form-control" required type="text" name="email"
                           oninput="emailValidate()" id="email"
                           value="<%=request.getParameter("email") == null ?
                               "" : request.getParameter("email")%>" />
                </div>
            </td>
            <td>
                <div id="emailMsg"></div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="form-group" style="width: 300px;">
                    <label for="fname">First name: </label>
                    <input class="form-control" required type="text"
                           name="firstName" id="fname"
                       value="<%=request.getParameter("firstName") == null ?
                           "" : request.getParameter("firstName")%>" />
                </div>
            </td>
            <td>
                <div id="fnameMsg"></div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="form-group" style="width: 300px;">
                    <label for="lname">Last name: </label>
                    <input class="form-control" required type="text"
                           name="lastName" id="lname"
                       value="<%=request.getParameter("lastName") == null ?
                           "" : request.getParameter("lastName")%>" />
                </div>
            </td>
            <td>
                <div id="lnameMsg"></div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="form-group" style="width: 300px;">
                    <label for="code">Input text on image below: </label>
                    <input class="form-control" type="text" name="code" id="code"/>
                </div>
            </td>
        </tr>
        <tr>
            <td>
            	<img style="margin-left: 75px" src="Captcha">
            	<button name="action" value="Refresh" type="submit" class="btn btn-success" style="margin-top: 25px">Refresh</button>         	
            </td>
        </tr>
    </table>

    <button type="submit" class="btn btn-success" style="margin-top: 25px">Register</button>
    <a href="index.jsp"><button type="submit" class="btn btn-info"
                                style="margin-top: 25px">Home</button></a>
</form>

