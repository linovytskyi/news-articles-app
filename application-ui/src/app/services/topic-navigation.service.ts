import {Injectable, Input} from '@angular/core';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class TopicNavigationService {

  @Input()
  public selectedTopic: string = null;
  public readonly RECENT_NEWS = 'Останні новини';

  private topics: string[] = ['Останні новини', 'Війна', 'Україна']

  constructor(private router: Router) {
  }

  public getTopics() {
    return this.topics;
  }

  public getSelectedTopic() {
    return this.selectedTopic;
  }

  public selectedTopicIsRecentNews(): boolean {
    return this.selectedTopic === null;
  }

  public selectTopic(topic: string): void {
    this.selectedTopic = topic;
  }

  public isSelected(topic: string): boolean {

    
    return this.selectedTopic === topic;
  }
}
