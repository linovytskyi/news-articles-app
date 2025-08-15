# news-articles-web

Project: 
The project was a Master's Thesis focused on creating an aggregation system to gather news from various sources and analyze it using a custom AI model. The AI component was a fine-tuned XLM-RoBERTa language model trained on a specialized dataset generated via a custom parser. The model showcased capabilities in generating news headlines and classifying texts by identifying their respective topics.

The system architecture consisted of five microservices. The web server was developed using Java Spring Boot, while the aggregation and AI logic were implemented in Python, with FastAPI exposing specific endpoints. RabbitMQ enabled asynchronous communication between the aggregation service and the web server. The user interface was designed with Angular, and PostgreSQL was utilized as the database.

Responsibilities:
• Designed and implemented the system architecture. 
• Developed a custom parser to process various Ukrainian data sources. 
• Created a balanced dataset for training purposes. 
• Trained an XLM-Roberta language model on a custom dataset. 
• Developed an API capable of analyzing text and providing topic and title suggestions. 
• Built a web server using Spring Boot. 
• Designed and implemented a user interface using Angular. 
• Developed an aggregation API to consolidate data. 
• Implemented asynchronous communication between the aggregation API and the web server. 

Tools and Technologies:
Python, XLM-Roberta, PyTorch, FastAPI, Flask, SqlAlchemy, Spring Boot (Web, Security, Data JPA), PostgreSQL, JWT, Angular, HTML5, CSS3, RabbitMQ, PostgresSQL.
