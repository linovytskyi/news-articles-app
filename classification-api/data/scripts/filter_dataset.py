import pandas as pd

df = pd.read_csv('../raw/results.csv')
values_to_remove = ['ТСН. Тиждень', 'ТСН. 19:30', 'ТСН.Cпецвипуск', 'Вона', 'Консервація і варення', 'Другі страви', 'Підбірки рецептів']

df_filtered = df[~df['article_type'].isin(values_to_remove)]

df_filtered.to_csv('../processed/filtered_file.csv', index=False)
