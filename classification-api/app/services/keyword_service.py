import logging

from sqlalchemy.orm import Session
from app.models.topic import TopicKeyword


class KeywordService:

    def __init__(self, db: Session):
        self.db = db
        self.logger = logging.getLogger(__name__)

    def get_all_keywords(self):
        self.logger.info("Getting all keywords")
        keywords = self.db.query(TopicKeyword).all()
        self.logger.info(f"All keywords: {keywords}")
        return keywords

    def get_keyword_by_id(self, keyword_id: int):
        self.logger.info(f"Getting keyword by id: {keyword_id}")
        keyword = self.db.query(TopicKeyword).filter(TopicKeyword.id == keyword_id).first()
        self.logger.info(f"Keyword: {keyword}")
        return keyword

    def get_keyword_by_value(self, value: str):
        self.logger.info(f"Getting keyword by value: {value}")
        keyword = self.db.query(TopicKeyword).filter(TopicKeyword.value == value).all()
        self.logger.info(f"Keyword: {keyword}")
        return keyword

    def get_all_keywords_by_topic_id(self, topic_id: int):
        self.logger.info(f"Getting all keywords by topic id: {topic_id}")
        keywords_tuples = self.db.query(TopicKeyword.value).filter(TopicKeyword.topic_id == topic_id).all()
        keywords_list = [keyword[0] for keyword in keywords_tuples]
        self.logger.info(f"Keywords list {keywords_list}")
        return keywords_list