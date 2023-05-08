import tensorflow as tf
import numpy as np
import cv2
from collections import Counter

# 加载已保存的模型
model = tf.saved_model.load('D:/Users/Documents/PycharmProjects/tensorFlowTest/exportedmodel/saved_model')

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

label_map_path = 'D:/Temp/data/label_map.pbtxt'
label_map = parse_label_map(label_map_path)

# 读取图片
image_path = 'D:/Temp/data/images/image11.png'
image = cv2.imread(image_path)
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

# 输出结果
print("检测到的资产类别及其数量：")
for class_id, count in class_count.items():
    class_name = label_map[class_id]
    print(f"{class_name}: {count}")
