class Article:

    def __init__(self, title, article_type, text, keywords, datetime, source, url, number_of_views, picture_link):
        self.title = title
        self.article_type = article_type
        self.text = text
        self.keywords = keywords
        self.create_at = datetime
        self.source = source
        self.url = url
        self.number_of_views = number_of_views
        self.picture_link = picture_link

    def to_dict(self):
        return {
            "title": self.title,
            "article_type": self.article_type,
            "text": self.text,
            "keywords": self.keywords,
            "datetime": self.create_at.isoformat() if hasattr(self.create_at, "isoformat") else str(self.create_at),
            "source": self.source,
            "url": self.url,
            "number_of_views": self.number_of_views,
            "picture_link": self.picture_link,
        }
