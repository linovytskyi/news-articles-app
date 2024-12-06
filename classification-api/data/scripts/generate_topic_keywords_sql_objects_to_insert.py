from data.model.topic import Topic

topics = Topic.get_topic_objects_from_dataset('../processed/results.csv')

elements = []

for topic in topics:
    for keyword in topic.keywords:
        processed_keyword = keyword.replace("'", "ʼ")
        raw_element = f"""'topic_id': topic_id_map['{topic.name}'], 'value': '{processed_keyword}'"""
        element = "{" + raw_element + "}"
        elements.append(element)

print(",\n".join(elements))






