import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TopicAnalytics} from '../models/analytics/topic-analytics';
import {KeywordAnalytics} from '../models/analytics/keyword-analytics';
import {SourceAnalytics} from '../models/analytics/source-analytics';
import {GeneralAnalytics} from '../models/analytics/general-analytics';

@Injectable({
  providedIn: 'root'
})
export class AnalyticsService {
  constructor(private http: HttpClient) {
  }

  private readonly ANALYTICS_API = "http://localhost:8081/analytics";

  public getGeneralAnalytics(): Observable<GeneralAnalytics> {
    return this.http.get<GeneralAnalytics>(this.ANALYTICS_API);
  }

  public getTopicAnalytics(topic: string): Observable<TopicAnalytics> {
    return this.http.get<TopicAnalytics>(this.ANALYTICS_API + `/topic?topic=${topic}`)
  }

  public getKeywordAnalytics(keyword: string): Observable<KeywordAnalytics> {
    return this.http.get<KeywordAnalytics>(this.ANALYTICS_API + `/keyword?keyword=${keyword}`)
  }

  public getSourceAnalytics(source: string): Observable<SourceAnalytics> {
    return this.http.get<SourceAnalytics>(this.ANALYTICS_API + `/source?source=${source}`)
  }
}
