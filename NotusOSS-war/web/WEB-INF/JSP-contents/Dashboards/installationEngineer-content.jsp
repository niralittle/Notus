<script type="text/javascript" src="assets/dashboard.js"></script>


<div id="content">
    <ul class="tabs">
        <li><a href="javascript:tabSwitch('tab_1', 'dash-left');" id="tab_1" class="active">Create Router</a></li>
        <li><a href="javascript:tabSwitch('tab_2', 'dash-center');" id="tab_2">Create Table</a></li>
        <li><a href="javascript:tabSwitch('tab_3', 'dash-right');" id="tab_3">Delete Table</a></li>
    </ul>
    <div id="dash-left" class="content">
        <div id="container-header"><span>Create Router</span>        </div>
        <script type="text/javascript" src="assets/registerValidator.js"></script>

            <table border="0" cellpadding="5">
                <tr>
                    <td>Router Name:</td>
                    <td><input type="text" name="routerName" value="" /></td>
                </tr>
                
                <tr>
                    <td>Port Quantity: </td>
                    <td><input type="text" name="portQuantity" value="" /></td>
                </tr>
            </table>
            <div id="dash-buttons">
                <input type="submit" value="Create" />
            </div>


    </div>
    <div id="dash-center" class="content">
        <div id="container-header"><span>Create Cable</span></div>
        <table>
            <tr>
                <td>
                    Cable Specification:
                </td>
                <td>
                    <select name="cableSpecification:">
                        <option>UTP</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Create" name="createCable" />
                </td>
            </tr>
        </table>
    </div>

    <div id="dash-right" class="content">
        <div id="container-header"><span>Delete Cable</span>          </div>
        <table>
            <tr>
                <td>
                    Cable Specification:
                </td>
                <td>
                    <input type="text" name="cableSpecification" value="" />
                </td>
            </tr>
            <tr>
                <td>
                    Reason:
                </td>
                <td>
                    <input type="text" name="reasonDelete" value="" style="width:200px;height:200px;" />
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
