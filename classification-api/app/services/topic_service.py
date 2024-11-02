import logging

from sqlalchemy.orm import Session
from app.models.topic import Topic, TopicKeyword
from app.services.text.text_classifier import TextClassifier


class TopicService:

    def __init__(self, db: Session):
        self.db = db
        self.logger = logging.getLogger(__name__)

    def get_topic_by_id(self, topic_id: int):
        return self.db.query(Topic).filter(Topic.id == topic_id).first()

    def get_topic_by_name(self, topic_name: str):
        return self.db.query(Topic).filter(Topic.name == topic_name).first()

    def get_topic_id_by_name(self, topic_name: str):
        return self.db.query(Topic.id).filter(Topic.name == topic_name).first()

    def get_all_topic_names(self):
        topic_names = self.db.query(Topic.name).all()
        return [name[0] for name in topic_names]

    def get_all_topics(self):
        return self.db.query(Topic).all()
