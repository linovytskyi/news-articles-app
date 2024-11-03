import logging

from fastapi import APIRouter
from fastapi.params import Depends
from pydantic import BaseModel
from app.dependencies import text_classifier, get_topic_service
from app.schemas.topic import Topic
from app.services.topic_service import TopicService

router = APIRouter(prefix='/classify')
logger = logging.getLogger(__name__)

class TextData(BaseModel):
    text: str

@router.post("/topic", response_model=Topic)
def classify_text(text_data: TextData,
                  topic_service: TopicService = Depends(get_topic_service)):
    logger.info(f"Received request to classify text:\n {text_data.text}")
    topic_name = text_classifier.classify_topic(text_data.text)
    topic = topic_service.get_topic_by_name(topic_name)
    return topic