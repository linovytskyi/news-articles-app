from app.scrappers.news_source2.source2_scrapper import Source2Scrapper
from app.scrappers.news_source1.source1_scrapper import Source1Scrapper
from app.service.aggregation_service import AggregationService

source1Scrapper = Source1Scrapper()
source2Scrapper = Source2Scrapper()

scrappers = [source1Scrapper, source2Scrapper]

amount_of_articles_to_scrap_in_batch = 10
amount_of_minutes_to_sleep = 5

aggregations_service = AggregationService(scrappers, amount_of_articles_to_scrap_in_batch, amount_of_minutes_to_sleep)
aggregations_service.start_aggregation()