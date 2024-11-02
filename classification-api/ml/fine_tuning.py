from transformers import AutoTokenizer, AutoModelForSequenceClassification, Trainer, TrainingArguments
from datasets import DatasetDict, Dataset
import torch
from functools import partial
from sklearn.metrics import accuracy_score, f1_score
from sklearn.model_selection import train_test_split
import pandas as pd

device = "cuda" if torch.cuda.is_available() else "cpu"

def preprocess_data(examples, tokenizer, label2id):
    inputs = tokenizer(examples['title'], examples['text'], max_length=512, truncation=True, padding="max_length")
    labels = [label2id[label] for label in examples['article_type']]
    inputs["labels"] = labels
    return inputs

def compute_metrics(pred):
    labels = pred.label_ids
    preds = pred.predictions.argmax(-1)
    acc = accuracy_score(labels, preds)
    f1 = f1_score(labels, preds, average='weighted')
    return {"accuracy": acc, "f1": f1}

# Основний блок
if __name__ == '__main__':
    df = pd.read_csv('../data/results.csv')

    train_df, test_df = train_test_split(df, test_size=0.2, stratify=df['article_type'], random_state=42)

    dataset = DatasetDict({
        'train': Dataset.from_pandas(train_df),
        'test': Dataset.from_pandas(test_df)
    })

    model_name = 'xlm-roberta-base'
    num_labels = len(df['article_type'].unique())
    tokenizer = AutoTokenizer.from_pretrained(model_name)
    model = AutoModelForSequenceClassification.from_pretrained(model_name, num_labels=num_labels).to(device)

    label_list = df['article_type'].unique()
    label2id = {label: idx for idx, label in enumerate(label_list)}
    id2label = {idx: label for label, idx in label2id.items()}

    preprocess_with_tokenizer = partial(preprocess_data, tokenizer=tokenizer, label2id=label2id)

    tokenized_train_dataset = dataset["train"].map(preprocess_with_tokenizer, batched=True)
    tokenized_val_dataset = dataset["test"].map(preprocess_with_tokenizer, batched=True)

    training_args = TrainingArguments(
        output_dir="../../results",
        evaluation_strategy="epoch",
        learning_rate=2e-5,
        per_device_train_batch_size=4,
        per_device_eval_batch_size=4,
        num_train_epochs=4,
        max_steps=10000,
        weight_decay=0.01,
        fp16=True
    )

    trainer = Trainer(
        model=model,
        args=training_args,
        train_dataset=tokenized_train_dataset,
        eval_dataset=tokenized_val_dataset,
        tokenizer=tokenizer,
        compute_metrics=compute_metrics,
    )

    trainer.train()
