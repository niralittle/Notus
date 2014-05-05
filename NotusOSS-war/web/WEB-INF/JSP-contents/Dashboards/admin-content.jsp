<script type="text/javascript" src="assets/dashboard.js"></script>



    <ul class="tabs">
        <li><a href="javascript:tabSwitch('tab_1', 'dash-left');" id="tab_1" class="active">Create Employee Account</a></li>
        <li><a href="javascript:tabSwitch('tab_2', 'dash-center');" id="tab_2">Reassign task</a></li>
        <li><a href="javascript:tabSwitch('tab_3', 'dash-right');" id="tab_3">Block Account</a></li>
        <li><a href="javascript:tabSwitch('tab_4', 'dash-report');" id="tab_4">Reports</a></li>
    </ul>
    <div id="dash-left" class="content">
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
            <tr>
                <td colspan="2">
                    <input id="dash-buttons" type="submit" value="Register" />
                </td>
            </tr>
        </table>
            



        <%--     </form> --%>
    </div>
    <div id="dash-center" class="content">
        <div id="container-header"><span>Reassign</span></div>
        <table>
            <tr>
                <td>
                    Service Order Location:
                </td>
                <td>
                    <select name="serviceOrderLocation"></select>
                </td>
            </tr>
            <tr>
                <td>
                    Service Order:
                </td>
                <td>
                    <select name="serviceOrder"></select>
                </td>
            </tr>
            <tr>
                <td>
                    New Employee:
                </td>
                <td>
                    <select name="newEmployee"></select>
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
                <td colspan="2">
                    <input type="submit" value="Reassign" name="Reassign" />
                </td>
            </tr>
        </table>
    </div>

    <div id="dash-right" class="content">
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
                <td colspan="2">
                    <input type="submit" value="Block" name="Block" />
                </td>
            </tr>
        </table>
    </div>
    <div id="dash-report" class="content">
        <div id="container-header"><span>Reports</span> </div>
        <script type="text/javascript" src="assets/date.js"></script>
        <form method="post" id="admin-report" action="getReport" target="_blank" onsubmit="return continueOrNot()">
            <input type="radio" name="report" value="0" onselect="disableDate()" checked/>
            Most profitable router<br>
            <input type="radio" name="report" value="3" onselect="disableDate()"/>
            Routers utilization and capacity<br>
            <hr>
            <input type="radio" name="report" value="1" onselect="enableDateRange()"/>
            New orders per period<br>
            <input type="radio" name="report" value="2" onselect="enableDateRange()"/>
            Disconnect orders per period<br>
            <br>
            From:
            <SELECT id="dayFrom">
                <SCRIPT LANGUAGE="JavaScript">
                    displayDay();
                </SCRIPT>
            </SELECT>
            <SELECT id="monthFrom" onChange="checkYear(this.form.dayFrom, this.form.monthFrom, this.form.yearFrom)">
                <SCRIPT LANGUAGE="JavaScript">
                    displayMonth();
                </SCRIPT>
            </SELECT>
            <SELECT id="yearFrom" onChange="checkYear(this.form.dayFrom, this.form.monthFrom, this.form.yearFrom)">
                <SCRIPT LANGUAGE="JavaScript">
                    displayYearVariable(5, 5);
                </SCRIPT>
            </SELECT>
            To:
            <SELECT id="dayTo">
                <SCRIPT LANGUAGE="JavaScript">
                    displayDay();
                </SCRIPT>
            </SELECT>
            <SELECT id="monthTo" onChange="checkYear(this.form.dayTo, this.form.monthTo, this.form.yearTo)">
                <SCRIPT LANGUAGE="JavaScript">
                    displayMonth();
                </SCRIPT>
            </SELECT>
            <SELECT id="yearTo" onChange="checkYear(this.form.dayTo, this.form.monthTo, this.form.yearTo)">
                <SCRIPT LANGUAGE="JavaScript">
                    displayYearVariable(5, 5);
                </SCRIPT>
            </SELECT>
            <hr>
            <%--<input type="radio" name="report" value="4" onselect="enableMonthSelect()"/>
            <select id="month">
                <script>
                    displayMonth();
                </script>
            </select>
            <select id="year">
                <script>
                    displayYearVariable(5, 5);
                </script>
            </select>
            Profitability by month<br>--%>
            <input type="submit" value="View report" id="viewreport"/><br>
            <input type="hidden" name="fromdate" id="fromdate"/>
            <input type="hidden" name="todate" id="todate"/>
            <input type="hidden" name="bymonth" id="bymonth"/>
            <div id="tip" style="display:none"></div>
        </form>
    </div>
