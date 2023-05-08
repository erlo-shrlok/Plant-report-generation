<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>指标列表</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        .hidden {
            display: none;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="mt-4 mb-4">指标列表</h1>
    <button type="button" id="uploadButton">导入指标</button>
    <input type="file" id="fileInput" class="hidden" accept=".xlsx, .xls">
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>类型</th>
            <th>参考</th>
            <th>分类</th>
        </tr>
        </thead>
        <tbody>
        <#list criterion as c>
            <tr>
                <td>${c.id}</td>
                <td>${c.type}</td>
                <td>${c.refer}</td>
                <td>${c.cla}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>

<script>
    document.getElementById("uploadButton").addEventListener("click", function() {
        if (confirm("确定吗？导入指标会导致之前的指标被删除")) {
            document.getElementById("fileInput").click();
        }
    });

    document.getElementById("fileInput").addEventListener("change", function() {
        let fileInput = document.getElementById("fileInput");
        if (fileInput.files.length === 0) {
            return;
        }

        let formData = new FormData();
        formData.append("file", fileInput.files[0]);

        let request = new XMLHttpRequest();

        request.onreadystatechange = function() {
            if (request.readyState === 4) {
                if (request.status === 200 && request.responseText === "success") {
                    alert("指标导入成功");
                    location.reload();
                } else {
                    alert("指标导入失败，请重试");
                }
            }
        };

        request.open("POST", "/criterion/uploadExcel2");
        request.send(formData);
    });
</script>
</body>
</html>
