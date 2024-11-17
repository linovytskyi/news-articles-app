import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Article} from '../models/article/article';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private http: HttpClient) {
  }

  private readonly ARTICLE_API = "http://localhost:8081/article"

  public getArticleById(id: number): Observable<Article> {
    return this.http.get<Article>(this.ARTICLE_API + `/${id}`);
  }
}
