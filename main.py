import os
import cv2
import numpy as np
import tensorflow as tf
from object_detection.utils import label_map_util
from object_detection.utils import visualization_utils as viz_utils

# 目标检测，输出图片

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

PATH_TO_MODEL_DIR = 'D:/Users/Documents/PycharmProjects/tensorFlowTest/exportedmodel/saved_model'
PATH_TO_LABELS = 'D:/Temp/data/label_map.pbtxt'
IMAGE_PATH = 'D:/Temp/data/images/image8.png'

detect_fn = tf.saved_model.load(PATH_TO_MODEL_DIR)

category_index = label_map_util.create_category_index_from_labelmap(PATH_TO_LABELS,
                                                                    use_display_name=True)

image_np = cv2.imread(IMAGE_PATH)
image_rgb = cv2.cvtColor(image_np, cv2.COLOR_BGR2RGB)

image_tensor = tf.convert_to_tensor(image_rgb)
image_tensor = image_tensor[tf.newaxis, ...]

detections = detect_fn(image_tensor)

num_detections = int(detections.pop('num_detections'))
detections = {key: value[0, :num_detections].numpy()
              for key, value in detections.items()}
detections['num_detections'] = num_detections

# 检测结果中的所有类型都应为 int32
detections['detection_classes'] = detections['detection_classes'].astype(np.int64)

label_id_offset = 1
image_np_with_detections = image_np.copy()

viz_utils.visualize_boxes_and_labels_on_image_array(
            image_np_with_detections,
            detections['detection_boxes'],
            detections['detection_classes'] + label_id_offset,
            detections['detection_scores'],
            category_index,
            use_normalized_coordinates=True,
            max_boxes_to_draw=200,
            min_score_thresh=.30,
            agnostic_mode=False)

cv2.imshow('Object Detection', cv2.cvtColor(image_np_with_detections, cv2.COLOR_RGB2BGR))
cv2.waitKey(0)
cv2.destroyAllWindows()
