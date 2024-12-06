CREATE TABLE sources (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL UNIQUE
);

CREATE INDEX idx_sources_name ON sources(name);

CREATE TABLE articles (
                          id SERIAL PRIMARY KEY,
                          original_title VARCHAR(255) NOT NULL,
                          title VARCHAR(255) NOT NULL,
                          topic VARCHAR(255) NOT NULL,
                          text TEXT,
                          summary TEXT,
                          posted_at TIMESTAMP,
                          source_id INTEGER NOT NULL,
                          original_url VARCHAR(255),
                          picture_url VARCHAR(255),
                          FOREIGN KEY (source_id) REFERENCES sources(id) ON DELETE CASCADE
);

CREATE INDEX idx_articles_source_id ON articles(source_id);
CREATE INDEX idx_articles_topic ON articles(topic);
CREATE INDEX idx_articles_created_at ON articles(posted_at);
CREATE INDEX idx_articles_title ON articles(title);

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
CREATE INDEX idx_article_keywords_type ON article_keywords(type);
CREATE INDEX idx_article_keywords_article_topic ON article_keywords(article_topic);

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE users_saved_articles (
                                id SERIAL PRIMARY KEY,
                                user_id INTEGER NOT NULL,
                                article_id INTEGER NOT NULL,
                                saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE
);

CREATE INDEX idx_saved_articles_user_id ON users_saved_articles(user_id);
CREATE INDEX idx_saved_articles_article_id ON users_saved_articles(article_id);
