import os
import cv2
import tensorflow as tf
from collections import Counter
from xml.etree import ElementTree
import numpy as np
from sklearn.metrics import recall_score

# 这个代码会在测试集上运行模型，然后计算并打印召回率。

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

# 加载模型和标签映射
model = tf.saved_model.load('D:/Users/Documents/PycharmProjects/tensorFlowTest/exportedmodel/saved_model')
label_map = parse_label_map('D:/Temp/data/label_map.pbtxt')

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
    return np.array(train_labels)  # Convert list to NumPy array

# 这个函数首先提取预测结果，然后过滤低于阈值的预测结果，统计各个类别的数量。
# 然后，它计算每个类别的召回率，并将这些召回率添加到一个列表中。最后，它计算总的召回率，这是所有类别的召回率的平均值。
#
# 注意，这个函数假设每个类别的目标数量是已知的，这是通过true_labels参数传入的。
# 如果你的标签数据不包含这个信息，你需要修改你的数据处理代码，使其能够提供这个信息。
# 这个函数会返回两个值：总体的召回率和一个字典，其中每个键是一个类别标签，每个值是对应类别的召回率。
# 遍历predictions列表中的每个元素（即每个预测结果），并对每个预测结果进行处理。它首先提取出每个预测结果中的'num_detections'，'detection_classes'，和'detection_scores'，然后计算出每个类别的真阳性和假阴性的数量，最后计算出召回率。
#
# 注意，这个函数假设y_test是一个一维的NumPy数组，其中每个元素都是一个类别标签。如果你的y_test的格式不同，你可能需要修改这个函数来适应你的数据。
def calculate_recall(predictions, y_test, label_map):
    total_true_positives = 0
    total_false_negatives = 0
    total_relevant_elements = 0
    recall_per_class = {}

    for prediction in predictions:
        num_detections = int(prediction['num_detections'].numpy())
        detection_classes = prediction['detection_classes'].numpy()[0][:num_detections]
        detection_scores = prediction['detection_scores'].numpy()[0][:num_detections]

        for label in label_map:
            true_positives = np.sum((detection_classes == label) & (detection_scores > 0.5))
            false_negatives = np.sum(y_test[:, label-1]) - true_positives
            total_true_positives += true_positives
            total_false_negatives += false_negatives
            total_relevant_elements += np.sum(y_test[:, label-1])

            # Calculate recall for this class and store it in the dictionary
            if np.sum(y_test[:, label-1]) > 0:
                recall_per_class[label] = true_positives / np.sum(y_test[:, label-1])

    # Calculate overall recall
    recall = total_true_positives / total_relevant_elements if total_relevant_elements > 0 else 0

    return recall, recall_per_class

x_test = load_train_images('D:/Temp/data/images2')
y_test = load_train_labels('D:/Temp/data/images2', label_map)

# 使用模型进行预测
# 这段代码会遍历测试集中的每个图片，将每个图片转换为TensorFlow张量，
# 然后使用模型进行预测。预测结果被添加到predictions列表中。
predictions = []
for image in x_test:
    input_tensor = tf.convert_to_tensor(image)
    input_tensor = input_tensor[tf.newaxis,...]
    output_dict = model(input_tensor)
    predictions.append(output_dict)

# 计算召回率
recall, recall_per_class = calculate_recall(predictions, y_test, label_map)

# 打印出每个类别的召回率和总体的召回率
print("Overall recall:", recall)
for label, recall in recall_per_class.items():
    print("Recall for class", label, ":", recall)

