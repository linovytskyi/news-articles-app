import matplotlib.pyplot as plt
from transformers import XLMRobertaTokenizerFast, XLMRobertaForSequenceClassification, TrainingArguments, Trainer
from datasets import DatasetDict, Dataset
import torch
from functools import partial
from sklearn.metrics import accuracy_score, f1_score
from sklearn.model_selection import train_test_split
import pandas as pd

device = "cuda" if torch.cuda.is_available() else "cpu"

def preprocess_data(examples, tokenizer, label2id):
    inputs = tokenizer(examples['text'], max_length=512, truncation=True, padding="max_length")
    labels = [label2id[label] for label in examples['article_type']]
    inputs["labels"] = labels
    return inputs

def compute_metrics(pred):
    labels = pred.label_ids
    preds = pred.predictions.argmax(-1)
    acc = accuracy_score(labels, preds)
    f1 = f1_score(labels, preds, average='weighted')
    return {"accuracy": acc, "f1": f1}

def plot_metrics(trainer_state_log):
    epochs = []
    train_loss = []
    eval_loss = []
    accuracy = []
    f1_scores = []

    for log in trainer_state_log:
        if 'epoch' in log:
            if log['epoch'] not in epochs:  # Avoid duplicate entries for the same epoch
                epochs.append(log['epoch'])
        if 'loss' in log:
            train_loss.append(log['loss'])
        if 'eval_loss' in log:
            eval_loss.append(log['eval_loss'])
        if 'eval_accuracy' in log:
            accuracy.append(log['eval_accuracy'])
        if 'eval_f1' in log:
            f1_scores.append(log['eval_f1'])

    # Ensure lists are the same length by padding with None
    max_length = len(epochs)
    train_loss.extend([None] * (max_length - len(train_loss)))
    eval_loss.extend([None] * (max_length - len(eval_loss)))
    accuracy.extend([None] * (max_length - len(accuracy)))
    f1_scores.extend([None] * (max_length - len(f1_scores)))

    # Plot Training and Evaluation Loss
    plt.figure(figsize=(10, 6))
    plt.plot(epochs, train_loss, label="Training Loss", marker='o')
    plt.plot(epochs, eval_loss, label="Evaluation Loss", marker='o')
    plt.xlabel("Epochs")
    plt.ylabel("Loss")
    plt.title("Training and Evaluation Loss")
    plt.legend()
    plt.grid(True)
    plt.show()

    # Plot Accuracy and F1-score
    plt.figure(figsize=(10, 6))
    plt.plot(epochs, accuracy, label="Accuracy", marker='o')
    plt.plot(epochs, f1_scores, label="F1 Score", marker='o')
    plt.xlabel("Epochs")
    plt.ylabel("Metrics")
    plt.title("Accuracy and F1-score Over Epochs")
    plt.legend()
    plt.grid(True)
    plt.show()

if __name__ == '__main__':
    df = pd.read_csv('../../data/processed/results.csv')

    train_df, test_df = train_test_split(df, test_size=0.2, stratify=df['article_type'], random_state=42)

    dataset = DatasetDict({
        'train': Dataset.from_pandas(train_df),
        'test': Dataset.from_pandas(test_df)
    })

    model_name = 'xlm-roberta-base'
    num_labels = len(df['article_type'].unique())
    tokenizer = XLMRobertaTokenizerFast.from_pretrained(model_name)
    model = XLMRobertaForSequenceClassification.from_pretrained(model_name, num_labels=num_labels).to(device)

    label_list = df['article_type'].unique()
    label2id = {label: idx for idx, label in enumerate(label_list)}
    id2label = {idx: label for label, idx in label2id.items()}

    preprocess_with_tokenizer = partial(preprocess_data, tokenizer=tokenizer, label2id=label2id)

    tokenized_train_dataset = dataset["train"].map(preprocess_with_tokenizer, batched=True)
    tokenized_val_dataset = dataset["test"].map(preprocess_with_tokenizer, batched=True)

    training_args = TrainingArguments(
        output_dir="llm/results",
        evaluation_strategy="epoch",
        learning_rate=5e-5,
        per_device_train_batch_size=8,
        per_device_eval_batch_size=8,
        num_train_epochs=6,
        weight_decay=0.01,
        fp16=True,
        warmup_steps=500,
        save_strategy="epoch",
        logging_dir="./logs",
        logging_strategy="steps",
        logging_steps=100,
        save_total_limit=2,
        load_best_model_at_end=True,
        disable_tqdm=False,
        logging_first_step=True
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
    plot_metrics(trainer.state.log_history)
