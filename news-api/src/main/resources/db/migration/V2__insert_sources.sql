ALTER TABLE sources
ADD column url VARCHAR(64);

INSERT INTO sources (name, url)
VALUES ('ТСН', 'https://tsn.ua/'),
       ('УНІАН', 'https://www.unian.ua/')

