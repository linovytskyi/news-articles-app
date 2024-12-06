from typing import Any, List

from pydantic import BaseModel

from web.schemas.topic import TopicShort


class AnalyzedText(BaseModel):
    topic: TopicShort
    persons: List[str] = []
    locations: List[str] = []
    organizations: List[str] = []
    keywords: List[str] = []

    class Config:
        orm_mode = True