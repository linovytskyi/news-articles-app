import {ArticleKeyword} from './article-keyword';
import {Source} from './source';


export class Article {
  id: number;
  originalTitle: string;
  title: string;
  topic: string;
  text: string;
  summary: string;
  createdAt: Date;
  source: Source;
  url: string;
  pictureLink: string;
  keywords: ArticleKeyword[];
}
