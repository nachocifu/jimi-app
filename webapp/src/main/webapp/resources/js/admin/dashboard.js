$(document).ready(function () {

    /**
     * Handle Pie Chart for table status
     *
     */
    var data = [{
        values: [tableStatusPie['free']['count'], tableStatusPie['paying']['count'], tableStatusPie['busy']['count']],
        labels: [tableStatusPie['free']['title'], tableStatusPie['paying']['title'], tableStatusPie['busy']['title']],
        type: 'pie'
    }];

    var layout = {
        title: tableStatusPie['plotTitle']
    };

    Plotly.newPlot('table-status-pie', data, layout, {displayModeBar: false});

    var data_2 = [monthlyOrderTotalTimeSeries['values']];

    var layout_2 = {
        title: monthlyOrderTotalTimeSeries['plotTitle'],
    };

    Plotly.newPlot('monthly-order-total-time-series', data_2, layout_2, {displayModeBar: false});

});