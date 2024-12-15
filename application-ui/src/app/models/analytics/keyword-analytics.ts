import {TopicsCount} from './topics-count';
import {SourceCount} from './source-count';

export class KeywordAnalytics {
  keyword: string;
  amountOfArticles: number;
  topicsCounts: TopicsCount[];
  sourceCounts: SourceCount[];
}
