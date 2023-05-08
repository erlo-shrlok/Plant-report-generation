import os
import io
import pandas as pd
import tensorflow as tf
import argparse
import re
from PIL import Image
from object_detection.utils import dataset_util
from collections import namedtuple

# 通过标签映射文件来构建映射：即根据标签映射文件创建一个字典
def load_label_map(label_map_path):
    with open(label_map_path, 'r') as f:
        label_map_data = f.read()

    label_map = {}
    for item in re.finditer(r'item\s*{([^}]+)}', label_map_data, re.MULTILINE):
        item_data = item.group(1)
        item_id = int(re.search(r'id:\s*(\d+)', item_data).group(1))
        item_name = re.search(r'name:\s*\'([^\']+)', item_data).group(1)
        label_map[item_name] = item_id

    return label_map

LABEL_MAP_PATH = 'D:/Temp/data/label_map.pbtxt'  # 修改为您的label_map.pbtxt文件路径
LABEL_DICT = load_label_map(LABEL_MAP_PATH)


def class_text_to_int(row_label):
    if row_label == 'cat':
        return 1
    elif row_label == 'dog':
        return 2
    else:
        return None

def split(df, group):
    data = namedtuple('data', ['filename', 'object'])
    gb = df.groupby(group)
    return [data(filename, gb.get_group(x)) for filename, x in zip(gb.groups.keys(), gb.groups)]

def create_tf_example(group, path):
    with tf.io.gfile.GFile(os.path.join(path, '{}'.format(group.filename)), 'rb') as fid:
        encoded_jpg = fid.read()
    encoded_jpg_io = io.BytesIO(encoded_jpg)
    image = Image.open(encoded_jpg_io)
    width, height = image.size

    filename = group.filename.encode('utf8')
    image_format = b'jpg'
    xmins = []
    xmaxs = []
    ymins = []
    ymaxs = []
    classes_text = []
    classes = []

    for index, row in group.object.iterrows():
        xmins.append(row['xmin'] / width)
        xmaxs.append(row['xmax'] / width)
        ymins.append(row['ymin'] / height)
        ymaxs.append(row['ymax'] / height)
        classes_text.append(row['class'].encode('utf8'))
        classes.append(LABEL_DICT[row['class']])

    tf_example = tf.train.Example(features=tf.train.Features(feature={
        'image/height': dataset_util.int64_feature(height),
        'image/width': dataset_util.int64_feature(width),
        'image/filename': dataset_util.bytes_feature(filename),
        'image/source_id': dataset_util.bytes_feature(filename),
        'image/encoded': dataset_util.bytes_feature(encoded_jpg),
        'image/format': dataset_util.bytes_feature(image_format),
        'image/object/bbox/xmin': dataset_util.float_list_feature(xmins),
        'image/object/bbox/xmax': dataset_util.float_list_feature(xmaxs),
        'image/object/bbox/ymin': dataset_util.float_list_feature(ymins),
        'image/object/bbox/ymax': dataset_util.float_list_feature(ymaxs),
        'image/object/class/text': dataset_util.bytes_list_feature(classes_text),
        'image/object/class/label': dataset_util.int64_list_feature(classes),
    }))
    return tf_example

def main(_):
    writer = tf.io.TFRecordWriter(args.output_path)
    path = os.path.join(os.getcwd(), args.image_dir)
    examples = pd.read_csv(args.csv_input)
    grouped = split(examples, 'filename')
    for group in grouped:
        tf_example = create_tf_example(group, path)
        writer.write(tf_example.SerializeToString())

    writer.close()
    output_path = os.path.join(os.getcwd(), args.output_path)
    print('Successfully created the TFRecords: {}'.format(output_path))

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description="Convert CSV to TFRecord")
    parser.add_argument("--output_path", type=str, help="Path to output TFRecord file", required=True)
    parser.add_argument("--image_dir", type=str, help="Path to image directory", required=True)
    parser.add_argument("--csv_input", type=str, help="Path to CSV input file", required=True)
    args = parser.parse_args()
    tf.compat.v1.app.run()
