import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {NewsFeed} from '../models/news-feed/news-feed';
import {NewsFeedRequest} from '../models/dto/news-feed-request';


@Injectable({
  providedIn: 'root'
})
export class NewsFeedService {
  constructor(private http: HttpClient) {
  }

  private readonly NEWS_FEED_API = "http://localhost:8081/news-feed";
  public getNewsFeedForPage(request: NewsFeedRequest): Observable<NewsFeed> {
    return this.http.post<NewsFeed>(this.NEWS_FEED_API, request);
  }
}
