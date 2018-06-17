$(document).ready(function () {

    var slider = document.getElementById('range-input-container');
    noUiSlider.create(slider, {

        range: {
            'min': 1,
            'max': 25
        },

        step: 1,

        start: [1],
        direction: 'ltr',
        orientation: 'horizontal',

        // Move handle on tap, bars are draggable
        behaviour: 'tap-drag',
        tooltips: true,
        format: wNumb({
            decimals: 0
        })
    });

    var inputFormat = document.getElementById('amount');

    slider.noUiSlider.on('update', function (values, handle) {
        inputFormat.value = values[handle];
    });

    $('select').formSelect();
    $('select').change(function () {
        slider.noUiSlider.updateOptions(
            {
                range: {
                    'min': 1,
                    'max': $(this).find(":selected").data('max')
                }
            },
            true
        );

    }).change();
});
