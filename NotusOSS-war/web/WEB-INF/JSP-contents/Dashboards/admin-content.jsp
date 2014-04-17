<script type="text/javascript" src="assets/dashboard.js"></script>


<div id="content">
    <ul class="tabs">
        <li><a href="javascript:tabSwitch('tab_1', 'admin-left');" id="tab_1" class="active">Create Employee Account</a></li>
        <li><a href="javascript:tabSwitch('tab_2', 'admin-center');" id="tab_2">Reassign task</a></li>
        <li><a href="javascript:tabSwitch('tab_3', 'admin-right');" id="tab_3">Block Account</a></li>
    </ul>
    <div id="admin-left" class="content">
        <div id="container-header"><span>Create Employee Account</span>        </div>  
        <script type="text/javascript" src="assets/registerValidator.js"></script>
  <%--      <form name="Register" method="post" onsubmit="return validate()" action="Register">--%>
            <table border="0" cellpadding="5">
                <tr>
                    <td>Login: </td>
                    <td><input required type="text" name="login" id="login" oninput="loginValidate()" value="" />
                        <div id="loginMsg"></div>
                    </td>
                </tr>
                <tr>
                    <td>Password: </td>
                    <td><input required type="password" name="password" id="pass" value="" />

                        <div id="passMsg"></div>
                    </td>
                </tr>
                <tr>
                    <td>Repeat password: </td>
                    <td><input required type="password" name="password2" id="pass2" value="" />

                        <div id="passMs2"></div>
                    </td>
                </tr>
                <tr>
                    <td>Email: </td>
                    <td><input required type="text" name="email" oninput="emailValidate()" id="email"  value="" />
                        <div id="emailMsg"></div>
                    </td>
                </tr>

                <tr>
                    <td>First name: </td>
                    <td><input required type="text" name="firstName" id="fname" value="" />
                        <div id="fnameMsg"></div>
                    </td>
                </tr>
                <tr>
                    <td>Last name: </td>
                    <td><input required type="text" name="lastName" id="lname" value="" />
                        <div id="lnameMsg"></div>
                    </td>
                </tr>
            </table>
            <div id="admin-buttons">
                <input type="submit" value="Register" />

                <a href="index.jsp"><input type="button" value="Home" name="submit"/> </a>
            </div>


    <%--     </form> --%>
    </div>
    <div id="admin-center" class="content">
        <div id="container-header"><span>Reassign</span></div>
        <table>
            <tr>
                <td>
                    Service Order:
                </td>
                <td>
                    <input type="text" name="blockAcc" value="" />
                </td>
            </tr>
            <tr>
                <td>
                    New Employee:
                </td>
                <td>
                    <input type="text" name="NewEmployee" value="" />
                </td>
            </tr>
            <tr>
                <td>
                    Reason:
                </td>
                <td>
                    <input type="text" name="reasonBlock" value="" style="width:200px;height:200px;" />
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Block" name="Block" />
                </td>
            </tr>
        </table>
    </div>

    <div id="admin-right" class="content">
        <div id="container-header"><span>Block Account</span>          </div>
        <table>
            <tr>
                <td>
                    Account login:
                </td>
                <td>
                    <input type="text" name="blockAcc" value="" />
                </td>
            </tr>
            <tr>
                <td>
                    Reason:
                </td>
                <td>
                    <input type="text" name="reasonBlock" value="" style="width:200px;height:200px;" />
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Block" name="Block" />
                </td>
            </tr>
        </table>
    </div>
</div>