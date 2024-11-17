import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TopicsCount} from "../models/dto/topics-count";


@Injectable({
    providedIn: 'root'
})
export class TopicService {

    private readonly TOPICS_API = "http://localhost:8081/topic";

    constructor(private http: HttpClient) {
    }

    public getTopTopics(): Observable<TopicsCount[]> {
        return this.http.get<TopicsCount[]>(this.TOPICS_API + "/top");
    }
}
