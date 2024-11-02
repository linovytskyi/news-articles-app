import logging

from sqlalchemy.orm import Session
from app.models.topic import TopicKeyword


class KeywordService:

    def __init__(self, db: Session):
        self.db = db
        self.logger = logging.getLogger(__name__)

    def get_all_keywords(self):
        return self.db.query(TopicKeyword).all()

    def get_keyword_by_id(self, keyword_id: int):
        return self.db.query(TopicKeyword).filter(TopicKeyword.id == keyword_id).first()

    def get_keyword_by_value(self, value: str):
        return self.db.query(TopicKeyword).filter(TopicKeyword.value == value).all()

    def get_all_keywords_by_topic_id(self, topic_id: int):
        keywords = self.db.query(TopicKeyword.value).filter(TopicKeyword.topic_id == topic_id).all()
        return [keyword[0] for keyword in keywords]