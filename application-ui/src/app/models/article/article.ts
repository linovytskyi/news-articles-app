import {ArticleKeyword} from './article-keyword';
import {Source} from './source';


export class Article {
  id: number;
  originalTitle: string;
  title: string;
  topic: string;
  text: string;
  summary: string;
  postedAt: Date;
  source: Source;
  originalUrl: string;
  pictureUrl: string;
  keywords: ArticleKeyword[];
}
