from transformers import XLMRobertaTokenizerFast, \
    XLMRobertaForSequenceClassification
import torch

def load_model():
    trained_model_path = r'C:\Users\Nikita Linovytskyi\IdeaProjects\news-articles-app\classification-api\ml\classification\llm\results\checkpoint-3660'
    model_name = 'xlm-roberta-base'

    device = "cuda" if torch.cuda.is_available() else "cpu"
    tokenizer = XLMRobertaTokenizerFast.from_pretrained(model_name, clean_up_tokenization_spaces=True)
    model = XLMRobertaForSequenceClassification.from_pretrained(trained_model_path).to(device)
    print("LLM Model and Tokenizer loaded successfully!")
    return model, tokenizer, device