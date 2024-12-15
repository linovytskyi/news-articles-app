import {Component, OnInit} from '@angular/core';
import {AnalyticsService} from '../../../services/analytics.service';
import {TopicAnalytics} from '../../../models/analytics/topic-analytics';
import {EChartsOption} from 'echarts';
import {EchartsAdapterService} from '../../../services/echarts-adapter.service';
import {TopicsCount} from '../../../models/dto/topics-count';
import {PieChartData} from '../../../models/charts/pie-chart-data';
import {SourceCount} from '../../../models/analytics/source-count';
import {TopicService} from '../../../services/topic.service';
import {Source} from '../../../models/article/source';
import {SourceService} from '../../../services/source.service';
import {KeywordAnalytics} from '../../../models/analytics/keyword-analytics';
import {SourceAnalytics} from '../../../models/analytics/source-analytics';
import {GeneralAnalytics} from '../../../models/analytics/general-analytics';

export enum AnalyticsOptions {
  GENERAL = 'GENERAL',
  TOPIC = 'TOPIC',
  KEYWORD = 'KEYWORD',
  SOURCE = 'SOURCE'
}

@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrl: './analytics.component.css'
})
export class AnalyticsComponent implements OnInit {

  constructor(private analyticsService: AnalyticsService,
              private sourceService: SourceService,
              private topicsService: TopicService,
              private echartsService: EchartsAdapterService) {
  }

  public topKeywordsChartOptions: EChartsOption;
  public sourcesChartOptions: EChartsOption;
  public topicsChartOptions: EChartsOption;
  public topics: string[];
  public sources: Source[];

  public selectedAnalyticsOption: AnalyticsOptions;
  public selectedTopic = "";
  public selectedKeyword = "";
  public selectedSource = "";

  public generalAnalytics: GeneralAnalytics;
  public topicAnalytics: TopicAnalytics;
  public keywordAnalytics: KeywordAnalytics;
  public sourceAnalytics: SourceAnalytics;

  public ngOnInit(): void {
    this.topics = this.topicsService.getSavedTopics();
    this.sources = this.sourceService.getSavedSources();
    this.selectedAnalyticsOption = AnalyticsOptions.GENERAL;
    this.showGeneralAnalytics();
  }

  public handleAnalyticsOptionChange(): void {
    this.generalAnalytics = null;
    this.topicAnalytics = null;
    this.keywordAnalytics = null;
    this.sourceAnalytics = null;

    this.topicsChartOptions = null;
    this.topKeywordsChartOptions = null;
    this.sourcesChartOptions = null;
  }

  public showGeneralAnalytics(): void {
    this.analyticsService.getGeneralAnalytics().subscribe(
      res =>  {
        this.generalAnalytics = res
        this.topicsChartOptions = this.echartsService.createOptionsForPieChart(null, this.getTopicsNames(this.generalAnalytics.topicsCounts), this.getPieChartDataForTopicsCounts(this.generalAnalytics.topicsCounts))
        this.topKeywordsChartOptions = this.echartsService.createOptionsForKeywordsChart(this.generalAnalytics)
        this.sourcesChartOptions = this.echartsService.createOptionsForPieChart(null, this.getSourcesNames(this.generalAnalytics.sourceCounts), this.getPieChartDataForSourcesCounts(this.generalAnalytics.sourceCounts))
      }
    )
  }

  public showTopicAnalytics(): void {
    this.analyticsService.getTopicAnalytics(this.selectedTopic).subscribe(
      res =>  {
        this.topicAnalytics = res
        this.topKeywordsChartOptions = this.echartsService.createOptionsForKeywordsChart(this.topicAnalytics)
        this.sourcesChartOptions = this.echartsService.createOptionsForPieChart(null, this.getSourcesNames(this.topicAnalytics.sourceCounts), this.getPieChartDataForSourcesCounts(this.topicAnalytics.sourceCounts))
      }
    )
  }

  public showKeywordAnalytics(): void {
    this.analyticsService.getKeywordAnalytics(this.selectedKeyword).subscribe(
      res => {
        this.keywordAnalytics = res
        this.sourcesChartOptions = this.echartsService.createOptionsForPieChart(null, this.getSourcesNames(this.keywordAnalytics.sourceCounts), this.getPieChartDataForSourcesCounts(this.keywordAnalytics.sourceCounts))
        this.topicsChartOptions = this.echartsService.createOptionsForPieChart(null, this.getTopicsNames(this.keywordAnalytics.topicsCounts), this.getPieChartDataForTopicsCounts(this.keywordAnalytics.topicsCounts))
      }
    )
  }

  public showSourceAnalytics(): void {
    this.analyticsService.getSourceAnalytics(this.selectedSource).subscribe(
      res => {
        this.sourceAnalytics = res
        this.topKeywordsChartOptions = this.echartsService.createOptionsForKeywordsChart(this.sourceAnalytics)
        this.topicsChartOptions = this.echartsService.createOptionsForPieChart(null, this.getTopicsNames(this.sourceAnalytics.topicsCounts), this.getPieChartDataForTopicsCounts(this.sourceAnalytics.topicsCounts))
      }
    )
  }

  public shouldSubmitButtonBeDisabled(): boolean {
    if (this.selectedAnalyticsOption === AnalyticsOptions.GENERAL) {
      return false;
    }

    if (this.selectedAnalyticsOption === AnalyticsOptions.TOPIC && this.selectedTopic) {
      return false;
    }

    if (this.selectedAnalyticsOption === AnalyticsOptions.KEYWORD && this.selectedKeyword) {
      return false;
    }

    if (this.selectedAnalyticsOption === AnalyticsOptions.SOURCE && this.selectedSource) {
      return false;
    }

    return true;
  }

  private getPieChartDataForSourcesCounts(sourceCounts: SourceCount[]): PieChartData[] {
    if (sourceCounts && sourceCounts.length > 0) {
      return sourceCounts.map(item => this.buildRepresentationFromSourceCount(item));
    }
    return [];
  }

  private getPieChartDataForTopicsCounts(sourceCounts: TopicsCount[]): PieChartData[] {
    if (sourceCounts && sourceCounts.length > 0) {
      return sourceCounts.map(item => this.buildRepresentationFromTopicCount(item));
    }
    return [];
  }

  private buildRepresentationFromSourceCount(sourceCounts: SourceCount): PieChartData {
    return new PieChartData(sourceCounts.source, sourceCounts.count);
  }

  private buildRepresentationFromTopicCount(topicCount: TopicsCount): PieChartData {
    return new PieChartData(topicCount.topic, topicCount.count);
  }

  private getSourcesNames(sourcesCounts: SourceCount[]): string[] {
    if (!sourcesCounts || sourcesCounts.length === 0) {
      return [];
    }
    return sourcesCounts.map(sourceCount => sourceCount.source);
  }

  private getTopicsNames(topicsCounts: TopicsCount[]): string[] {
    if (!topicsCounts || topicsCounts.length === 0) {
      return [];
    }
    return topicsCounts.map(topicCount => topicCount.topic);
  }

  protected readonly AnalyticsOptions = AnalyticsOptions;
}
