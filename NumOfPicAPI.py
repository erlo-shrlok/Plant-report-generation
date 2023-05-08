import os
from flask import Flask, request
from flask_restful import Resource, Api
import base64
import cv2
import numpy as np
from collections import Counter
import tensorflow as tf

# 此处代码应当包括 parse_label_map 函数和检测模型的加载
# ...
# label_map = parse_label_map(label_map_path)
# model = tf.saved_model.load(model_dir)

# 从label_map文件中解析类别名与id的映射关系
def parse_label_map(label_map_path):
    label_map = {}
    with open(label_map_path, 'r') as f:
        lines = f.readlines()
        for i in range(0, len(lines), 5):
            class_id = int(lines[i + 1].strip().split(': ')[1])
            class_name = lines[i + 2].strip().split(': ')[1].strip("'")
            label_map[class_id] = class_name
    return label_map

# 加载已保存的模型
model = tf.saved_model.load('D:/Users/Documents/PycharmProjects/tensorFlowTest/exportedmodel/saved_model')

label_map_path = 'D:/Temp/data/label_map.pbtxt'

label_map = parse_label_map(label_map_path)

app = Flask(__name__)
api = Api(app)

class AssetDetection(Resource):
    def post(self):
        # 从请求中读取图片（BASE64 编码）
        image_data = request.form['image']
        image_data = base64.b64decode(image_data)

        # 将图片数据转换为 OpenCV 图像格式
        image_np = np.frombuffer(image_data, dtype=np.uint8)
        image = cv2.imdecode(image_np, flags=cv2.IMREAD_COLOR)
        image_rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
        input_tensor = tf.convert_to_tensor(image_rgb)
        input_tensor = input_tensor[tf.newaxis, ...]

        # 使用模型进行预测
        predictions = model(input_tensor)

        # 提取预测结果
        num_detections = int(predictions.pop('num_detections'))
        detection_scores = predictions['detection_scores'][0][:num_detections].numpy()
        detection_classes = predictions['detection_classes'][0][:num_detections].numpy().astype(np.int64)

        # 设置检测阈值，例如 0.5
        threshold = 0.5

        # 过滤低于阈值的预测结果
        detected_classes = detection_classes[detection_scores >= threshold]

        # 统计各个类别的数量
        class_count = Counter(detected_classes)

        # 构建返回的 JSON 对象
        response = {label_map[class_id]: count for class_id, count in class_count.items()}

        return response

api.add_resource(AssetDetection, '/detect_assets')

if __name__ == '__main__':
    app.run(debug=True)
