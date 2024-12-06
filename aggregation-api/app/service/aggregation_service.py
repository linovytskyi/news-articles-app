import logging
from app.client.rabbitmq_client import RabbitMqClient
import schedule
import time
from concurrent.futures import ThreadPoolExecutor

class AggregationService:

    def __init__(self, scrappers, amount_of_articles_to_scrap_in_batch, amount_of_minutes_to_sleep):
        self.rabbit_mq_client = RabbitMqClient('localhost', 'news_queue', 'news_queue')
        self.scrappers = scrappers
        self.amount_of_articles_to_scrap_in_batch = amount_of_articles_to_scrap_in_batch
        self.amount_of_minutes_to_sleep = amount_of_minutes_to_sleep
        self.logger = logging.getLogger(__name__)

    def __scrape_and_send(self, func, arg):
        try:
            articles = func(arg)
            for article in articles:
                self.rabbit_mq_client.send_to_queue(article)
                self.logger.info(f"Sent article to RabbitMQ: {article}")
        except Exception as e:
            self.logger.error(f"Error while scraping and sending articles: {e}")

    def __run_scrapers(self):
        scraper_tasks = [
            (scrapper.scrap_latest_articles, self.amount_of_articles_to_scrap_in_batch) for scrapper in self.scrappers
        ]
        with ThreadPoolExecutor() as executor:
            for func, arg in scraper_tasks:
                executor.submit(self.__scrape_and_send, func, arg)

    def start_aggregation(self):
        if not self.scrappers:
            self.logger.warning("No scrappers available to run aggregation.")
            return

        schedule.every(self.amount_of_minutes_to_sleep).minutes.do(self.__run_scrapers)

        self.__run_scrapers()

        self.logger.info("Starting the scraper scheduler...")

        try:
            while True:
                schedule.run_pending()
                time.sleep(self.amount_of_minutes_to_sleep)
        except KeyboardInterrupt:
            self.logger.info("Scraper scheduler stopped.")
