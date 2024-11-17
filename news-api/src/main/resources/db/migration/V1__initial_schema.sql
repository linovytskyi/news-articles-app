CREATE TABLE sources (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL
);

CREATE INDEX idx_sources_name ON sources(name);

CREATE TABLE articles (
                          id SERIAL PRIMARY KEY,
                          original_title VARCHAR(255) NOT NULL,
                          title VARCHAR(255) NOT NULL,
                          topic VARCHAR(255) NOT NULL,
                          text TEXT,
                          summary TEXT,
                          created_at TIMESTAMP,
                          source_id INTEGER NOT NULL,
                          url VARCHAR(255),
                          picture_link VARCHAR(255),
                          FOREIGN KEY (source_id) REFERENCES sources(id) ON DELETE CASCADE
);

CREATE INDEX idx_articles_source_id ON articles(source_id);
CREATE INDEX idx_articles_topic ON articles(topic);
CREATE INDEX idx_articles_created_at ON articles(created_at);

-- Updated article_keywords table without a foreign key to a keywords table
CREATE TABLE article_keywords (
                                  id SERIAL PRIMARY KEY,
                                  article_id INTEGER NOT NULL,
                                  value VARCHAR(255) NOT NULL,
                                  type VARCHAR(50) NOT NULL,
                                  article_topic VARCHAR(50) NOT NULL,
                                  FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE
);

CREATE INDEX idx_article_keywords_article_id ON article_keywords(article_id);
CREATE INDEX idx_article_keywords_value ON article_keywords(value);
