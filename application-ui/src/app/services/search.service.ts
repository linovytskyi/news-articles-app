import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SearchRequest} from '../models/dto/search-request';
import {Observable} from 'rxjs';
import {SearchResponse} from '../models/dto/search-response';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  constructor(private http: HttpClient) {
  }

  private readonly SEARCH_API_URL = "http://localhost:8081/search";

  public searchArticles(searchRequest: SearchRequest): Observable<SearchResponse> {
    return this.http.post<SearchResponse>(this.SEARCH_API_URL, searchRequest)
  }

}
