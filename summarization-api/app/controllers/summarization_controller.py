import logging

from fastapi import APIRouter
from pydantic import BaseModel

from app.models.summarized_text import SummarizedText
from app.dependencies import text_summarizer
from app.dependencies import title_generator

router = APIRouter(prefix='/summarize')
logger = logging.getLogger(__name__)

class TextData(BaseModel):
    text: str

@router.post("", response_model=SummarizedText)
def summarize_text(text_data: TextData):
    text = text_data.text
    logger.info(f"Received request to summarize text:\n {text}")
    title = title_generator.generate_title(text)
    summary = text_summarizer.summarize(text)

    summarized_text = SummarizedText(
        title=title,
        summary=summary
    )
    logger.info(f"Summarized text: {summarized_text}")

    return summarized_text

@router.post("/title", response_model=str)
def classify_text(text_data: TextData):
    text = text_data.text
    logger.info(f"Received request to get title from text:\n {text}")
    return title_generator.generate_title(text)

@router.post("/sentences", response_model=str)
def classify_text(text_data: TextData):
    text = text_data.text
    logger.info(f"Received request to summarize text into sentences:\n {text}")
    return text_summarizer.summarize(text)