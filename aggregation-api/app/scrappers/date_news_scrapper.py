from abc import abstractmethod, ABC
from datetime import timedelta, datetime
from app.scrappers.news_scrapper import NewsScrapper

class DateNewsScrapper(NewsScrapper, ABC):

    start_date: datetime
    end_date: datetime
    current_date: datetime
    delta_in_day: timedelta
    amount_of_processed_dates: int
    current_page: int

    def __init__(self, source, latest_news_page_url):
        super().__init__(source, latest_news_page_url)

    @abstractmethod
    def scrap_amount_of_pages_for_date(self, url_of_first_page_of_date):
        pass

    def create_dataset_of_news_per_period(self, start, end):
        self.__initialize_parameters_for_dataset_creation(start, end)
        self.__create_dataset_of_news_per_date_period()

    def __initialize_parameters_for_dataset_creation(self, start, end):
        self.start_date = start
        self.current_date = start
        self.delta_in_day = timedelta(days=1)
        self.end_date = end
        self.amount_of_processed_dates = 0
        self.current_page = 1

    def __create_dataset_of_news_per_date_period(self):
        self.logger.info("Running scrapper...")
        page_url = self.__construct_url_of_next_list_page()
        while page_url is not None:
            self.__write_dataset_entries_from_date_page_url(page_url)
            self.current_date += self.delta_in_day
            self.amount_of_processed_dates += 1
            self.current_page = 1
            page_url = self.__construct_url_of_next_list_page()

        self.logger.info("Scrapper finishes work")

    def __construct_url_of_next_list_page(self):
        interval_delta = self.end_date - self.start_date
        if self.amount_of_processed_dates >= interval_delta.days:
            return None

        return self.construct_url_of_next_list_page()

    def __write_dataset_entries_from_date_page_url(self, page_url):
        self.logger.info(f"Scrapping news for date {self.current_date}")
        amount_of_pages = self.__scrap_amount_of_pages_for_date(page_url)
        while self.current_page <= amount_of_pages:
            self.__write_dataset_entries_from_news_page_list_url(page_url)
            self.current_page += 1
        self.logger.info(f"Successfully scrapped news for date {self.current_date}")

    def __scrap_amount_of_pages_for_date(self, url):
        self.logger.info(f"Trying to get amount of pages from URL:{url}")
        try:
            amount_of_pages = self.scrap_amount_of_pages_for_date(url)
            self.logger.info(f"Amount of pages {amount_of_pages}")
            return amount_of_pages
        except Exception as e:
            self.logger.error(f"An exception occurred. Number of pages wasn't retrieved", exc_info=True)

    def __write_dataset_entries_from_news_page_list_url(self, page_url):
        self.logger.info(f"Scrapping news on {self.current_page} page")
        self.write_dataset_entries_from_news_page_list_url(page_url)
        self.logger.info(f"Scrapped news on {self.current_page} page")