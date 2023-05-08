import pandas as pd
import argparse
import os

# 划分数据集，将数据集划分为训练集和验证集。
def main(csv_input, output_directory, train_ratio):
    df = pd.read_csv(csv_input)
    df = df.sample(frac=1).reset_index(drop=True)

    train_df = df.sample(frac=train_ratio, random_state=1)
    val_df = df.drop(train_df.index)

    if not os.path.exists(output_directory):
        os.makedirs(output_directory)

    train_df.to_csv(os.path.join(output_directory, 'train.csv'), index=False)
    val_df.to_csv(os.path.join(output_directory, 'val.csv'), index=False)

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--csv_input', help='Path to the input CSV file', type=str, required=True)
    parser.add_argument('--output_directory', help='Directory where the train and validation CSV files will be saved', type=str, required=True)
    parser.add_argument('--train_ratio', help='Ratio of the dataset to be used for training', type=float, default=0.8)

    args = parser.parse_args()
    main(args.csv_input, args.output_directory, args.train_ratio)
