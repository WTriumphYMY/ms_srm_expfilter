// var changep = document.getElementById("pChart");
// changep.style.display = "none";
// var changef = document.getElementById("fChart");
// changef.style.display = "none";

var changep = document.getElementById("pChart");
changep.disabled=true;
var changef = document.getElementById("fChart");
changef.disabled=true;



// change_cancel.style.display = "none";
$('#submitData').click(function(){
    // alert("drawChart按钮按下");
    // $('#exampleModal').modal('toggle');
    var pkId=$('#pkId').val();
    $.ajax({
        url:"/dataFilter",
        type:"post",
        dataType:"json",
        data:"pkId="+pkId,//传到后端
        async:false,
        cache:false,

        success:function(data){
            changef.disabled=false;
            changep.disabled=false;
            // changep.style.display = "block";
            alert("submitData执行");

            $('#pChart').click(function(){
                // alert("pChart执行");
                $('#exampleModal').modal('toggle');
                drawChart(data.t, data.p, "P/MPa");
            })
            $('#fChart').click(function(){
                // alert("fChart执行");
                $('#exampleModal').modal('toggle');
                drawChart(data.t, data.f, "F/kN");
            })



        },
        error:function () {
            alert("获取数据失败");
            changef.disabled=false;
            changep.disabled=false;
            // changep.style.display = "block";
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
            text: '滤波后数据'
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
