import {Injectable} from '@angular/core';
import {FeedArticle} from '../models/news-feed/feed-article';
import {Article} from '../models/article/article';

@Injectable({
  providedIn: 'root'
})
export class TextUtilService {

  public getParagraphs(text: string , amountOfSentencesToMakeParagraph: number): string[] {
    const sentences = text.split(".")
    const paragraphs = []

    let paragraph = ""

    for (let i = 0; i < sentences.length; i++) {
      const sentence = sentences[i]

      if (i === sentences.length - 1) {
        paragraph += sentence;
      } else {
        paragraph += sentence + ".";
      }

      if (i !== 0 && i % amountOfSentencesToMakeParagraph === 0) {
        paragraphs.push(paragraph);
        paragraph = ""
      }
    }
    return paragraphs
  }
}
