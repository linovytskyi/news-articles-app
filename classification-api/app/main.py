import logging

import uvicorn
from fastapi import FastAPI
from app.controllers import topic_controller, keywords_controller, classification_controller, analyzer_controller


logging.basicConfig(
    level=logging.INFO,  # Set the logging level
    format="%(asctime)s [%(levelname)s] %(name)s - %(message)s",
)

logging.getLogger('sqlalchemy.engine.Engine').disabled = True

app = FastAPI()

# Include the router
app.include_router(topic_router.router)
app.include_router(keywords_router.router)
app.include_router(classification_router.router)
app.include_router(analyzer_router.router)

if __name__ == "__main__":
    uvicorn.run("app.main:app", host="127.0.0.1", port=8000, reload=False, log_level="error")
