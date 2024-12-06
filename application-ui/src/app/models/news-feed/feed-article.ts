import {Source} from '../article/source';
import {ShortFeedArticle} from './short-feed-article';

export class FeedArticle {
  id: number;
  title: string;
  topic: string;
  pictureUrl: string;
  postedAt: string;
  source: Source;
  summary: string;
  topicArticles: ShortFeedArticle[]
  keywords: string[]
}
