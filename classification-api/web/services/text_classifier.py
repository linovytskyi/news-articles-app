import torch
from ml.classification.llm.text_classification_model import load_model

class TextClassifier:

    TEXT_CLASSES = [
        "Астрологія",  # 0
        "Війна",  # 1
        "Економіка",  # 2
        "Енергетика",  # 3
        "Зброя",  # 4
        "Здоров`я",  # 5
        "Зірки",  # 6
        "Корисні статті",  # 7
        "Наука",  # 8
        "Погода",  # 9
        "Політика",  # 10
        "Різне",  # 11
        "Світ",  # 12
        "Спорт",  # 13
        "Україна",  # 14
        "Шоу-бізнес"  # 15
    ]

    def __init__(self):

        self.model, self.tokenizer, self.device = load_model()
        label2id = {label: idx for idx, label in enumerate(self.TEXT_CLASSES)}

        self.id2label = {idx: label for label, idx in label2id.items()}

    def classify_topic(self, text):
        inputs = self.tokenizer(text, return_tensors="pt", padding=True, truncation=True, max_length=512).to(self.device)
        outputs = self.model(**inputs)
        prediction = torch.argmax(outputs.logits, dim=-1)
        return self.id2label[prediction.item()]