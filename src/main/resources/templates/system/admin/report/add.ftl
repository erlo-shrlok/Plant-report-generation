<div class="row">
    <div class="col-md-12">
        <form id="securityAddForm">
            <div class="modal-body">
                <div class="form-group">
                    <label class="" id="nameLabel">报告名称</label>
                    <input type="text" class="form-control" name="name" id="name" placeholder="输入报告名称...">
                </div>
                <div class="form-group">
                    <label class="" id="projectLabel">所属项目</label>
                    <select class="form-control" name="pId" id="pId">
                        <option value="" disabled selected>选择所属项目</option>
                    </select>
                </div>
                <div class="form-group">
                    <label class="" id="levelLabel">等级</label>
                    <input type="text" class="form-control" name="level" id="level" placeholder="输入系统等级...">
                </div>
                <div class="form-group">
                    <label class="" id="addressLabel">备注</label>
                    <input type="text" class="form-control" name="remark" id="remark" placeholder="输入系统备注...">
                </div>
            </div>
        </form>
    </div>
</div>
<div class="modal-footer">
    <div class="pull-right">
        <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i class="fa fa-close"></i>关闭</button>
        <button type="button" class="btn btn-primary btn-sm" onclick="securitySave();"><i class="fa fa-save"></i>保存
        </button>
    </div>
</div>
<script type="text/javascript" src="other/zTree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="other/zTree/js/jquery.ztree.excheck.js"></script>
<link rel="stylesheet" type="text/css" href="other/zTree/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript">
    $(document).ready(function () {
        // 请求 “/project/getIdAndName" 接口来获取所有项目，并将它们添加到下拉列表中
        $.ajax({
            url:"/project/getIdAndName",
            type:"GET",
            dataType: "json",
            success: function (data){
                if(data){
                    let projectSelect = $("#pId");
                    data.forEach(function (project) {
                        projectSelect.append($('<option>',{
                            value: project.id,
                            text: project.name
                        }));
                    });
                }
            }
        });
    });
    function securitySave() {
        $("span").remove(".errorClass");
        $("br").remove(".errorClass");
        var status = 1;
        if ($("#name").val() == "") {
            $("#nameLabel").prepend('<span class="errorClass" style="color:red">*菜单不能为空</span><br class="errorClass"/>');
            status = 0;
        }
        if ($("#url").val() == "") {
            $("#urlLabel").prepend('<span class="errorClass" style="color:red">*请求地址不能为空</span><br class="errorClass"/>');
            status = 0;
        }
        if($("#code").val() == ""){
            $("#codeLabel").prepend('<span class="errorClass" style="color:red">*菜单编号不能为空</span><br class="errorClass"/>');
            status = 0;
        }
        if($("#sort").val() == ""){
            $("#sortNameLabel").prepend('<span class="errorClass" style="color:red">*顺序编号不能为空</span><br class="errorClass"/>');
            status = 0;
        }
        if (status == 0) {
            return false;
        } else {
            $.ajax({
                url: '/report/save',
                type: 'post',
                dataType: 'text',
                data: $("#securityAddForm").serialize(),
                success: function (data) {
                    var json = JSON.parse(data);
                    if (json.status) {
                        $("#lgModal").modal('hide');
                        alertMsg("添加成功", "success");
                        window.location.reload();
                    } else {
                        alertMsg("添加失败:" + json.msg, "success");
                    }
                }
            });
        }
    }
    var setting = {
        check: {
            enable: true,
            chkStyle: "radio",
            radioType: "all"
        },
        view: {
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick,
            onCheck: onCheck
        }
    };

    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        hideMenu();
        return false;
    }

    function onCheck(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                nodes = zTree.getCheckedNodes(true);
        v = "";
        var id = '';
        var code = '';
        var lev=0;
        for (var i = 0, l = nodes.length; i < l; i++) {
            v += nodes[i].name + ",";
            id = nodes[i].id;
            code = nodes[i].code;
            lev = nodes[i].level;
        }
        if (v.length > 0) v = v.substring(0, v.length - 1);
        var pName = $("#pName");
        pName.attr("value", v);
        var pId = $("#pId");
        var pCode = $("#pCode");
        var level = $("#level");
        pId.attr("value", id);
        pCode.attr("value", code);
        level.attr("value", lev+1);
        hideMenu();
    }
    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }
    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "pName" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
            hideMenu();
        }
    }
    function initZTree(zNodes){
//        alert("node=" + zNodes);
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    };
</script>