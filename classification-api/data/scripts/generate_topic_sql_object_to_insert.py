from data.scripts.topic import Topic

topics = Topic.get_topic_objects_from_dataset('../processed/filtered_file.csv')

elements = []

for topic in topics:
    raw_element = f"""'name': '{topic.name}', 'created_date': datetime.utcnow()"""
    element = "{" + raw_element + "}"

    elements.append(element)

print(",\n".join(elements))