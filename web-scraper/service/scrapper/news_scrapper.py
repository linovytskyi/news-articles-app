import random
import time
from abc import ABC, abstractmethod
from service.csv_writer import CSVWriter
from custom_logging import logger


class NewsScrapper(ABC):

    HEADERS = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36'
    }

    CSV_WRITER = CSVWriter('results.csv',
                           ['title', 'article_type', 'text', 'keywords', 'datetime', 'source', 'url',
                            'number_of_views'])

    def __init__(self):
        self.logger = logger

    @abstractmethod
    def get_url_of_next_articles_list(self):
        pass

    @abstractmethod
    def construct_next_articles_list_url(self):
        pass

    @abstractmethod
    def get_article_links_from_articles_list_page(self, page_url):
        pass

    @abstractmethod
    def scrap_news_article(self, url):
        pass

    @abstractmethod
    def run_scrapper(self):
        pass

    def scrap_articles_from_articles_list_page(self, page_url):
        article_links = self.__get_article_links_from_articles_list_page(page_url)
        for link in article_links:
            self.__scrap_news_article(link)

    def __get_article_links_from_articles_list_page(self, url):
        self.logger.info(f"Trying to scrap main page with URL:{url}")
        try:
            article_links = self.get_article_links_from_articles_list_page(url)
            self.logger.info(f"Successfully scraped main page. Article links {article_links}")
            return article_links
        except Exception as e:
            logger.error(f"An exception occurred. Article with link {url} wasn't processed", exc_info=True)

    def __scrap_news_article(self, url):
        try:
            self.logger.info(f"Trying to process article with URL: {url}")
            article = self.scrap_news_article(url)
            self.logger.info(f"Successfully scrapped article.\nTitle: {article.title}\nType: {article.article_type}\nKeywords: {article.keywords}")
            self.CSV_WRITER.write_article_to_csv(article)
            self.logger.info("Successfully processed article.")
            NewsScrapper.sleep()
        except Exception as e:
            logger.error(f"An exception occurred. Article with link {url} wasn't processed", exc_info=True)

    @staticmethod
    def sleep():
        if random.randint(0, 100) > 4:
            NewsScrapper.__short_sleep()
        else:
            NewsScrapper.__long_sleep()

    @staticmethod
    def __short_sleep():
        seconds = random.randint(5, 10)
        logger.info(f"Sleeping short for {seconds} seconds...")
        time.sleep(seconds)

    @staticmethod
    def __long_sleep():
        seconds = random.randint(300, 400)
        logger.info(f"Sleeping long for {seconds} seconds...")
        time.sleep(seconds)
