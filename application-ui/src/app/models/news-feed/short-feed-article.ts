import {Source} from '../article/source';

export class ShortFeedArticle {
  id: number;
  title: string;
  topic: string;
  pictureUrl: string;
  postedAt: Date;
  source: Source;
}
