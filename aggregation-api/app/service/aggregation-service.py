from app.client.rabbitmq_client import RabbitMqClient


class AggregationService:

    def __init__(self, scrappers):
        self.rabbit_mq_client = RabbitMqClient('localhost', 'news_queue', 'news_queue')
        self.scrappers = scrappers

