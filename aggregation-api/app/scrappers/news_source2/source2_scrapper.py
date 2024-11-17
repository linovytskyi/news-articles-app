from datetime import datetime

import pytz
import requests
from bs4 import BeautifulSoup

from app.model.article import Article
from app.scrappers.paged_news_scrapper import PagedNewsScrapper


class UnianScrapper(PagedNewsScrapper):

    def __init__(self):
        super().__init__("https://www.unian.ua/", "https://www.unian.ua/detail/main_news")

    def scrap_article_links_from_list_page(self, page_url):
        self.logger.info(f"Scrapping main page with URL: {page_url}...")
        response = requests.get(page_url, headers=self.HEADERS, timeout=10)

        if response.status_code == 200:
            self.logger.info("Got response with status code 200. Starting to process HTML")
            soup = BeautifulSoup(response.text, 'html.parser')
            article_links = self.__scrap_article_links(soup)
            return article_links

    @staticmethod
    def __scrap_article_links(soup):
        articles_list_block = soup.find("div", {"class": "prl0 col-sm-12"})
        articles = articles_list_block.find_all("a", {"class": "list-thumbs__title"})
        article_links = []
        for article in articles:
            link = article['href']
            article_links.append(link)
        return article_links

    def scrap_article(self, url):
        response = requests.get(url, headers=self.HEADERS, timeout=10)

        if response.status_code == 200:
            soup = BeautifulSoup(response.text, 'html.parser')

            article_html = soup.find("div", {"class": "article"})

            article_title = article_html.h1.get_text()
            article_type = article_html.div.div.ol.find_all("li")[-1].get_text()
            article_text_blocks = article_html.find_all("div", {"class": "article-text"})
            article_text = ""
            for block in article_text_blocks:
                article_text += block.get_text() + ". "
            article_text = article_text.split("Вас також можуть зацікавити новини:")[0]
            article_info = article_html.find("div", {"class": "article__info"})

            article_published_time_str = article_info.find("div", {"class": "article__info-item time"}).get_text()
            dt = datetime.strptime(article_published_time_str, "%H:%M, %d.%m.%y")
            article_published_time = dt.strftime("%Y-%m-%dT%H:%M:%S")

            article_tags = article_html.find("div", {"class": "article__tags"})
            tags = []
            if article_tags is not None:
                for tag in article_tags:
                    tags.append(tag.get_text())

            article_image = article_html.find_all("img", {"class": "lazy"})[0]['data-src']

            return Article(article_title.strip(), article_type.strip(), article_text.strip(), tags,
                              article_published_time, self.source, url, None, article_image)

        else:
            self.logger.error(f"Failed to retrieve the page. Status code: {response.status_code}")

    def construct_url_of_next_list_page(self):
        return f"https://www.unian.ua/detail/all_news?page={self.current_page_index}"