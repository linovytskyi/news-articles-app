import logging

from torch.ao.quantization.pt2e.port_metadata_pass import logger

from ml.title.title_generation_model import load_model


class TitleGenerator:

    def __init__(self):
        self.model, self.tokenizer, self.device = load_model()
        self.logger = logging.getLogger(__name__)


    def generate_title(self, text: str):
        self.logger.info("Generating title from text...")
        inputs = self.tokenizer(text, return_tensors="pt", max_length=512, truncation=True).to(self.device)
        summary_ids = self.model.generate(inputs["input_ids"], max_length=50, min_length=30, length_penalty=2.0, num_beams=4,
                                 early_stopping=True)
        title = self.tokenizer.decode(summary_ids[0], skip_special_tokens=True, clean_up_tokenization_spaces=False)
        self.logger.info(f"Generated title:\n{title}")
        return title