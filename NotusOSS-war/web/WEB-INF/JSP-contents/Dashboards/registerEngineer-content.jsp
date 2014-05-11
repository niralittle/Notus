  <%= request.getAttribute("errMessage") == null ? "" : request.getAttribute("errMessage")%>
  <h3 style="color:green;"><%=request.getAttribute("success") == null ? "" : request.getAttribute("success")%></h3>   
       
        <form name="Register" method="post" onsubmit="return validate()" action="Register">
            <table border="0" cellpadding="5">
                <tr>
                    <td>
                        <div class="form-group" style="width: 300px;">
                            <label for="login">Username:</label>
                            <input class="form-control" required type="text" name="login" id="login" oninput="loginValidate()"
                                   value="<%=request.getParameter("login") == null ? "" : request.getParameter("login")%>" />
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
                            <input class="form-control" required type="password" name="password" id="pass"/>
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
                            <input class="form-control" required type="password" name="passwordConf" id="pass2"/>
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
                            <input class="form-control" required type="text" name="email" oninput="emailValidate()" id="email"
                                   value="<%=request.getParameter("email") == null ? "" : request.getParameter("email")%>" />
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
                            <input class="form-control" required type="text" name="firstName" id="fname"
                                   value="<%=request.getParameter("firstName") == null ? "" : request.getParameter("firstName")%>" />
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
                            <input class="form-control" required type="text" name="lastName" id="lname"
                                   value="<%=request.getParameter("lastName") == null ? "" : request.getParameter("lastName")%>" />
                        </div>
                    </td>
                    <td>
                        <div id="lnameMsg"></div>
                    </td>
                </tr>
            <tr>
            	<td>
                    <div class="form-group" style="width: 300px;">
                        <label for="select">Choose employee group: </label>

                        <select class="form-control" id="select" name="employeeGroup">
                            <option value="2">Supporting</option>
                            <option value="3">Provisioning</option>
                            <option value="4">Installation</option>
                        </select>
                    </div>
            	</td>
            </tr>
        </table>
        <button type="submit" class="btn btn-success" style="margin-top: 25px">Register</button>
        </form>
