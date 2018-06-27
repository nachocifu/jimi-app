$(document).ready(function() {
    new Chartist.Line('#chart-1', {
        labels: ['8','9','10','11','12','13','14','15','16','17','18','19','20','21','22'],
        series: [
            [12, 9, 7, 8, 5,5,5,5,6,8,22,3,4,123,134,44]
        ]
    }, {
        fullWidth: true,
        chartPadding: {
            right: 10
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
          right: 10
        }
      });

});