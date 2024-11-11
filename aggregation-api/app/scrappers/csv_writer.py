import csv
import os

from custom_logging import logger


class CSVWriter:
    def __init__(self, filename, fieldnames):
        self.filename = filename
        self.fieldnames = fieldnames
        self.logger = logger

    def write_article_to_csv(self, article):
        file_exists = os.path.exists(self.filename)

        with open(self.filename, mode='a', newline='', encoding='utf-8') as file:
            writer = csv.DictWriter(file, fieldnames=self.fieldnames)
            if not file_exists:
                writer.writeheader()


            data = {field: getattr(article, field) for field in self.fieldnames}
            writer.writerow(data)

        logger.info(f"Objects have been written to '{self.filename}'")