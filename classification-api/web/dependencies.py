# app/dependencies.py

from fastapi import Depends

from web.repository.keyword_repository import KeywordRepository
from web.services.keyword_extractor import KeyWordExtractor
from web.services.text_classifier import TextClassifier
from web.services.text_entity_extractor import TextEntityExtractor
from web.repository.topic_repository import TopicRepository
from sqlalchemy.orm import Session
from web.database import SessionLocal

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


def get_topic_service(db: Session = Depends(get_db)):
    return TopicRepository(db)

def get_keyword_service(db: Session = Depends(get_db)):
    return KeywordRepository(db)

text_classifier = TextClassifier()
keyword_extractor = KeyWordExtractor()
text_entity_extractor = TextEntityExtractor()