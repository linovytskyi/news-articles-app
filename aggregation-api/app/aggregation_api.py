import schedule
import time
from concurrent.futures import ThreadPoolExecutor

from app.client.rabbitmq_client import RabbitMqClient
# Importing the scrapers
from app.scrappers.tsn.tsn_scrapper import TsnScrapper
from app.scrappers.news_source2.source2_scrapper import UnianScrapper

# Initialize the scraper instances
tsn_scrapper = TsnScrapper()
unian_scrapper = UnianScrapper()

# Initialize RabbitMQ client
rabbit_mq_client = RabbitMqClient('localhost', 'news_queue', 'news_queue')

# List of scrapers and their arguments
scraper_tasks = [
    (tsn_scrapper.scrap_latest_articles, 10),
    (unian_scrapper.scrap_latest_articles, 10)
]

# Function to scrape articles and send them to RabbitMQ concurrently
def scrape_and_send(func, arg):
    articles = func(arg)  # Scrape articles
    for article in articles:
        rabbit_mq_client.send_to_queue(article)  # Send each article to RabbitMQ
        print("Sent article to RabbitMQ:", article)

# Run scrapers and send articles to RabbitMQ in parallel
def run_scrapers():
    with ThreadPoolExecutor() as executor:
        # Submit each scraping task to the executor, which scrapes and sends articles in parallel
        for func, arg in scraper_tasks:
            executor.submit(scrape_and_send, func, arg)

# Schedule the job to run every 5 minutes
schedule.every(3).minutes.do(run_scrapers)

# Run scrapers immediately on startup
run_scrapers()

print("Starting the scraper scheduler...")

# Keep the scheduler running
while True:
    schedule.run_pending()
    time.sleep(1)
