import {Component, HostListener, OnInit} from '@angular/core';
import {NewsFeed} from '../../models/news-feed/news-feed';
import {NewsFeedService} from '../../services/news-feed.service';
import {FeedArticle} from '../../models/news-feed/feed-article';
import {ActivatedRoute, Router} from '@angular/router';
import {TextUtilService} from '../../services/text-util.service';
import {NewsFeedRequest} from '../../models/dto/news-feed-request';
import {KeywordService} from '../../services/keyword.service';
import {TopKeywordRequest} from '../../models/dto/top-keyword-request';
import {TopicService} from '../../services/topic.service';
import {TopicsCount} from '../../models/dto/topics-count';
import {PieChartData} from '../../models/charts/pie-chart-data';
import {EchartsAdapterService} from '../../services/echarts-adapter.service';
import {EChartsOption} from 'echarts';

@Component({
  selector: 'app-news-feed',
  templateUrl: 'news-feed.component.html',
  styleUrl: 'news-feed.component.css'
})
export class NewsFeedComponent implements OnInit {

  public newsFeed: NewsFeed;
  public newsFeedRequest: NewsFeedRequest;
  public topKeywordRequest: TopKeywordRequest;
  public topKeywords: string[]
  public selectedTopic: string;

  public topTopicsChartOption: EChartsOption;

  private readonly AMOUNT_OF_SENTENCES_TO_MAKE_PARAGRAPH = 2;
  private readonly AMOUNT_OF_FEED_ARTICLES_ON_ONE_PAGE = 3;
  private readonly AMOUNT_OF_KEYWORDS_ON_LEFT_SIDEBAR = 10;

  constructor(private newsFeedService: NewsFeedService,
              private keywordService: KeywordService,
              private topicService: TopicService,
              private echartsService: EchartsAdapterService,
              private textUtilService: TextUtilService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  public ngOnInit(): void {
    this.newsFeedRequest = this.buildInitialFeedRequest();
    this.topKeywordRequest = this.buildInitialTopKeywordRequest();
    this.selectedTopic = null;

    this.uploadNewsFeed();
    this.loadTopKeywords();
    this.loadTopTopics();
  }

  public getSummaryParagraphs(article: FeedArticle): string[] {
    return this.textUtilService.getParagraphs(article.summary, this.AMOUNT_OF_SENTENCES_TO_MAKE_PARAGRAPH);
  }

  @HostListener('window:scroll', [])
  public onScroll(): void {
    if ((window.innerHeight + window.scrollY) >= document.documentElement.scrollHeight - 200) {
      this.uploadNewsFeed();
    }
  }

  private uploadNewsFeed() {
    this.newsFeedService.getNewsFeedForPage(this.newsFeedRequest).subscribe(feed => {
      this.handleNewsFeedPageResponse(feed);
    })
  }

  private reUploadNewsFeed() {
    this.newsFeed.feedArticles = [];
    this.newsFeedRequest.pageNumber = 0;
    this.scrollToTop();
    this.uploadNewsFeed();
    console.log("reuploaded")
  }

  private handleNewsFeedPageResponse(feed: NewsFeed) {
    if (this.newsFeed) {
      this.mergeNewsFeedPages(feed);
    } else {
      this.newsFeed = feed;
    }
    this.newsFeedRequest.pageNumber = feed.pageNumber + 1;
  }

  public mergeNewsFeedPages(newsFeed: NewsFeed) {
    newsFeed.feedArticles.forEach(feedArticle => this.newsFeed.feedArticles.push(feedArticle));
  }

  public loadTopKeywords() {
    this.keywordService.getTopKeywords(this.topKeywordRequest).subscribe(
      keywords => this.topKeywords = keywords
    )
  }

  public loadTopTopics(): void {
    this.topicService.getTopTopics().subscribe(topicsCount => {
      const topicNames = topicsCount.map(item => item.topic);
      this.topTopicsChartOption = this.echartsService.createOptionsForPieChart(null, topicNames, this.getPieChartDataForTopicsCountList(topicsCount))
    })
  }


  public navigateToArticlePage(articleId: number) {
    this.router.navigate(['/article', articleId]);
  }

  public topicChangedHandler(newTopic: string) {
    this.updateSelectedTopic(newTopic);
    this.resetKeywordSelection();
    this.reUploadNewsFeed();
    this.loadTopKeywords();
  }

  public handleKeywordSelection(keyword: string) {
    if (this.isKeywordSelected(keyword)) {
      this.removeKeywordFromSelection(keyword);
    } else {
      this.newsFeedRequest.keywords.push(keyword);
    }
    this.reUploadNewsFeed();
  }

  public isKeywordSelected(keyword: string) {
    const keywords = this.newsFeedRequest.keywords;
    return keywords.includes(keyword);
  }

  private removeKeywordFromSelection(keyword: string) {
    this.newsFeedRequest.keywords = this.newsFeedRequest.keywords.filter(k => k !== keyword);
  }

  private resetKeywordSelection() {
    this.newsFeedRequest.keywords = []
  }

  private scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  private updateSelectedTopic(topic: string) {
    this.selectedTopic = topic;
    this.newsFeedRequest.topic = topic;
    this.topKeywordRequest.topic = topic;
  }

  private getPieChartDataForTopicsCountList(topicsCount: TopicsCount[]): PieChartData[] {
    if (topicsCount && topicsCount.length > 0) {
      return topicsCount.map(item => this.buildRepresentationFromTopicsCount(item));
    }
    return [];
  }

  private buildRepresentationFromTopicsCount(topicsCount: TopicsCount): PieChartData {
    return new PieChartData(topicsCount.topic, topicsCount.count);
  }

  private buildInitialFeedRequest(): NewsFeedRequest {
    const request = new NewsFeedRequest()
    request.pageNumber = 0
    request.pageSize = this.AMOUNT_OF_FEED_ARTICLES_ON_ONE_PAGE;
    request.topic = null;
    request.keywords = []
    return request;
  }

  private buildInitialTopKeywordRequest(): TopKeywordRequest {
    const request = new TopKeywordRequest()
    request.amount = this.AMOUNT_OF_KEYWORDS_ON_LEFT_SIDEBAR;
    return request;
  }
}
