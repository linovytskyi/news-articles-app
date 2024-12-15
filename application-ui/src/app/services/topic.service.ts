import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TopicsCount} from "../models/dto/topics-count";


@Injectable({
    providedIn: 'root'
})
export class TopicService {

    private readonly TOPICS_API = "http://localhost:8081/topic";
    private ALL_TOPICS: string[]

    constructor(private http: HttpClient) {
    }

    public setSavedTopics(topics: string[]): void {
      this.ALL_TOPICS = topics;
    }

    public getSavedTopics() {
      return this.ALL_TOPICS;
    }

    public getAllTopics(): Observable<string[]> {
      return this.http.get<string[]>(this.TOPICS_API);
    }

    public getTopTopics(): Observable<TopicsCount[]> {
        return this.http.get<TopicsCount[]>(this.TOPICS_API + "/top");
    }
}
