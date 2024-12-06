import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TopicAnalytics} from '../models/analytics/topic-analytics';

@Injectable({
  providedIn: 'root'
})
export class AnalyticsService {
  constructor(private http: HttpClient) {
  }

  private readonly ANALYTICS_API = "http://localhost:8081/analytics";

  public getTopicAnalytics(topic: string): Observable<TopicAnalytics> {
    return this.http.get<TopicAnalytics>(this.ANALYTICS_API + `/topic?topic=${topic}`)
  }

}
