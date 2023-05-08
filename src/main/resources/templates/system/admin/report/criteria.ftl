<!DOCTYPE html>
<html>
<head>
    <title>评估指标</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<form id="reportForm">
    <input type="hidden" id="rId" name="rId" value="${rId}">
    <table>
        <thead>
        <tr>
            <th>指标编号</th>
            <th>安全大类</th>
            <th>安全子类</th>
            <th>指标描述</th>
            <th>描述</th>
            <th>符合情况</th>
        </tr>
        </thead>
        <tbody>
        <#list criteria as criterion>
            <tr>
                <td>${criterion.id}</td>
                <td>${criterion.cla}</td>
                <td>${criterion.type}</td>
                <td>${criterion.refer}</td>
                <td>
                    <!-- 显示关联的 ReportItem 的描述，并允许编辑 -->
                    <input type="text" name="post" data-id="${(criterion.reportItem.id)!''}" data-cid="${criterion.id}" value="${(criterion.reportItem.post)!''}" />
                </td>
                <td>
                    <!-- 显示关联的 ReportItem 的符合情况，并允许编辑 -->
                    <input type="text" name="kind" data-id="${(criterion.reportItem.id)!''}" data-cid="${criterion.id}" value="${(criterion.reportItem.kind)!''}" />
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
    <button type="button" class="btn btn-default btn-sm" id="submitReport">保存</button>
    <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i
                class="fa fa-close"></i>关闭
    </button>
    <script>
        $(document).ready(function() {
            $('#submitReport').click(function() {
                let reportItems = [];

                $('#reportForm input[name="post"]').each(function() {
                    let id = $(this).data('id');
                    let post = $(this).val();
                    let c_id  = $(this).data('cid');
                    let kind = $('input[name="kind"][data-cid="' + c_id + '"]').val();

                    // 检查用户是否填写了描述和符合情况
                    if (post.trim() === '' || kind.trim() === '') {
                        return;
                    }

                    reportItems.push({
                        id: id,
                        post: post,
                        kind: kind,
                        cId: parseInt(c_id.replace(/,/g, '')), // 移除逗号并将 c_id 转换为整数
                        rId: parseInt($('#rId').val()) // 添加 rId 到 reportItems
                    });
                });

                console.log(reportItems)

                $.ajax({
                    url: '/reportItem/addReportItems',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(reportItems),
                    success: function(response) {
                        alert('报告已保存');
                    },
                    error: function(xhr, status, error) {
                        alert('保存失败，请重试');
                    }
                });
            });
        });
    </script>
</form>
</body>
</html>

