<div class="row">
    <div class="col-md-12">
        <form id="addForm">
            <div class="modal-body">
                <div class="form-group">
                    <label for="name" class="" id="nameLabel">项目名</label>
                    <input type="text" class="form-control" name="name" id="name" placeholder="输入项目名...">
                </div>
                <div class="form-group">
                    <label for="customer" class="" id="customerLabel">客户公司名</label>
                    <input type="text" class="form-control" name="customer" id="customer" placeholder="输入客户公司...">
                </div>
                <div class="form-group">
                    <label for="address" class="" id="addressLabel">客户地址</label>
                    <input type="text" class="form-control" name="address" id="address" placeholder="输入客户地址...">
                </div>
                <div class="form-group">
                    <label for="cname" class="" id="cnameLabel">客户名字</label>
                    <input type="text" class="form-control" name="cname" id="cname" placeholder="输入客户名字...">
                </div>
                <div class="form-group">
                    <label for="phone" class="" id="phoneLabel">客户手机号</label>
                    <input type="text" class="form-control" name="phone" id="phone" placeholder="输入客户手机号...">
                </div>
                <div class="form-group">
                    <label for="email" class="" id="emailLabel">客户邮箱</label>
                    <input type="text" class="form-control" name="email" id="email" placeholder="输入客户邮箱...">
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
<script type="text/javascript">
    function securitySave() {
            $.ajax({
                url: '/project/save',
                type: 'POST',
                dataType: 'JSON',
                data: $("#addForm").serialize(),
                success: function (data) {
                    if (data.status) {
                        $("#lgModal").modal('hide');
                        alertMsg("添加成功", "success");
                        window.location.reload()
                    } else {
                        alertMsg("添加失败:" + json.msg, "success");
                    }
                }
            });
    }

</script>