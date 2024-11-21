from abc import ABC
from app.scrappers.csv_writer import CSVWriter
from app.scrappers.news_scrapper import NewsScrapper


class PagedNewsScrapper(NewsScrapper, ABC):

    current_page_index: int
    end_page_index: int

    def __init__(self, source, latest_news_page_url):
        super().__init__(source, latest_news_page_url)

    def create_dataset_of_news_per_period(self, start, end):
        self.current_page_index = start
        self.end_page_index = end
        self.__create_dataset_of_news_per_page_index_period()

    def __create_dataset_of_news_per_page_index_period(self):
        self.logger.info("Running scrapper...")
        url = self.__construct_url_of_next_list_page()
        self.logger.info(f"Scraping news on page with index {self.current_page_index}")
        while url is not None:
            self.write_dataset_entries_from_news_page_list_url(url)
            self.current_page_index += 1
            url = self.__construct_url_of_next_list_page()
        self.logger.info("Scrapper finishes work")

    def __construct_url_of_next_list_page(self):
        if self.current_page_index > self.end_page_index:
            return None

        return self.construct_url_of_next_list_page()