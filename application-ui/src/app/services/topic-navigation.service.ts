import {Injectable, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LocalStorageService} from './local-storage.service';
import {TopicService} from './topic.service';

@Injectable({
  providedIn: 'root'
})
export class TopicNavigationService {

  @Input()
  public selectedTopic: string = null;
  public readonly RECENT_NEWS = 'Останні новини';

  public getSelectedTopic() {
    return this.selectedTopic;
  }

  public selectTopic(topic: string): void {
    this.selectedTopic = topic;
  }

  public isSelected(topic: string): boolean {
    return this.selectedTopic === topic;
  }
}
