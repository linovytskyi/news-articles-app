from sqlalchemy import Column, Integer, String, DateTime, ForeignKey
from sqlalchemy.orm import relationship
from datetime import datetime
from app.database import Base

class Topic(Base):
    __tablename__ = 'topics'

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(100), nullable=False, unique=True)
    created_date = Column(DateTime, default=datetime.utcnow)

    keywords = relationship("TopicKeyword", back_populates="topic", cascade="all, delete-orphan")

class TopicKeyword(Base):
    __tablename__ = 'topic_keywords'

    id = Column(Integer, primary_key=True, index=True)
    topic_id = Column(Integer, ForeignKey('topics.id'), nullable=False)
    value = Column(String(100), nullable=False)

    topic = relationship("Topic", back_populates="keywords")
