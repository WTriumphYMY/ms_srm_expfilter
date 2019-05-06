$('#pressureChart').click(function(){
   alert("drawChart按钮按下");
   // $('#exampleModal').modal('toggle');
    var pkId=$('#pkId').val();
    $.ajax({
        url:"/getChartData",
        type:"post",
        dataType:"json",
        data:"pkId="+pkId,//传到后端
        async:false,
        cache:false,

        success:function(data){
            // alert("drawChart执行");
             $('#exampleModal').modal('toggle');
            // drawChart(data.t, data.p, "p");
            var chart = Highcharts.chart('container', {
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
                    categories: data.t
                },
                yAxis: {
                    title: {
                        text: "P/MPa"
                    }
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle'
                },

                series: [{
                    name: "P",
                    data: data.p
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
        },
        error:function () {
            alert("获取数据失败");
        }

    });
})

$('#forceChart').click(function(){
    // alert("drawChart按钮按下");
    // $('#exampleModal').modal('toggle');
    var pkId=$('#pkId').val();
    $.ajax({
        url:"/getChartData",
        type:"post",
        dataType:"json",
        data:"pkId="+pkId,//传到后端
        async:false,
        cache:false,
        success:function(data){
            // alert("drawChart执行");
            $('#exampleModal').modal('toggle');
            var chart = Highcharts.chart('container', {
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
                    categories: data.t
                },
                yAxis: {
                    title: {
                        text: "F/kN"
                    }
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle'
                },

                series: [{
                    name: "f",
                    data: data.f
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
        },
        error:function () {
            alert("获取数据失败");
        }

    });
})

function drawChart(xdata, ydata, yname){
    var chart = Highcharts.chart('container', {
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
