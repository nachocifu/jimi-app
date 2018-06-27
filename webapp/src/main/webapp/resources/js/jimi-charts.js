$(document).ready(function() {
      new Chartist.Line('#chart-1', {
        labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'],
        series: [
          [12, 9, 7, 8, 5],
        ]
      }, {
        fullWidth: true,
        chartPadding: {
          right: 40
        }
      });

      new Chartist.Line('#chart-2', {
        labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'],
        series: [
          [12, 9, 7, 8, 5],
        ]
      }, {
        fullWidth: true,
        chartPadding: {
          right: 40
        }
      });

      new Chartist.Line('#chart-3', {
        labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'],
        series: [
          [12, 9, 7, 8, 5],
        ]
      }, {
        fullWidth: true,
        chartPadding: {
          right: 40
        }
      });

});