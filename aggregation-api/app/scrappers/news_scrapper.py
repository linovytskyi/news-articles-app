import random
import time
from abc import ABC, abstractmethod

from requests import ReadTimeout

from app.scrappers.csv_writer import CSVWriter
from custom_logging import logger


class NewsScrapper(ABC):

    headers_list = [
        {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36'},
        {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1 Safari/605.1.15'},
        {'User-Agent': 'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:89.0) Gecko/20100101 Firefox/89.0'},
        {'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0'}
    ]

    HEADERS = None

    CSV_WRITER = CSVWriter('results.csv',
                           ['title', 'article_type', 'text', 'keywords', 'datetime', 'source', 'url',
                            'number_of_views'])

    def __init__(self, source, latest_news_page_url):
        self.logger = logger
        self.__set_random_header()
        self.source = source
        self.latest_news_page_url = latest_news_page_url

    @abstractmethod
    def construct_url_of_next_list_page(self):
        pass

    @abstractmethod
    def scrap_article_links_from_list_page(self, page_url):
        pass

    @abstractmethod
    def scrap_article(self, url):
        pass

    @abstractmethod
    def create_dataset_of_news_per_period(self, start, end):
        pass

    def scrap_latest_articles(self, amount):
        article_urls = self.__scrap_article_links_from_list_page(self.latest_news_page_url)
        latest_article_urls = article_urls[:amount]
        articles = []
        for url in latest_article_urls:
            aggregatedArticle = self.__scrap_article(url)
            articles.append(aggregatedArticle)
            self.__short_sleep()

        return articles


    def write_dataset_entries_from_news_page_list_url(self, page_url):
        article_links = self.__scrap_article_links_from_list_page(page_url)
        if article_links is not None:
            for link in article_links:
                self.__create_dataset_entry_from_news_page_url(link)

    def __scrap_article_links_from_list_page(self, url):
        self.logger.info(f"Trying to get aggregatedArticle links from page with URL:{url}")
        try:
            article_links = self.scrap_article_links_from_list_page(url)
            self.logger.info(f"Successfully scraped aggregatedArticle links {article_links}")
            return article_links
        except ReadTimeout as e:
            self.__handle_read_timeout()
        except Exception as e:
            logger.error(f"An exception occurred. Article links from URL {url} wasn't processed", exc_info=True)

    def __create_dataset_entry_from_news_page_url(self, url):
        try:
            aggregatedArticle = self.__scrap_article(url)
            self.CSV_WRITER.write_article_to_csv(aggregatedArticle)
            self.logger.info("Successfully written aggregatedArticle to an CSV file.")
            self.__sleep()
        except ReadTimeout as e:
            self.__handle_read_timeout()
        except Exception as e:
            logger.error(f"An exception occurred. Article with link {url} wasn't processed", exc_info=True)

    def __scrap_article(self, url):
        self.logger.info(f"Trying to scrap aggregatedArticle with URL: {url}")
        aggregatedArticle = self.scrap_article(url)
        self.logger.info(f"Successfully scrapped aggregatedArticle.\nTitle: {aggregatedArticle.title}\nType: {aggregatedArticle.article_type}\nKeywords: {aggregatedArticle.keywords}")
        return aggregatedArticle

    def __handle_read_timeout(self):
        logger.error(f"Read time out occurred", exc_info=True)
        self.__set_random_header()
        self.__long_sleep()

    def __set_random_header(self):
        headers = NewsScrapper.headers_list
        if self.HEADERS is not None:
            headers = [header for header in headers if header != self.HEADERS]
        self.HEADERS = random.choice(headers)
        logger.info("Updated request headers")

    @staticmethod
    def __sleep():
        if random.randint(0, 100) > 3:
            NewsScrapper.__short_sleep()
        else:
            NewsScrapper.__medium_sleep()

    @staticmethod
    def __short_sleep():
        seconds = random.randint(5, 10)
        logger.info(f"Sleeping short for {seconds} seconds...")
        time.sleep(seconds)

    @staticmethod
    def __medium_sleep():
        seconds = random.randint(300, 400)
        logger.info(f"Sleeping medium for {seconds} seconds...")
        time.sleep(seconds)

    @staticmethod
    def __long_sleep():
        seconds = random.randint(1200, 1800)
        logger.info(f"Sleeping long for {seconds} seconds...")
        time.sleep(seconds)