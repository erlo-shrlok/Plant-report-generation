<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">菜单管理</h3>
            </div>
            <div class="box-body">
                <div class="clearfix">
                    <div class="col-md-4">
                        <a class="btn btn-sm btn-primary" target="modal" modal="lg"
                           href="/menu/add">添加</a>
                    </div>
                </div>
                <table id="menu_tab" class="table" style="margin-top: 20px">

                </table>
            </div>
        </div>
    </div>
</div>
<link rel="stylesheet" type="text/css" href="/other/jquery-easyui-1.5.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/other/jquery-easyui-1.5.3/themes/icon.css">
<script type="text/javascript" src="/other/jquery-easyui-1.5.3/js/jquery.easyui.min.js"></script>
<script type="text/javascript">
    <!--这段代码使用jQuery插件treerid来创建一个树形表格,并通过AJAX获取服务器端的数据进行展示-->
    $(function () {
        var table = $('#menu_tab').treegrid({
            height: 700,// 设置树形表格的高度为 700px
            rownumbers: true,// 显示行号
            animate: true,// 启用动画效果
            collapsible: true,// 使树形节点可折叠
            fitColumns: true,// 自适应列宽
            url: '/menu/getTreeGridMenu',// 向 '/menu/getTreeGridMenu' 发送 AJAX 请求获取树形表格的数据
            method: 'get',// 设置 AJAX 请求的方法为 GET
            idField: 'id',// 指定节点的 id 字段为 'id'
            treeField: 'name',// 指定节点的树形结构字段为 'name'
            showFooter: true,// 显示表格底部
            columns: [// 设置表格列信息，其中包括以下内容
                [{
                    title: '菜单名称',// 列标题
                    field: 'name',// 该列对应的数据字段
                    width: 80// 该列的宽度
                },
                    {
                        title: '请求URL',
                        field: 'url',
                        align: 'center',// 该列的对齐方式
                        width: 80
                    },
                    {
                        title: '菜单编码',
                        field: 'code',
                        align: 'center',
                        width: 80
                    },
                    {
                        title: '层级',
                        field: 'level',
                        align: 'center',
                        width: 40
                    },
                    {
                        title: '顺序',
                        field: 'sort',
                        align: 'center',
                        width: 40
                    },
                    {
                        title: '创建时间',
                        field: 'createDate',
                        align: 'center',
                        width: 80
                    },
                    {
                        title:'操作',
                        field:'menuId',
                        align: 'center',
                        width: 80,
                        <!--自定义该列的格式化方式，这里的 formatter 用于生成操作列的 HTML 内容。formatter 属性中的函数会在每个数据行上执行，该函数会根据当前行的数据生成一个包含编辑和删除链接的 HTML 内容，并将该内容返回，用于在操作列上显示-->
                        formatter:function(value){
                            console.log(value);
                            var  content="";
                            if (value != '000000000000000000'){
                                content = '<a class="btn btn-xs btn-info"  target="modal" modal="lg" href="/menu/edit/' + value + '">编辑</a>'
                                        + " &nbsp;"
                                        +'<a class="btn btn-xs btn-default" callback="reloadMenuList()" data-body="确认要删除吗？" target="ajaxTodo" href="/menu/delete/' + value + '">删除</a>'

                            }
                            return content;
                        }

                    }
                ]
            ]
        });
        <!--最后，table 变量表示创建的树形表格对象，可以通过该对象调用树形表格的方法，例如增加、删除节点等等。该代码在页面加载完成后执行，因此会自动创建树形表格并发送 AJAX 请求获取数据-->
    })
    function reloadMenuList(){
        $('#menu_tab').treegrid('reload');
    }

    /**
     * 该函数的作用是获取树形表格 #tg 中当前选中的行，并将该行数据以 JSON 格式打印到浏览器的控制台中
     */
    function doClick() {
        var row = $('#tg').treegrid('getSelected');
        console.log(JSON.stringify(row));
    }
</script>