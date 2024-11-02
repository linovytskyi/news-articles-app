# app/dependencies.py

from fastapi import Depends

from app.services.keyword_service import KeywordService
from app.services.text.keyword_extractor import KeyWordExtractor
from app.services.text.text_classifier import TextClassifier
from app.services.text.text_entity_extractor import TextEntityExtractor
from app.services.topic_service import TopicService
from sqlalchemy.orm import Session
from app.database import SessionLocal

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


def get_topic_service(db: Session = Depends(get_db)):
    return TopicService(db)

def get_keyword_service(db: Session = Depends(get_db)):
    return KeywordService(db)

text_classifier = TextClassifier()
keyword_extractor = KeyWordExtractor()
text_entity_extractor = TextEntityExtractor()