import spacy
import pymorphy3

class TextEntityExtractor:

    def __init__(self):
        self.nlp = spacy.load("uk_core_news_lg")
        self.morph = pymorphy3.MorphAnalyzer(lang='uk')

    def extract_entities(self, text):
        entities_names = set()

        for entity in self.__extract_entities(text):
                entity_name = self.__get_text_nominative_form(entity.text)
                entities_names.add(entity_name)

        return self.__remove_duplicate_entities_names(entities_names)


    def extract_entities_with_label(self, text, entity_label):
        entities_names = set()
        labels = [entity_label]

        for entity in self.__extract_entities(text):
            if entity.label_ in labels:
                entity_name = self.__get_text_nominative_form(entity.text)
                entities_names.add(entity_name)

        return self.__remove_duplicate_entities_names(entities_names)

    def __extract_entities(self, text):
        doc = self.nlp(text)
        unique_entities = set()

        for entity in doc.ents:
            unique_entities.add(entity)

        return unique_entities

    @staticmethod
    def __remove_duplicate_entities_names(entities_names):
        duplicate_entity_names = set()
        for entity_name_a in entities_names:
            for entity_name_b in entities_names:
                if entity_name_a in entity_name_b and len(entity_name_a) > len(entity_name_b):
                    duplicate_entity_names.add(entity_name_a)

        for name in duplicate_entity_names:
            entities_names.remove(name)

        return entities_names

    def __get_text_nominative_form(self, text):
        words = text.split()
        normalized_words = [self.morph.parse(word)[0].normal_form for word in words]
        return ' '.join(normalized_words)