import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TopicNavigationService} from '../../../services/topic-navigation.service';
import {TopicService} from '../../../services/topic.service';

@Component({
  selector: 'app-topic-selection',
  templateUrl: './topic-selection.component.html',
  styleUrl: './topic-selection.component.css'
})
export class TopicSelectionComponent implements OnInit {

  @Input()
  public selectedTopic: string = null;
  public topics: string[] = []

  @Output()
  public topicSelectedEmitter = new EventEmitter<string>;

  constructor(private topicService: TopicService,
              private topicSelectionService: TopicNavigationService) {
  }

  public ngOnInit(): void {
    let topics = this.topicService.getSavedTopics();

    if (!topics) {
      this.topicService.getAllTopics().subscribe(topics => {
        this.topics = topics;
        this.topicService.setSavedTopics(topics);
        this.selectTopic(this.selectedTopic);
      })
    } else {
      this.topics = topics;
      this.selectTopic(this.selectedTopic);
    }
  }

  public selectTopic(topic: string) {
     if (topic === this.topicSelectionService.RECENT_NEWS) {
       topic = null;
     }

     if (topic !== this.getSelectedTopic()) {
       this.topicSelectionService.selectTopic(topic);
       this.topicSelectedEmitter.emit(topic)
     }
  }

  public isSelected(topic: string) {
    if (topic === this.topicSelectionService.RECENT_NEWS) {
      topic = null;
    }

    return this.topicSelectionService.isSelected(topic);
  }

  public getSelectedTopic(): string {
    return this.topicSelectionService.getSelectedTopic();
  }
}
