<div id="content">
    <div id="admin-left">
        <div id="container-header"><span>Create Employee Account</span></div>
        <script type="text/javascript" src="assets/registerValidator.js"></script>

        <form name="Register" method="post" onsubmit="return validate()" action="Register">
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

            <input type="submit" value="Register" />


        </form>


    </div>
    <div id="admin-center">
        <div id="container-header"><span>Reassign Task</span></div>
        <form name="reassign" action="ReassignTask" method="POST">
            <table cellpadding="5">
                <tr>
                    <td>Task ID:</td>
                    <td><input type="text" name="taskId" value="" /></td>
                </tr>
                <tr>
                    <td>New Employee for task:</td>
                    <td><input type="text" name="taskEmployeeId" value="" /></td>
                </tr>
                <tr>
                    <td>Reason:</td>
                    <td><input type="text" name="reason" value="" style="width:200px; height:50px;"/></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Appoint" /></td>
                </tr>
            </table>
        </form>

    </div>
    <div id="admin-right">
        <div id="container-header"><span>Block Account</span></div>
        <table cellpadding="5">
            <tr>
                <td>Account Login:</td>
                <td><input type="text" name="accountId" value="" /></td>
            </tr>            
            <tr>
                <td>Reason:</td>
                <td><input type="text" name="reason" value="" style="width:200px; height:50px;"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Block" /></td>
            </tr>
        </table>
        <form name="Block" action="BlockAcc" method="POST">
        </form>

    </div>
</div>
