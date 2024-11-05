from pydantic.main import BaseModel


class SummarizedText(BaseModel):
    title: str
    summary: str