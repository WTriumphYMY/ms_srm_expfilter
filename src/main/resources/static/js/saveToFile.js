$('#toFile').click(function(){

    var pkId=$('#pkId').val();
    var  path=$('#path').val();
    $.ajax({
        url:"/saveAsFile",
        type:"post",
        dataType:"json",
        // data:'path='+path+'&pkId='+pkId,
        data:  {path:path, pkId:pkId},//传到后端
        async:false,
        // cache:false,

        success:function(data){
            //滤波后的曲线

            alert("'已存到'+data");

        },
        error:function () {
            alert("获取数据失败");
        }

    });
})