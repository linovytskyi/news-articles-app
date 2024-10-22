from abc import abstractmethod, ABC
from datetime import timedelta
from service.scrapper.news_scrapper import NewsScrapper

class DateNewsScrapper(NewsScrapper, ABC):

    def __init__(self, start_date, end_date):
        super().__init__()
        self.start_date = start_date
        self.current_date = start_date
        self.delta_in_day = timedelta(days=1)
        self.end_date = end_date
        self.amount_of_processed_dates = 0
        self.current_page = 1

    @abstractmethod
    def get_amount_of_pages(self, url):
        pass

    def run_scrapper(self):
        self.logger.info("Running scrapper...")
        page_url = self.get_url_of_next_articles_list()
        while page_url is not None:
            self.__process_all_articles_for_date_on_page(page_url)
            self.current_date += self.delta_in_day
            self.amount_of_processed_dates += 1
            self.current_page = 1
            page_url = self.get_url_of_next_articles_list()

        self.logger.info("Scrapper finishes work")

    def get_url_of_next_articles_list(self):
        interval_delta = self.end_date - self.start_date
        if self.amount_of_processed_dates >= interval_delta.days:
            return None

        return self.construct_next_articles_list_url()

    def __process_all_articles_for_date_on_page(self, page_url):
        self.logger.info(f"Scrapping news for date {self.current_date}")
        amount_of_pages = self.__get_amount_of_pages(page_url)
        while self.current_page <= amount_of_pages:
            self.__scrap_articles_from_articles_list_page(page_url, self.current_page)
            self.current_page += 1
        self.logger.info(f"Successfully scrapped news for date {self.current_date}")

    def __get_amount_of_pages(self, url):
        self.logger.info(f"Trying to get amount of pages from URL:{url}")
        try:
            amount_of_pages = self.get_amount_of_pages(url)
            self.logger.info(f"Amount of pages {amount_of_pages}")
            return amount_of_pages
        except Exception as e:
            self.logger.error(f"An exception occurred. Number of pages wasn't retrieved", exc_info=True)

    def __scrap_articles_from_articles_list_page(self, page_url, current_page):
        self.logger.info(f"Scrapping news on {current_page} page")
        self.scrap_articles_from_articles_list_page(page_url)
        self.logger.info(f"Scrapped news on {current_page} page")