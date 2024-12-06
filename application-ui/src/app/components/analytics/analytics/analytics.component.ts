import {Component} from '@angular/core';
import {AnalyticsService} from '../../../services/analytics.service';
import {TopicAnalytics} from '../../../models/analytics/topic-analytics';
import {EChartsOption} from 'echarts';
import {EchartsAdapterService} from '../../../services/echarts-adapter.service';
import {TopicsCount} from '../../../models/dto/topics-count';
import {PieChartData} from '../../../models/charts/pie-chart-data';
import {SourceCount} from '../../../models/analytics/source-count';

@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrl: './analytics.component.css'
})
export class AnalyticsComponent {

  constructor(private analyticsService: AnalyticsService,
              private echartsService: EchartsAdapterService) {
  }

  public topKeywordsChartOptions: EChartsOption;
  public sourcesChartOptions: EChartsOption

  public selectedTopic: string;
  public topicAnalytics: TopicAnalytics;


  public showAnalytics(): void {
    this.analyticsService.getTopicAnalytics(this.selectedTopic).subscribe(
      res =>  {
        this.topicAnalytics = res
        this.topKeywordsChartOptions = this.echartsService.createOptionsForKeywordsChart(this.topicAnalytics)
        this.sourcesChartOptions = this.echartsService.createOptionsForPieChart(null, this.getSourceNames(this.topicAnalytics.mostPopularSources), this.getPieChartDataForSourceCountList(this.topicAnalytics.mostPopularSources))
      }

    )
  }

  private getPieChartDataForSourceCountList(sourceCounts: SourceCount[]): PieChartData[] {
    if (sourceCounts && sourceCounts.length > 0) {
      return sourceCounts.map(item => this.buildRepresentationFromTopicsCount(item));
    }
    return [];
  }

  private buildRepresentationFromTopicsCount(sourceCounts: SourceCount): PieChartData {
    return new PieChartData(sourceCounts.source, sourceCounts.count);
  }

  private getSourceNames(mostPopularSources: SourceCount[]): string[] {
    if (!mostPopularSources || mostPopularSources.length === 0) {
      return [];
    }
    return mostPopularSources.map(sourceCount => sourceCount.source);
  }

}
