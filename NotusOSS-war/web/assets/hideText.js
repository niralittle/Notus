 var show;
    function hidetxt(type) {
        //clean all fields if chosen other criteria to searching
        document.getElementById("get_users").reset();

        param = document.getElementById(type);
        if (param.style.display == "none") {
            if (show) {
                show.style.display = "none";
            }
            param.style.display = "block";
            show = param;
        } else
            param.style.display = "none";
    }