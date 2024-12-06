import ast
import pandas as pd

class Topic:

    def __init__(self, name, keywords):
        self.name = name
        self.keywords = keywords

    @staticmethod
    def get_topic_objects_from_dataset(dataset_location):
        df = pd.read_csv(dataset_location)
        label_list = df['article_type'].unique()

        topics = []

        for label in label_list:
            df_filtered = df[df['article_type'].isin([label])]
            df_filtered.loc[:, 'keywords'] = df_filtered['keywords'].apply(ast.literal_eval)

            all_keywords = set()

            for i in range(len(df_filtered)):
                keywords_array = df_filtered['keywords'].iloc[i]
                for j in range(len(keywords_array)):
                    all_keywords.add(keywords_array[j])

            topic = Topic(label, all_keywords)
            topics.append(topic)

        return topics