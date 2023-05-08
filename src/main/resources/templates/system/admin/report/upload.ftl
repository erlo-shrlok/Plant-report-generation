<!DOCTYPE html>
<html>
<head>
    <title>Upload Topology Image</title>
</head>
<body>
<h1>上传拓扑图</h1>
<form method="POST" action="/detect_assets" enctype="multipart/form-data">
    <input type="file" name="file" accept="image/*" />
    <br /><br />
    <input type="submit" value="上传" />
</form>
</body>
</html>
