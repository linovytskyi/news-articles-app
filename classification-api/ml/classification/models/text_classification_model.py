from transformers import AutoModelForSequenceClassification, AutoTokenizer
import torch
from transformers import AutoTokenizer, AutoModelForSequenceClassification

def load_model():
    trained_model_path = r'C:\Users\Nikita Linovytskyi\IdeaProjects\news-articles-app\classification-api\ml\classification\models\results\checkpoint-10000'
    model_name = 'xlm-roberta-base'

    device = "cuda" if torch.cuda.is_available() else "cpu"
    tokenizer = AutoTokenizer.from_pretrained(model_name, clean_up_tokenization_spaces=True)
    model = AutoModelForSequenceClassification.from_pretrained(trained_model_path).to(device)
    print("LLM Model and Tokenizer loaded successfully!")
    return model, tokenizer, device