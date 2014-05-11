<script type="text/javascript" src="assets/date.js"></script>

<form method="post" action="getReport" target="_blank" onsubmit="return continueOrNot()">
    <input type="radio" name="report" value="0" onclick="disableDate()"  checked/>
        Most profitable router<br>
    <input type="radio" name="report" value="3" onclick="disableDate()"/>
        Routers utilization and capacity<br>
    <input type="radio" name="report" value="4" onclick="disableDate()"/>
        Profitability by month<br>
    <hr>
    <input type="radio" name="report" value="1" onclick="enableDate()"/>
        New orders per period<br>
    <input type="radio" name="report" value="2" onclick="enableDate()"/>
        Disconnect orders per period<br>
    <br>
    From:
    <SELECT id="dayFrom" disabled>
        <SCRIPT LANGUAGE="JavaScript">
            displayDay();
        </SCRIPT>
    </SELECT>
    <SELECT id="monthFrom"  disabled onChange="checkYear(this.form.dayFrom,
        this.form.monthFrom, this.form.yearFrom)">
        <SCRIPT LANGUAGE="JavaScript">
            displayMonth();
        </SCRIPT>
    </SELECT>
    <SELECT id="yearFrom"  disabled onChange="checkYear(this.form.dayFrom,
        this.form.monthFrom, this.form.yearFrom)">
        <SCRIPT LANGUAGE="JavaScript">
            displayYearVariable(5, 5);
        </SCRIPT>
    </SELECT>
    To:
    <SELECT id="dayTo" disabled>
        <SCRIPT LANGUAGE="JavaScript">
            displayDay();
        </SCRIPT>
    </SELECT>
    <SELECT id="monthTo" disabled onChange="checkYear(this.form.dayTo,
        this.form.monthTo, this.form.yearTo)">
        <SCRIPT LANGUAGE="JavaScript">
            displayMonth();
        </SCRIPT>
    </SELECT>
    <SELECT id="yearTo" disabled onChange="checkYear(this.form.dayTo,
        this.form.monthTo, this.form.yearTo)">
        <SCRIPT LANGUAGE="JavaScript">
            displayYearVariable(5, 5);
        </SCRIPT>
    </SELECT>
    <hr>
    <input type="submit" value="View report" id="viewreport"/><br>
    <input type="hidden" name="fromdate" id="fromdate"/>
    <input type="hidden" name="todate" id="todate"/>
    <input type="hidden" name="bymonth" id="bymonth"/>
    <div id="tip" style="display:none"></div>
</form>
 