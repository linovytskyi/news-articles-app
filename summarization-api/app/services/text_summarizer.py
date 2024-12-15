import logging

from sumy.parsers.plaintext import PlaintextParser
from sumy.nlp.tokenizers import Tokenizer
from sumy.summarizers.text_rank import TextRankSummarizer

class TextSummarizer:

    __MIN_AMOUNT_OF_SENTENCES = 2
    __MAX_AMOUNT_OF_SENTENCES = 7
    __PERCENT_OF_INFORMATION_TO_SUMMARIZE_TO = 40

    def __init__(self):
        self.__parser = PlaintextParser
        self.__summarizer = TextRankSummarizer()
        self.logger = logging.getLogger(__name__)

    def summarize(self, text: str):
        self.logger.info("Summarizing text...")
        parsed_text = self.__parser.from_string(text, Tokenizer("uk")).document
        total_amount_of_sentences = len(parsed_text.sentences)
        self.logger.info(f"Total amount of sentences in text: {total_amount_of_sentences}")

        amount_of_sentences_to_make_summary_from = int(total_amount_of_sentences * (self.__PERCENT_OF_INFORMATION_TO_SUMMARIZE_TO / 100))

        if amount_of_sentences_to_make_summary_from < self.__MIN_AMOUNT_OF_SENTENCES:
            amount_of_sentences_to_make_summary_from = self.__MIN_AMOUNT_OF_SENTENCES

        if amount_of_sentences_to_make_summary_from > self.__MAX_AMOUNT_OF_SENTENCES:
            amount_of_sentences_to_make_summary_from = self.__MAX_AMOUNT_OF_SENTENCES

        self.logger.info(f"Amount of sentences to make summary: {amount_of_sentences_to_make_summary_from}")

        summary = self.__summarizer(parsed_text, amount_of_sentences_to_make_summary_from)
        summary_text = self.__join_sentences_of_summary(summary)
        self.logger.info(f"Summary of text:\n{summary_text}")
        return summary_text

    @staticmethod
    def __join_sentences_of_summary(summary):
        summary_text = ""
        for sentence in summary:
            summary_text += str(sentence) + ". "

        return summary_text.strip()