import pika
import json

class RabbitMqClient:

    def __init__(self, host, queue_name, routing_key):
        self.host = host
        self.queue_name = queue_name
        self.routing_key = routing_key


    def send_to_queue(self, article):
        connection = None
        try:
            connection = pika.BlockingConnection(pika.ConnectionParameters(self.host))
            channel = connection.channel()
            channel.queue_declare(queue=self.queue_name, durable=True)
            article_json = article.to_dict()

            message = json.dumps(article_json)
            channel.basic_publish(
                exchange='',
                routing_key=self.routing_key,
                body=message,
                properties=pika.BasicProperties(delivery_mode=2)  # Make message persistent
            )
            print("Sent data to queue:", article_json)

        except Exception as e:
            print(f"Error sending message to RabbitMQ: {e}")

        finally:
            if connection:
                connection.close()
