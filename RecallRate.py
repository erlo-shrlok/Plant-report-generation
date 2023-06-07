import os
import cv2
import tensorflow as tf
from collections import Counter
from xml.etree import ElementTree
import numpy as np
import matplotlib.pyplot as plt
from sklearn.metrics import recall_score

# 统计模型召回率
# 这个代码会在每个训练轮次结束后计算模型在测试集上的召回率，并将召回率添加到一个列表中。
# 然后，它会创建一个图表，显示召回率随训练轮次的变化。
#
# 注意，这个代码使用了sklearn.metrics.recall_score函数来计算召回率，
# 这个函数需要真实标签和预测标签都是二进制形式的。如果你的标签不是这种形式，你可能需要修改calculate_recall函数来适应你的数据。

# 加载模型和标签映射
model = tf.saved_model.load('D:/Users/Documents/PycharmProjects/tensorFlowTest/exportedmodel/saved_model')

def calculate_recall(predictions, true_labels, label_map):
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

    # 计算召回率
    recall = recall_score(true_labels, predicted_label, average='micro')

    # 返回召回率
    return recall

class RecallCallback(tf.keras.callbacks.Callback):
    def on_train_begin(self, logs=None):
        self.recalls = []

    def on_epoch_end(self, epoch, logs=None):
        # 使用模型进行预测
        predictions = model.predict(x_test)

        # 计算召回率
        recall = calculate_recall(predictions, y_test, label_map)

        # 将召回率添加到列表中
        self.recalls.append(recall)

# 创建回调
recall_callback = RecallCallback()

# 训练模型
model.fit(x_train, y_train, epochs=10, callbacks=[recall_callback])

# 创建召回率随训练轮次变化的图表
plt.plot(recall_callback.recalls)
plt.xlabel('Epoch')
plt.ylabel('Recall')
plt.show()
