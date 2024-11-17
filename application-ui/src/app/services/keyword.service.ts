import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TopKeywordRequest} from '../models/dto/top-keyword-request';

@Injectable({
  providedIn: 'root'
})
export class KeywordService {

  constructor(private http: HttpClient) {
  }

  private readonly KEYWORD_API_URL = "http://localhost:8081/keyword"

  public getTopKeywords(request: TopKeywordRequest): Observable<string[]> {
    return this.http.post <string[]>(this.KEYWORD_API_URL + "/top", request)
  }
}
