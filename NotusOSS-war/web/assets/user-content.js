function handleForms() {
    // if no jquery
    if (!window.$) { return; }

    $(function() {
        init();
    });
    
    function init() {
        $('form[action="DisconnectOrderProceed"]').each(function() {
            var $form = $(this);

            $form.on('submit', function() {
                if (!window.confirm('Are you sure you want to do this?')) {
                    // well, 'no' means no
                    return false;
                }
            });
        });
    }

}

handleForms();


