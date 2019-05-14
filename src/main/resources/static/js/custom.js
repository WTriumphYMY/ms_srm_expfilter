$(function(){
    var time = $('#time').val().substring(1,$('#time').val().length-1).split(",").map(Number);
    var rawP = $('#rawP').val().substring(1,$('#rawP').val().length-1).split(",").map(Number);
    var rawF = $('#rawF').val().substring(1,$('#rawF').val().length-1).split(",").map(Number);
    var smoothP = $('#smoothP').val().substring(1,$('#smoothP').val().length-1).split(",").map(Number);
    var smoothF = $('#smoothF').val().substring(1,$('#smoothF').val().length-1).split(",").map(Number);

    drawChart(time, rawP, '测量压强MPa', 'rawPressureFig');
    drawChart(time, smoothP, '滤波压强MPa', 'smoothPressureFig');
    drawChart(time, rawF, '测量推力kN', 'rawForceFig');
    drawChart(time, smoothF, '滤波推力kN', 'smoothForceFig');
})

function drawChart(xdata, ydata, yname, container){
    var chart = Highcharts.chart(container, {
        title: {
            text: '内弹道'
        },
        boost: {
            useGPUTranslations: true
        },
        subtitle: {
            text: '原始数据'
        },
        xAxis: {
            categories: xdata
        },
        yAxis: {
            title: {
                text: yname
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle'
        },

        series: [{
            name: yname,
            data: ydata
        }],
        responsive: {
            rules: [{
                condition: {
                    maxWidth: 500
                },
                chartOptions: {
                    legend: {
                        layout: 'horizontal',
                        align: 'center',
                        verticalAlign: 'bottom'
                    }
                }
            }]
        }
    });
}