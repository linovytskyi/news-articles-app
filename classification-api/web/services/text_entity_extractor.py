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

    def extract_entities_with_label(self, text, entities_labels):
        entities_names = set()

        for entity in self.__extract_entities(text):
            if entity.label_ in entities_labels:
                entity_name = self.__get_text_nominative_form(entity.text)
                if entity.label_ == "PER":
                    if len(entity_name.split()) > 1:
                        entities_names.add(entity_name)
                else:
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
        entities_names = sorted(entities_names, key=len, reverse=True)
        unique_entity_names = set()

        for entity_name in entities_names:
            if not any(entity_name in other_name for other_name in unique_entity_names):
                unique_entity_names.add(entity_name)

        return list(unique_entity_names)

    def __get_text_nominative_form(self, text):
        doc = self.nlp(text)
        normalized_words = []

        for token in doc:
            parsed_word = self.morph.parse(token.text)[0]

            if token.pos_ == "ADJ" and token.head.pos_ == "NOUN":
                noun_parsed = self.morph.parse(token.head.text)[0]


                inflect_grammemes = {'nomn'}
                if noun_parsed.tag.gender:
                    inflect_grammemes.add(noun_parsed.tag.gender)
                if noun_parsed.tag.number:
                    inflect_grammemes.add(noun_parsed.tag.number)

                adj_form = parsed_word.inflect(inflect_grammemes)
                normalized_words.append(adj_form.word if adj_form else parsed_word.normal_form)
            else:
                normalized_words.append(parsed_word.normal_form)

        return ' '.join(normalized_words)
