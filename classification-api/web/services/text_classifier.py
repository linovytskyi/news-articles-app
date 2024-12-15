import torch
from ml.classification.llm.text_classification_model import load_model

class TextClassifier:

    TEXT_CLASSES = [
        "Астрологія",  "Війна", "Економіка", "Енергетика",
        "Зброя", "Здоров`я", "Зірки", "Корисні статті",
        "Наука", "Погода", "Політика", "Різне",
        "Світ", "Спорт", "Україна", "Шоу-бізнес"
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