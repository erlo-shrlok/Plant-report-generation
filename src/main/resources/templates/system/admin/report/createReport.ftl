<div class="row">
    <div class="col-md-12">
        <form id="securityEditForm" >
            <input type="hidden" id="id" name="id" value="3">
            <input type="hidden" id="rId" name="rId" value="${bean.rId}">
            <div class="box-body">
                <div class="form-group">
                    <label id="nameNoLabel">报告名</label>
                    <input type="text" class="form-control" name="name" id="name" value="${bean.name!}"
                           disabled placeholder="">
                </div>
            </div>
            <div class="box-footer">
                <div class="pull-right">
                    <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i
                                class="fa fa-close"></i>关闭
                    </button>
                    <button onclick="securityUpdateUser();" type="button" class="btn btn-primary btn-sm"><i
                                class="fa fa-paste"></i>确认生成
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    function securityUpdateUser() {
//        debugger;
        console.log($("#securityEditForm").serialize())
        $.ajax({
            url: '/report/create',
            type: 'post',
            dataType: 'text',
            data: $("#securityEditForm").serialize(),
            success: function (data) {
                var json = JSON.parse(data);
                if (json.status) {
                    $("#lgModal").modal('hide');
                    alertMsg("更新成功", "success");
                    reloadTable(list_ajax);
                } else {
                    alertMsg("更新失败:" + json.msg, "success");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    }

</script>