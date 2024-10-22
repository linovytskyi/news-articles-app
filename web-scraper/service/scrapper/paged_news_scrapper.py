from abc import ABC
from service.csv_writer import CSVWriter
from service.scrapper.news_scrapper import NewsScrapper


class PagedNewsScrapper(NewsScrapper, ABC):
    HEADERS = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36'
    }

    CSV_WRITER = CSVWriter('results.csv',
                           ['title', 'article_type', 'text', 'keywords', 'datetime', 'source', 'url',
                            'number_of_views'])

    def __init__(self, starting_page_index, end_page_index):
        super().__init__()
        self.current_page_index = starting_page_index
        self.end_page_index = end_page_index

    def run_scrapper(self):
        self.logger.info("Running scrapper...")
        url = self.get_url_of_next_articles_list()
        self.logger.info(f"Scraping news on page with index {self.current_page_index}")
        while url is not None:
            self.scrap_articles_from_articles_list_page(url)
            self.current_page_index += 1
            url = self.get_url_of_next_articles_list()
        self.logger.info("Scrapper finishes work")

    def get_url_of_next_articles_list(self):
        if self.current_page_index < self.end_page_index:
            return None

        return self.construct_next_articles_list_url()