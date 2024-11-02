from typing import List
from pydantic import BaseModel
from datetime import datetime

class TopicKeywordBase(BaseModel):
    value: str

class TopicKeywordCreate(TopicKeywordBase):
    pass

class Keyword(TopicKeywordBase):
    id: int
    topic_id: int

    class Config:
        orm_mode = True

class TopicKeyword(TopicKeywordBase):
    id: int

    class Config:
        orm_mode = True

class TopicBase(BaseModel):
    name: str

class TopicCreate(TopicBase):
    keywords: List[TopicKeywordCreate]

class TopicShort(TopicBase):
    id: int
    created_date: datetime

    class Config:
        orm_mode = True

class Topic(TopicShort):
    keywords: List[TopicKeyword] = []

    class Config:
        orm_mode = True
