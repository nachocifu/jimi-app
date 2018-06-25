$(document).ready(function () {

    /**
     * Handle Pie Chart for table status
     *
     */
    Plotly.newPlot('table-status-pie',
        [
            {
                values: [tableStatusPie['free']['count'], tableStatusPie['paying']['count'], tableStatusPie['busy']['count']],
                labels: [tableStatusPie['free']['title'], tableStatusPie['paying']['title'], tableStatusPie['busy']['title']],
                type: 'pie',
                domain: {
                    x: [0, .48]
                },
                hole: .7
            }
        ],
        {
            title: 'Table Report Live', //TODO hardcoded text
            annotations: [
                {
                    font: {
                        size: 14
                    },
                    showarrow: false,
                    text: 'TABLES', //TODO hardcoed
                    x: 0.17,
                    y: 0.5
                }]
        }, {displayModeBar: false});

});