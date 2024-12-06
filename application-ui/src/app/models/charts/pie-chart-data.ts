import {TopicsCount} from '../dto/topics-count';
import {SourceCount} from '../analytics/source-count';

export class PieChartData {
  name: any;
  value: any;

  constructor(name: any, value: any) {
    this.name = name
    this.value = value
  }
}
