<script type="text/javascript" src="assets/dashboard.js"></script>


<div id="content">
    <div id="IEtask">
        <div id ="tasks">
            <p>Your tasks</p>
            <select name="IEtasks">
                <option>1</option>
                <option>2</option>
            </select>
            <input type="submit" value="Choose" onclick="" />
        </div>
        <div id ="newTasks">
            <p>Choose Task</p>
            <select name="task">
                <option>1</option
            </select>
            <input type="submit" value="ChooseNewTask" onclick="showInstEngTask()" />
        </div>
    </div>

    <div id ="instEngContent">
        <p style="font-size:18px;font-weight:bold;">Create Router</p>
        <table>
            <tr>
                <td>
                    Input Port Quantity:
                </td>
                <td>
                    <input type="text" name="portQountity" value="" />
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Create Router" />
                </td>
            </tr>
        </table>
        <p style="font-size:18px;font-weight:bold;">Create Cable</p>
        <input type="submit" value="CreateCable" onclick="showCableText()"/> <br>
        <div id="cableText">Cable was created</div><br>
        <input type="submit" value="Connect Cable to Port" />
    </div>

    <div id ="anotherTask">
        <input type="submit" value="Choose Another Task" onclick="showInstAllTasks()"/>
    </div>
</div>
