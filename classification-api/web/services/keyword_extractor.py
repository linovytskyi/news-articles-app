from itertools import chain
import spacy
import pymorphy3

from util.text_util import TextUtil



class KeyWordExtractor:
    parts_of_speech_with_lexeme = {"NOUN", "ADJF", "ADJS", "VERB", "PRTF", "PRTS", "NPRO"}

    def __init__(self):
        self.nlp = spacy.load("uk_core_news_lg")
        self.morph = pymorphy3.MorphAnalyzer(lang='uk')

    def extract_keywords(self, text, topic_keywords):
        text = self.__preprocess_text(text)
        tokens = self.__get_all_possible_tokens(text)
        all_phrases = self.generate_all_possible_phrases(tokens)
        keywords_from_phrases = self.__extract_all_keywords_from_phrases(topic_keywords, all_phrases)
        return keywords_from_phrases


    def __get_all_possible_tokens(self, text):
        words = text.split()
        tokens = []
        for word in words:
            parsed_word = self.morph.parse(word)[0]
            if self.__can_have_lexeme(parsed_word):
                tokens.append(parsed_word.normal_form.lower())
            else:
                tokens.append(parsed_word.word.lower())
        return tokens

    def __extract_all_entities_with_lexemes(self, text):
        doc = self.nlp(text)
        unique_entities = set()

        for entity in doc.ents:
            lexemes = self.morph.parse(entity.text)[0].lexeme
            for lexeme in lexemes:
                unique_entities.add(lexeme)

        return unique_entities

    @staticmethod
    def __find_all_keywords_with_entities(keywords, entities):
        keywords_with_entities = set()
        for keyword in keywords:
            for entity in entities:
                if entity.word in keyword.lower() and not KeyWordExtractor.__is_name_with_one_word(entity) :
                    keywords_with_entities.add(keyword)
        return keywords_with_entities

    @staticmethod
    def __is_name_with_one_word(entity):
        if 'Name' in entity.tag and len(entity.word.split()) < 2:
            return True
        return False

    @staticmethod
    def __can_have_lexeme(parsed_word):
        pos = parsed_word.tag.POS
        return pos in KeyWordExtractor.parts_of_speech_with_lexeme

    @staticmethod
    def generate_all_possible_phrases(tokens):
        bigrams = list(zip(tokens, tokens[1:]))
        trigrams = list(zip(tokens, tokens[1:], tokens[2:]))
        bigrams_combined = [' '.join(bigram) for bigram in bigrams]
        trigrams_combined = [' '.join(trigram) for trigram in trigrams]

        all_phrases = list(chain(tokens, bigrams_combined, trigrams_combined))
        return all_phrases

    @staticmethod
    def __extract_all_keywords_from_phrases(keywords, phrases):
        found_keywords = set()
        for keyword in keywords:
            if keyword.lower() in phrases:
                found_keywords.add(keyword)

        return found_keywords

    @staticmethod
    def __preprocess_text(text: str):
        text = text.replace("'", "")
        text = text.replace(",", " , ")
        text = text.replace(".", " . ")
        text_util = TextUtil()
        text = text_util.clean_text(text)
        return text