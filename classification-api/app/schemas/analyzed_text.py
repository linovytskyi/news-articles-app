from typing import Any, List

from pydantic import BaseModel

from app.schemas.topic import TopicShort


class AnalyzedText(BaseModel):
    topic: TopicShort
    persons: List[str] = []  # Default to an empty list
    keywords: List[str] = []  # Default to an empty list

    class Config:
        orm_mode = True