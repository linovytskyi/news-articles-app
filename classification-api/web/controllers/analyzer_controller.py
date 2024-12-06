import logging

from fastapi import APIRouter
from fastapi.params import Depends
from pydantic import BaseModel
from web.dependencies import text_classifier, get_topic_service, get_keyword_service
from web.dependencies import text_entity_extractor
from web.dependencies import keyword_extractor
from web.schemas.analyzed_text import AnalyzedText
from web.repository.keyword_repository import KeywordRepository
from web.repository.topic_repository import TopicRepository

class TextData(BaseModel):
    text: str

logger = logging.getLogger(__name__)


router = APIRouter(prefix='/analyze')
@router.post("", response_model=AnalyzedText)
def analyze_text(text_data: TextData,
                 topic_service: TopicRepository = Depends(get_topic_service),
                 keyword_service: KeywordRepository = Depends(get_keyword_service)):
    text = text_data.text
    logger.info(f"Received request to analyze text:\n {text}")
    topic_name = text_classifier.classify_topic(text)
    topic = topic_service.get_topic_by_name(topic_name)

    topic_keywords = keyword_service.get_all_keywords_by_topic_id(topic.id)

    persons = text_entity_extractor.extract_entities_with_label(text, "PER")
    locations = text_entity_extractor.extract_entities_with_label(text, "LOC")
    organizations = text_entity_extractor.extract_entities_with_label(text, "ORG")

    filtered_locations = [loc for loc in locations if loc in topic_keywords]
    filtered_organizations = [org for org in organizations if org in topic_keywords]

    all_excluded_entities = set(persons + filtered_locations + filtered_organizations)
    keywords = keyword_extractor.extract_keywords(text, topic_keywords)
    filtered_keywords = []


    for keyword in keywords:
        if keyword not in all_excluded_entities:
            filtered_keywords.append(keyword)

    analyzed_text = AnalyzedText(
        topic=topic,
        persons=persons,
        locations=filtered_locations,
        organizations=filtered_organizations,
        keywords=filtered_keywords
    )

    return analyzed_text