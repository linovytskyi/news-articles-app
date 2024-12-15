import {LocalStorageService} from "./local-storage.service";
import {User} from "../models/user/user";
import jwtDecode from "jwt-decode";
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {UserSavedArticle} from '../models/user/user-saved-article';
import {Observable} from 'rxjs';
import {SearchArticle} from '../models/search/search-article';

@Injectable({
  providedIn:'root'
})
export class UserService {

  private user: User;

  private readonly USERS_API = "http://localhost:8081/users";

  constructor(private localStorageService: LocalStorageService,
              private httpClient: HttpClient) {
  }

  public getCurrentUser(): User {
    if (!this.user) {
      this.setUser(this.localStorageService.getUser());
    }
    return this.user;
  }

  public setUser(user: User): void {
    this.localStorageService.saveUser(user);
    this.user = user;
  }

  public buildUserFromToken(token: any): User {
    const decodedToken = jwtDecode(token);
    const user = new User();
    // @ts-ignore
    user.email = decodedToken.sub;
    // @ts-ignore
    user.id = decodedToken.id;
    return user;
  }

  public saveArticle(articleId: number): Observable<void> {
    return this.httpClient.post<void>(this.USERS_API + "/article", articleId);
  }

  public getSavedArticles(): Observable<SearchArticle[]> {
    return this.httpClient.get<SearchArticle[]>(this.USERS_API + "/article")
  }

  public deleteSavedArticle(savedArticleId: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.USERS_API}/article/${savedArticleId}`);
  }
}

