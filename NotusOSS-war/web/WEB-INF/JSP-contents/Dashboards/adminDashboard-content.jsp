<script type="text/javascript" src="assets/date.js"></script>
        <form method="post" action="getReport" target="_blank" onsubmit="return continueOrNot()">
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

</body>
</html>
