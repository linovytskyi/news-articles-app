import logging
from typing import List

from fastapi import APIRouter
from fastapi.params import Depends
from fastapi import Response

from web.dependencies import get_keyword_service
from web.schemas.topic import Keyword
from web.repository.keyword_repository import KeywordRepository

router = APIRouter(prefix='/keyword')
logger = logging.getLogger(__name__)

@router.get("", response_model=List[Keyword])
def get_add_keywords(keyword_service: KeywordRepository = Depends(get_keyword_service)):
    logger.info("Received request to get all keywords")
    all_keywords = keyword_service.get_all_keywords()
    return all_keywords

@router.get("/{keyword_id}", response_model=Keyword)
def get_keyword_by_id(keyword_id: int,
                      keyword_service: KeywordRepository = Depends(get_keyword_service)):
    logger.info(f"Received request to get keyword by id {keyword_id}")
    keyword = keyword_service.get_keyword_by_id(keyword_id)
    if keyword is None:
        return Response(status_code=204)
    return keyword

@router.get("/value/{value}", response_model=List[Keyword])
def get_keyword_by_id(value: str,
                      keyword_service: KeywordRepository = Depends(get_keyword_service)):
    logger.info(f"Received request to get keyword by value {value}")
    keyword = keyword_service.get_keyword_by_value(value)
    if keyword is None:
        return Response(status_code=204)
    return keyword