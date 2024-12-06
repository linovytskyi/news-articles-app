import re
import pandas as pd

tsn = pd.read_csv('../raw/tsn.csv')
unian = pd.read_csv("../raw/unian.csv")

filtered_tsn = tsn[tsn['article_type'] == "Проспорт"]

tsn_rename_map = {
    "Проспорт": "Спорт",
    "Гламур": "Зірки",
    "Гроші": "Економіка",
    "Фінанси": "Економіка",
    "Наука та IT": "Наука",
    "Здоров’я": "Здоров`я",
}

tsn['article_type'] = tsn['article_type'].replace(tsn_rename_map)

unian_rename_map = {
    "Вибори в США 2024": "Політика",
    "Футбол": "Спорт",
    "Бокс": "Спорт",
    "Мода": "Різне",
    "Шоу": "Шоу-бізнес",
    "Куди податися": "Різне",
    "Фотозвіти": "Різне",
    "Комунікації": "Різне",
    "Музика": "Різне",
    "Залізо": "Різне",
    "Інше": "Різне",
    "Софт": "Різне",
    "Стосунки": "Різне",
    "Рецепти": "Корисні статті",
    "Поради туристам": "Корисні статті",
    "Екологія": "Різне",
    "Сад-город": "Різне",
    "Гаджети": "Різне",
    "Новини туризму": "Різне",
    "Здоров'я": "Здоров`я",
    "Інциденти": "Різне",
    "Свята": "Різне",
    "Агро": "Різне",
    "Цікавинки": "Різне",
    "Транспорт": "Різне",
    "Думки": "Різне",
    "Лайфаки": "Корисні статті",
    "Ігри": "Різне",
    "Кіно": "Різне"
}

unian['article_type'] = unian['article_type'].replace(unian_rename_map)

key_topics = ['Війна', 'Погода', 'Україна', 'Наука',
              'Економіка', 'Зброя', 'Здоров`я', 'Корисні статті',
              'Астрологія', 'Світ', 'Енергетика', 'Спорт',
              'Політика', 'Шоу-бізнес', 'Зірки', 'Різне']
tsn = tsn[tsn['article_type'].isin(key_topics)]
unian = unian[unian['article_type'].isin(key_topics)]

combined = pd.concat([tsn, unian], ignore_index=True)
combined = combined.drop_duplicates(subset=['title'])

def clean_text(text):
    text = re.sub(r'Nbsp', ' ', text)
    text = re.sub(r'\u00a0', ' ', text)
    text = re.sub(r'\u200e|Lrm', '', text)
    text = re.sub(r'\.(\S)', r'. \1', text)
    text = re.sub(r'Читайте також.*', '', text)
    return text.strip()

combined['text'] = combined['text'].apply(clean_text)

max_entries = 2000
min_entries = 500

balanced_combined = combined.groupby('article_type').apply(
    lambda x: x.sample(
        n=min(max_entries, max(min_entries, len(x))),
        replace=len(x) < min_entries,
        random_state=42
    )
).reset_index(drop=True)

balanced_combined.to_csv('../processed/results.csv', index=False)
print(balanced_combined['article_type'].value_counts())