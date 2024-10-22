from datetime import datetime

import requests
from bs4 import BeautifulSoup

from model.article import Article
from service.scrapper.date_news_scrapper import DateNewsScrapper


class TsnScrapper(DateNewsScrapper):


    def __init__(self, start_date, end_date):
        super().__init__(start_date, end_date)
        self.source = "https://tsn.ua/"

    def get_amount_of_pages(self, url):
        response = requests.get(url, headers=self.HEADERS, timeout=10)

        if response.status_code == 200:
            soup = BeautifulSoup(response.text, 'html.parser')
            number_of_pages = int(soup.find("nav", {"class": "c-pagination"}).find_all("a")[-2].get_text())
            return number_of_pages
        else:
            self.logger.error(f"Failed to retrieve the page. Status code: {response.status_code}")

    def get_article_links_from_articles_list_page(self, page_url):
        response = requests.get(page_url, headers=self.HEADERS, timeout=10)

        if response.status_code == 200:
            soup = BeautifulSoup(response.text, 'html.parser')
            articles = soup.find_all('article')
            article_links = []
            for article in articles:
                link = article.find("a", {"class": "c-card__link"})['href']
                article_links.append(link)
            return article_links
        else:
            self.logger.error(f"Failed to retrieve the page. Status code: {response.status_code}")

    def scrap_news_article(self, url):
        response = requests.get(url, headers=self.HEADERS, timeout=10)

        if response.status_code == 200:
            soup = BeautifulSoup(response.text, 'html.parser')

            article = soup.find("main", {"class": "c-article"})

            article_box = soup.find("div", {"class": "c-article__box"})
            article_title = article_box.find("h1", {"class": "c-card__title"}).span.text
            article_breadcrumbs = article_box.find("nav", {"class": "c-article__breadcrumbs"})
            article_breadcrumbs_list = article_breadcrumbs.ol.find_all("li")
            article_type = article_breadcrumbs_list[len(article_breadcrumbs_list) - 2].get_text()
            article_body = article_box.find("div", {"class": "c-article__body"})
            article_body_p = article_body.find_all("p")
            article_footer = article_box.find("footer", {"class": "c-card__foot"})
            article_datetime = article_footer.find("time")
            article_date = article_datetime['datetime']
            article_source = self.source
            article_body_text_array = []

            article_amount_of_view = article_footer.find("dd", {
                "class": "c-bar__label i-before i-before--spacer-r-sm i-views"}).get_text().strip()

            related_topic_nav_bar = article.find("nav", {"class": "c-bar"})
            related_topics_div = related_topic_nav_bar.find_all("span", {"class": ["c-tag", "c-tag__label"]})
            related_topic_text = []

            banner = soup.find("div", {"class": "c-banner__slide"})

            for i in range(len(related_topics_div)):
                related_topic_text.append(related_topics_div[i].get_text().strip())

            for i in range(len(article_body_p)):
                text = article_body_p[i].get_text()
                article_body_text_array.append(text)

            article_body_text_array.pop()
            article_body_text = ''.join(article_body_text_array)

            article_object = Article(article_title.strip(), article_type.strip(), article_body_text.strip(), related_topic_text,
                                     article_date, article_source.strip(), url, article_amount_of_view)
            return article_object

        else:
            self.logger.error(f"Failed to retrieve the page. Status code: {response.status_code}")

    def construct_next_articles_list_url(self):
        day = self.current_date.strftime("%d")
        month = self.current_date.strftime("%m")
        year = self.current_date.strftime("%Y")
        return f"https://tsn.ua/news/page-{self.current_page}?day={day}&month={month}&year={year}"


start_date = datetime.strptime("2024-10-01", "%Y-%m-%d")
end_date = datetime.strptime("2024-10-14", "%Y-%m-%d")
scrapper = TsnScrapper(start_date, end_date)
scrapper.run_scrapper()


