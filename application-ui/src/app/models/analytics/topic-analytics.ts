import {SourceCount} from './source-count';
import {KeywordCount} from './keyword-count';

export class TopicAnalytics {
  topic: string;
  amountOfArticles: number;
  sourceCounts: SourceCount[];
  keywordCounts: KeywordCount[];
}
