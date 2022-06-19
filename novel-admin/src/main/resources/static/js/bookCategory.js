const app = new Vue({
    el: '#app',
    data: {
      pageInfo:{
          total: "",
          list:null,
          pageNum:"",
          pageSize:"",
          size:""
      }

    }
});
layui.use('laypage', function () {
    var laypage = layui.laypage;
    laypage.render({
        elem: 'pagination'
        , count: totalCount
        , limits: [5, 10, 15, 20, 50] //设置条数可以选择显示多少条
        , layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
        , jump: function (obj, first) {
            //点击非第一页页码时的处理逻辑。比如这里调用了ajax方法，异步获取分页数据
            if (!first) {
                pagination(obj.curr, obj.limit);//第二个参数不能用变量pageSize，因为当切换每页大小的时候会出问题
            }
        }
    });
});

var pageIndex = 1;
var pageSize = 10;
var totalCount = 0;
pagination(pageIndex, pageSize);

function pagination(pageIndex, pageSize) {
    //查询条件
    var param = {
        page: pageIndex,
        rows: pageSize,
        //其它查询条件可在下面添加
    };
    //后台数据接口
    $.ajax({
        type: 'GET',
        url: '/bookCategory/list',
        // dataType: 'json',
        data: param,
        async: false,//这里设置为同步执行，目的是等数据加载完再调用layui分页组件，不然分页组件拿不到totalCount的值
        success: function (result) {
            console.log(result);
            app.pageInfo= result.data;


        }
    });
};