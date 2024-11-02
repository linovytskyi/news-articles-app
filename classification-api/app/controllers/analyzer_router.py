import logging

from fastapi import APIRouter, HTTPException
from fastapi.params import Depends
from pydantic import BaseModel
from app.dependencies import text_classifier, get_topic_service, get_keyword_service
from app.dependencies import text_entity_extractor
from app.dependencies import keyword_extractor
from app.schemas.analyzed_text import AnalyzedText
from app.services.keyword_service import KeywordService
from app.services.topic_service import TopicService

router = APIRouter(prefix='/analyze')
logger = logging.getLogger(__name__)

class TextData(BaseModel):
    text: str

@router.post("", response_model=AnalyzedText)
def analyze_text(text_data: TextData,
                 topic_service: TopicService = Depends(get_topic_service),
                 keyword_service: KeywordService = Depends(get_keyword_service)):
    text = text_data.text
    logger.info(f"Received request to analyze text:\n {text}")
    topic_name = text_classifier.classify_topic(text)
    topic = topic_service.get_topic_by_name(topic_name)

    if topic is None:
        raise HTTPException(status_code=404, detail="Topic not found")

    topic_keywords = keyword_service.get_all_keywords_by_topic_id(topic.id)

    logger.info(f"Topic keywords {topic_keywords}")

    persons = text_entity_extractor.extract_entities_with_label(text, "PER")
    keywords = keyword_extractor.extract_keywords(text, topic_keywords)

    analyzed_text = AnalyzedText(
        topic=topic,
        persons=persons,
        keywords=keywords
    )

    return analyzed_text