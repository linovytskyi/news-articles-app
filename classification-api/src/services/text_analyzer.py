from src.services.keyword_extractor import KeyWordExtractor
from src.services.text_classifier import TextClassifier
from src.services.text_entity_extractor import TextEntityExtractor
from src.util.text_util import TextUtil



class TextAnalyzer:

    def __init__(self):
        self.text_util = TextUtil()
        self.text_classifier = TextClassifier()
        self.text_entity_extractor = TextEntityExtractor()
        self.keyword_extractor = KeyWordExtractor()


    def analyze_text(self, text, keywords):
        cleared_text = self.text_util.clean_text(text)
        text_class = self.text_classifier.classify_text(cleared_text)
        entities = self.text_entity_extractor.extract_entities(cleared_text)
        keywords = self.keyword_extractor.extract_keywords(cleared_text, keywords)
        print(text_class)
        print(entities)
        print(keywords)