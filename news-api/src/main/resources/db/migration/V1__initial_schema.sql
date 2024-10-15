CREATE TABLE articles (
                          id BIGSERIAL PRIMARY KEY,
                          title VARCHAR(255) NOT NULL,
                          text TEXT NOT NULL,
                          author VARCHAR(255),
                          source VARCHAR(255),
                          url VARCHAR(255),
                          type VARCHAR(100)
);

