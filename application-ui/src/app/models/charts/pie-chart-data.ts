import {TopicsCount} from '../dto/topics-count';

export class PieChartData {
  name: any;
  value: any;

  constructor(topicsCount: TopicsCount) {
    this.name = topicsCount.topic;
    this.value = topicsCount.count;
  }
}
