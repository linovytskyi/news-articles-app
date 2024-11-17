from abc import ABC
from app.scrappers.csv_writer import CSVWriter
from app.scrappers.news_scrapper import NewsScrapper


class PagedNewsScrapper(NewsScrapper, ABC):

    current_page_index: int
    end_page_index: int

    HEADERS = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36'
    }

    CSV_WRITER = CSVWriter('results.csv',
                           ['title', 'article_type', 'text', 'keywords', 'datetime', 'source', 'url',
                            'number_of_views'])

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