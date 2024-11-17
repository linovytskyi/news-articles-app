import {Source} from '../article/source';
import {ShortFeedArticle} from './short-feed-article';

export class FeedArticle {
  id: number;
  title: string;
  topic: string;
  pictureLink: string;
  createdAt: string;
  source: Source;
  summary: string;
  topicArticles: ShortFeedArticle[]
  keywords: string[]
}
