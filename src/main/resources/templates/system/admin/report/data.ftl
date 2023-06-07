<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Asset Detection Result</title>
    <style>
        .center {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="center">
    <h1>拓扑图上设备的统计结果:</h1>
    <table>
        <thead>
        <tr>
            <th>设备</th>
            <th>数量</th>
        </tr>
        </thead>
        <tbody>
        <#list resultMap?keys as key>
            <tr>
                <td>${key}</td>
                <td>${resultMap[key]}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
</body>
</html>
