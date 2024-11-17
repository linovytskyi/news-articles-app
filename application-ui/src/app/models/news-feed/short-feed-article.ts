import {Source} from '../article/source';

export class ShortFeedArticle {
  id: number;
  title: string;
  topic: string;
  pictureLink: string;
  createdAt: Date;
  source: Source;
}
