$('select').change(function () {
    $("#amount").attr({
        "max": $(this).find(":selected").data('max')
    }).val(1);
}).change();