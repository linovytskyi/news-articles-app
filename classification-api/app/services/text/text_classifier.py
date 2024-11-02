import torch
from ml.models.text_classification_model import load_model

class TextClassifier:

    TEXT_CLASSES = ['Проспорт', 'Війна', 'Світ', 'Шоу-бізнес', 'Гламур', 'Київ', 'Укрaїнa',
                    'Ексклюзив ТСН', 'Зірки', 'Суспільство', 'Цікaвинки', 'Гроші', 'Наука та IT',
                    'Здоровий спосіб життя', 'Дім', 'Схуднення', 'Актуальна тема', 'Рецепти',
                    'Астрологія', 'Різне', 'Зброя', 'Корисні статті', 'Діти', 'Політика',
                    'Здоров’я', 'Салон краси', 'Львів']

    def __init__(self):

        self.model, self.tokenizer, self.device = load_model()
        label2id = {label: idx for idx, label in enumerate(self.TEXT_CLASSES)}

        self.id2label = {idx: label for label, idx in label2id.items()}

    def classify_topic(self, text):
        inputs = self.tokenizer(text, return_tensors="pt", padding=True, truncation=True, max_length=512).to(self.device)
        outputs = self.model(**inputs)
        prediction = torch.argmax(outputs.logits, dim=-1)
        return self.id2label[prediction.item()]