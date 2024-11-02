import logging

import uvicorn
from fastapi import FastAPI
from app.controllers import topic_router, keywords_router, classification_router, analyzer_router


logging.basicConfig(
    level=logging.INFO,  # Set the logging level
    format="%(asctime)s [%(levelname)s] %(name)s - %(message)s",
)

app = FastAPI()

# Include the router
app.include_router(topic_router.router)
app.include_router(keywords_router.router)
app.include_router(classification_router.router)
app.include_router(analyzer_router.router)

if __name__ == "__main__":
    uvicorn.run("app.main:app", host="127.0.0.1", port=8000, reload=True)
