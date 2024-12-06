import {SourceCount} from './source-count';
import {KeywordCount} from './keyword-count';

export class TopicAnalytics {
  topic: string;
  amountOfArticles: number;
  mostPopularSources: SourceCount[]
  mostPopularKeywords: KeywordCount[]
}
