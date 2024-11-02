import string
import spacy
from spacy.lang.uk.stop_words import STOP_WORDS

class TextUtil:

    def __init__(self):
        self.nlp = spacy.load("uk_core_news_lg")


    def clean_text(self, text):
        doc = self.nlp(text)
        punctuations = string.punctuation + '«»—“”'
        cleaned_tokens = []

        for token in doc:
            if token.text.lower() not in STOP_WORDS and token.text not in punctuations and not token.is_space:
                cleaned_tokens.append(token.lemma_)


        return " ".join(cleaned_tokens)