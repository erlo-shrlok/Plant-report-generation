import os
import cv2
import tensorflow as tf
from collections import Counter
from xml.etree import ElementTree
import numpy as np

# 计算模型的准确率

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

# 从指定的目录中加载所有的测试图像
# 这个函数的目的是将你的图像文件转换为一个可以直接输入到你的模型中的格式
def load_test_images(images_dir):
    images = []
    for filename in sorted(os.listdir(images_dir)):
        if filename.endswith('.png'):
            # 使用cv2.imread函数读取图像文件。cv2.imread函数接受一个文件路径，然后返回一个表示图像的NumPy数组。
            image = cv2.imread(os.path.join(images_dir, filename))
            images.append(image)
    return images

# 将你的标注文件转换为一个与模型预测结果相同的格式，这样你就可以直接比较它们来计算准确率
# def load_test_labels(images_dir, label_map):
#     labels = []
#     for filename in sorted(os.listdir(images_dir)):
#         if filename.endswith('.xml'):
#             tree = ElementTree.parse(os.path.join(images_dir, filename))
#             root = tree.getroot()
#             label = [0] * len(label_map)
#             for obj in root.findall('object'):
#                 class_name = obj.find('name').text
#                 class_id = list(label_map.keys())[list(label_map.values()).index(class_name)]
#                 label[class_id - 1] = 1
#             labels.append(label)
#     return labels

def load_test_labels(train_labels_dir, label_map):
    train_labels = []
    for filename in sorted(os.listdir(train_labels_dir)):
        if filename.endswith('.xml'):
            tree = ElementTree.parse(os.path.join(train_labels_dir, filename))
            root = tree.getroot()
            label = [0] * len(label_map)
            for obj in root.findall('object'):
                class_name = obj.find('name').text
                if class_name in label_map.values():
                    class_id = list(label_map.keys())[list(label_map.values()).index(class_name)]
                    label[class_id - 1] += 1  # Increment the count for this class
            train_labels.append(label)
    return train_labels

# 加载模型和标签映射
model = tf.saved_model.load('D:/Users/Documents/PycharmProjects/tensorFlowTest/exportedmodel/saved_model')
label_map = parse_label_map('D:/Temp/data/label_map.pbtxt')

# 读取测试数据和标注
test_images = load_test_images('D:/Temp/data/images')
test_labels = load_test_labels('D:/Temp/data/images', label_map)

# 初始化准确率计算器
accuracy = tf.metrics.Accuracy()

# 这个函数将接收一个OpenCV图像，然后返回一个可以直接输入到你的模型中的张量
def preprocess_image(image):
    # 将图像转换为RGB格式
    image_rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)

    # 将图像数据转换为一个TensorFlow张量
    input_tensor = tf.convert_to_tensor(image_rgb)

    return input_tensor


# 对每个测试样本进行预测，并计算准确率
for image, true_label in zip(test_images, test_labels):
    # 预处理图像
    image = preprocess_image(image)
    input_tensor = tf.convert_to_tensor(image)
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
    predicted_label = [0] * len(label_map)
    for class_id in detected_classes:
        predicted_label[class_id - 1] += 1

    # 更新准确率计算器
    accuracy.update_state(true_label, predicted_label)

# 打印最终的准确率
print('Accuracy:', accuracy.result().numpy())
