import os
import cv2
import tensorflow as tf
from collections import Counter
from xml.etree import ElementTree
import numpy as np
import matplotlib.pyplot as plt

# 通过模型源代码训练模型，轮次训练，并统计每轮次的准确率

# 加载模型和标签映射
model = tf.saved_model.load('D:/Users/Documents/PycharmProjects/tensorFlowTest/exportedmodel/saved_model')

# 在这个函数中，predictions是模型的预测结果，true_labels是真实标签，label_map是类别名与id的映射关系。
# 这个函数首先提取预测结果，然后过滤低于阈值的预测结果，统计各个类别的数量，然后更新准确率计算器，最后返回准确率。
# 你可以在训练过程中的每个轮次结束后，使用这个函数来计算模型在测试集上的准确率。
# 然后，你可以记录下每个轮次的准确率，以便后续进行分析和可视化。
def calculate_accuracy(predictions, true_labels, label_map):
    # 初始化准确率计算器
    accuracy = tf.metrics.Accuracy()

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
    accuracy.update_state(true_labels, predicted_label)

    # 返回准确率
    return accuracy.result().numpy()

def load_train_images(train_images_dir):
    train_images = []
    for filename in sorted(os.listdir(train_images_dir)):
        if filename.endswith('.png'):
            image = cv2.imread(os.path.join(train_images_dir, filename))
            train_images.append(image)
    return train_images

def load_train_labels(train_labels_dir, label_map):
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

label_map = parse_label_map('D:/Temp/data/label_map.pbtxt')

x_train = load_train_images('D:/Temp/data/images2/train')
y_train = load_train_labels('D:/Temp/data/images2/train', label_map)

x_test = load_train_images('D:/Temp/data/images2')
y_test = load_train_labels('D:/Temp/data/images2', label_map)

class AccuracyCallback(tf.keras.callbacks.Callback):
    def on_train_begin(self, logs=None):
        self.accuracies = []

    def on_epoch_end(self, epoch, logs=None):
        # 使用模型进行预测
        predictions = model.predict(x_test)

        # 计算准确率
        accuracy = calculate_accuracy(predictions, y_test, label_map)

        # 将准确率添加到列表中
        self.accuracies.append(accuracy)

# 创建回调
accuracy_callback = AccuracyCallback()

# 训练模型
model.fit(x_train, y_train, epochs=10, callbacks=[accuracy_callback])

# 创建准确率随训练轮次变化的图表
plt.plot(accuracy_callback.accuracies)
plt.xlabel('Epoch')
plt.ylabel('Accuracy')
plt.show()
