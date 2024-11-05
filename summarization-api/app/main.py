import logging

import uvicorn
from fastapi import FastAPI
from app.controllers import summarization_controller


logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(levelname)s] %(name)s - %(message)s",
)

app = FastAPI()
app.include_router(summarization_controller.router)
if __name__ == "__main__":
    uvicorn.run("app.main:app", host="127.0.0.1", port=8001, reload=False, log_level="info")