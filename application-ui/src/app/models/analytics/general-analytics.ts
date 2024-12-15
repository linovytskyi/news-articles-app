import {TopicsCount} from './topics-count';
import {KeywordCount} from './keyword-count';
import {SourceCount} from './source-count';

export class GeneralAnalytics {
  amountOfArticles: number;
  topicsCounts: TopicsCount[];
  keywordCounts: KeywordCount[];
  sourceCounts: SourceCount[];
}
