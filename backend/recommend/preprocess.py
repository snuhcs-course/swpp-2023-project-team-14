import numpy as np
import pandas as pd
from transformers import ElectraTokenizer, ElectraModel, BertTokenizer, BertModel
import torch
import time 
from datetime import datetime
import argparse

# Max Sequence
max_sequence_length = 512

def getEmbedding(sentence, model, tokenizer):
    sentence = sentence[:max_sequence_length]
    tokens = tokenizer(sentence, return_tensors="pt", padding=True, truncation=True)
    with torch.no_grad():
        output = model(**tokens)
    embedding = output.last_hidden_state.mean(dim=1).detach().numpy()
    return embedding

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('type', type=int)
    args = parser.parse_args()
    dataType = args.type
    
    data_dir = "./data/"

    # Load Pretrained Models
    koelectra_model_name = "monologg/koelectra-base-v3-discriminator"
    kobert_model_name = "snunlp/KR-BERT-char16424"
    koelectra_tokenizer = ElectraTokenizer.from_pretrained(koelectra_model_name)
    kobert_tokenizer = BertTokenizer.from_pretrained(kobert_model_name)
    koelectra_model = ElectraModel.from_pretrained(koelectra_model_name)
    kobert_model = BertModel.from_pretrained(kobert_model_name)


    
    ## DataType: User(1), Event(0)
    if dataType == 1:
        beforePreprocessFileName = data_dir+"userData.csv"
        afterPreprocessFileName = data_dir+"userEmbedding.csv"
    elif dataType == 0:
        beforePreprocessFileName = data_dir+"eventData.csv"
        afterPreprocessFileName = data_dir+"eventEmbedding.csv"
    else:
        raise Exception
    

    # Get the current time in seconds since the epoch
    timestamp = time.time()

    # Convert the timestamp to a datetime object
    dt_object = datetime.fromtimestamp(timestamp)

    # Format the datetime object as a string in the desired format
    formatted_time = dt_object.strftime("%Y-%m-%d %H:%M:%S")

    print(f'preprocess: reading data. time={formatted_time}')
    df = pd.read_csv(beforePreprocessFileName)

    df.fillna("없음", inplace=True)
    df['description'] = df.apply(lambda row: ' '.join([f'{col}: {val},' for col, val in row.items()]), axis=1)

    # Get the current time in seconds since the epoch
    timestamp = time.time()

    # Convert the timestamp to a datetime object
    dt_object = datetime.fromtimestamp(timestamp)

    # Format the datetime object as a string in the desired format
    formatted_time = dt_object.strftime("%Y-%m-%d %H:%M:%S")
    print(f'preprocess: getting embeddings. time={formatted_time}')

    df['koElectraEmbedding'] = df['description'].apply(lambda x: getEmbedding(x, koelectra_model, koelectra_tokenizer))
    df['koBertEmbedding'] = df['description'].apply(lambda x: getEmbedding(x, kobert_model, kobert_tokenizer))

    df.to_csv(afterPreprocessFileName, index=False)

if __name__ == '__main__':
    main()
