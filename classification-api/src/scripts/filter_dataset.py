import pandas as pd


df = pd.read_csv('../src/data/results.csv')
values_to_remove = ['ТСН. Тиждень', 'ТСН. 19:30', 'ТСН.Cпецвипуск', 'Вона', 'Консервація і варення', 'Другі страви', 'Підбірки рецептів']

df_filtered = df[~df['article_type'].isin(values_to_remove)]

df_filtered.to_csv('filtered_file.csv', index=False)
df = pd.read_csv('../filtered_file.csv')
label_list = df['article_type'].unique()

print(label_list)

import ast

df_filtered = df[df['article_type'].isin(['Війна'])]

# Step 1: Use .loc[] to safely modify the column
df_filtered.loc[:, 'keywords'] = df_filtered['keywords'].apply(ast.literal_eval)

all_keywords = set()

for i in range(len(df_filtered)):
    keywords_array = df_filtered['keywords'].iloc[i]
    for j in range(len(keywords_array)):
        all_keywords.add(keywords_array[j])

print(all_keywords)

