import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Source} from '../models/article/source';

@Injectable({
  providedIn: 'root'
})
export class SourceService {
  constructor(private http: HttpClient) {
  }

  private readonly SOURCES_API = "http://localhost:8081/source";
  private ALL_SOURCE: Source[];

  public getAllSource(): Observable<Source[]> {
    return this.http.get<Source[]>(this.SOURCES_API);
  }

  public setSavedSources(sources: Source[]): void {
    this.ALL_SOURCE = sources;
  }

  public getSavedSources(): Source[] {
    console.log('init!')
    return this.ALL_SOURCE;
  }
}
