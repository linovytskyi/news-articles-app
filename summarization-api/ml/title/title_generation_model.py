import logging

import torch
from transformers import AutoTokenizer, AutoModelForSeq2SeqLM


def load_model():
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

    tokenizer = AutoTokenizer.from_pretrained("google/mt5-large", legacy=False)
    model = AutoModelForSeq2SeqLM.from_pretrained("yelyah/mT5-XLSUM-ua-news").to(device)
    print("Tokenizer and Model have been loaded.")
    return model, tokenizer, device