import logging

from sqlalchemy.orm import Session
from web.models.topic import Topic, TopicKeyword


class TopicRepository:

    def __init__(self, db: Session):
        self.db = db
        self.logger = logging.getLogger(__name__)

    def get_topic_by_id(self, topic_id: int):
        self.logger.info(f"Getting topic by id: {topic_id}")
        topic = self.db.query(Topic).filter(Topic.id == topic_id).first()
        self.logger.info(f"Found topic: {topic}")
        return topic

    def get_topic_by_name(self, topic_name: str):
        self.logger.info(f"Getting topic by name: {topic_name}")
        topic = self.db.query(Topic).filter(Topic.name == topic_name).first()
        self.logger.info(f"Found topic: {topic}")
        return topic

    def get_topic_id_by_name(self, topic_name: str):
        self.logger.info(f"Getting topic id by topic name {topic_name}")
        topic_id = self.db.query(Topic.id).filter(Topic.name == topic_name).first()
        self.logger.info(f"Found topic id: {topic_id}")
        return topic_id

    def get_all_topic_names(self):
        self.logger.info("Getting all topic names")
        topic_names_tuples = self.db.query(Topic.name).all()
        topic_names_list = [name[0] for name in topic_names_tuples]
        self.logger.info(f"All topic names {topic_names_list}")
        return topic_names_list

    def get_all_topics(self):
        self.logger.info("Getting all topics")
        topics = self.db.query(Topic).all()
        self.logger.info(f"All topics {topics}")
        return topics