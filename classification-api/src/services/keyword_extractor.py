from itertools import chain

import spacy
import pymorphy3

class KeyWordExtractor:

    parts_of_speech_with_lexeme = {"NOUN", "ADJF", "ADJS", "VERB", "PRTF", "PRTS", "NPRO"}

    def __init__(self):
        self.nlp = spacy.load("uk_core_news_lg")
        self.morph = pymorphy3.MorphAnalyzer(lang='uk')

    def extract_keywords(self, text, keywords):
        tokens = self.__get_all_possible_tokens(text)
        all_phrases = self.generate_all_possible_phrases(tokens)
        return self.__extract_all_keywords_from_phrases(keywords, all_phrases)

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
            if keyword in phrases:
                found_keywords.add(keyword)

        return found_keywords