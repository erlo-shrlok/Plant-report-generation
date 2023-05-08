<div class="row">
    <div class="col-md-12">
        <form id="securityEditForm">
            <input type="hidden" id="id" name="id" value="${bean.id}">
            <div class="box-body">
                <div class="form-group">
                    <label id="nameNoLabel">项目名</label>
                    <input type="text" class="form-control" name="name" id="name" value="${bean.name!}"
                           disabled placeholder="输入项目名...">
                </div>
                <div class="form-group">
                    <label id="customerLabel">客户公司名</label>
                    <input type="text" class="form-control" name="customer" id="customer" value="${bean.customer!}"
                           placeholder="输入客户公司名...">
                </div>
                <div class="form-group">
                    <label id="addressLabel">客户地址</label>
                    <input type="text" class="form-control" name="address" id="address" value="${bean.address!}"
                           placeholder="输入客户地址...">
                </div>
                <div class="form-group">
                    <label id="cnameLabel">客户名字</label>
                    <input type="text" class="form-control" name="cname" id="cname" value="${bean.cname!}"
                           placeholder="输入客户名字...">
                </div>
                <div class="form-group">
                    <label id="phoneLabel">客户手机号</label>
                    <input type="text" class="form-control" name="phone" id="phone" value="${bean.phone!}"
                           placeholder="输入客户手机号...">
                </div>
                <div class="form-group">
                    <label id="emailLabel">客户邮箱</label>
                    <input type="text" class="form-control" name="email" id="email" value="${bean.email!}"
                           placeholder="输入客户邮箱...">
                </div>
            </div>
            <div class="box-footer">
                <div class="pull-right">
                    <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i
                            class="fa fa-close"></i>关闭
                    </button>
                    <button onclick="securityUpdateUser();" type="button" class="btn btn-primary btn-sm"><i
                            class="fa fa-paste"></i>更新
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    function securityUpdateUser() {
//        debugger;
        $.ajax({
            url: '/project/update',
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