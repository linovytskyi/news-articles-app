import {TopicsCount} from './topics-count';
import {KeywordCount} from './keyword-count';

export class SourceAnalytics {
  source: string;
  amountOfArticles: number;
  topicsCounts: TopicsCount[];
  keywordCounts: KeywordCount[];
}
