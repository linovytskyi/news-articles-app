import logging
from typing import List

from fastapi import APIRouter
from fastapi import Response
from fastapi.params import Depends

from app.dependencies import get_topic_service
from app.schemas.topic import Topic, TopicKeyword
from app.services.topic_service import TopicService

router = APIRouter(prefix='/topic')
logger = logging.getLogger(__name__)

@router.get("", response_model=List[Topic])
def get_add_topics(topic_service: TopicService = Depends(get_topic_service)):
    logger.info("Received request to get all topics")
    all_topics = topic_service.get_all_topics()
    return all_topics

@router.get("/{topic_id}", response_model=Topic)
def get_topic_by_id(topic_id: int, topic_service: TopicService = Depends(get_topic_service)):
    logger.info(f"Received request to get all topic by id - {topic_id}")
    topic = topic_service.get_topic_by_id(topic_id)
    if topic is None:
        return Response(status_code=204)
    return topic

@router.get("/{topic_id}/keywords", response_model=List[TopicKeyword])
def get_topic_by_id(topic_id: int, topic_service: TopicService = Depends(get_topic_service)):
    logger.info(f"Received request to get all keywords of topic with id - {topic_id}")
    topic = topic_service.get_topic_by_id(topic_id)
    if topic is None:
        return Response(status_code=204)
    return topic.keywords

@router.get("/name/{name}", response_model=Topic)
def get_topic_by_name(name: str, topic_service: TopicService = Depends(get_topic_service)):
    logger.info(f"Received request to get all topic by id - {name}")
    topic = topic_service.get_topic_by_name(name)
    if topic is None:
        return Response(status_code=204)
    return topic