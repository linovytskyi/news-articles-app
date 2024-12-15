import {Component, OnInit} from '@angular/core';
import {SearchRequest} from '../../../models/dto/search-request';
import {SearchService} from '../../../services/search.service';
import {SearchResponse} from '../../../models/dto/search-response';
import {Router} from '@angular/router';
import {TopicService} from '../../../services/topic.service';
import {LocalStorageService} from '../../../services/local-storage.service';
import {Source} from '../../../models/article/source';
import {SourceService} from '../../../services/source.service';

export enum DateSearchOption {
  DATE,
  DATE_RANGE
}

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  public dateSearchOption = DateSearchOption.DATE
  public keywordsInput: string = '';
  public searchRequest: SearchRequest;
  public searchResponse: SearchResponse;
  public topicsToSearchFrom: string[];
  public sourcesToSearchFrom: Source[];

  constructor(private searchService: SearchService,
              private topicsService: TopicService,
              private sourceService: SourceService,
              private localStorageService: LocalStorageService,
              private router: Router) {}

  public ngOnInit(): void {
    if (this.localStorageService.getSavedSearchRequest()) {
      this.searchRequest = this.localStorageService.getSavedSearchRequest();
      if (this.isNotEmpty(this.searchRequest)) {
        this.searchArticles();
      }
    } else {
      this.searchRequest = this.buildInitialSearchRequest();
    }
    this.topicsToSearchFrom = this.topicsService.getSavedTopics();
    this.sourcesToSearchFrom = this.sourceService.getSavedSources();
  }

  public searchArticles(): void {
    this.searchService.searchArticles(this.searchRequest).subscribe(
      res => this.handleSearchResponse(res)
    )
  }

  public addKeywordToSearch() {
    if (this.canKeywordBeAdded()) {
      this.searchRequest.keywords.push(this.keywordsInput)
      this.keywordsInput = ''
    }
  }

  public canKeywordBeAdded(): boolean {
    return this.keywordsInput && this.keywordsInput.trim().length > 2;
  }

  public removeKeywordFromSearch(keywordToRemove: string): void {
    this.searchRequest.keywords = this.searchRequest.keywords.filter(keyword => keyword != keywordToRemove);
  }

  private handleSearchResponse(searchResponse: SearchResponse) {
    this.searchResponse = searchResponse;
    this.localStorageService.saveSearchRequest(this.searchRequest);
  }

  public navigateToArticle(id: number) {
    this.router.navigate(['/article', id]);
  }

  public handleDateSearchOptionChange(): void {
    if (this.dateSearchOption === DateSearchOption.DATE_RANGE) {
      this.searchRequest.searchDate = null;
    } else {
      this.searchRequest.startDate = null;
      this.searchRequest.endDate = null;
    }
  }

  private buildInitialSearchRequest(): SearchRequest {
    return {
      text: "",
      topic: "",
      keywords: [],
      source: "",
      startDate: null,
      endDate: null,
      searchDate: null,
      pageNumber: 0,
      pageSize: 5
    }
  }

  public isNotEmpty(request: SearchRequest): boolean {
    if (!request) {
      return false;
    }

    return (
      !!request.text?.trim() ||
      !!request.topic?.trim() ||
      !!request.source?.trim() ||
      request.searchDate !== null ||
      request.startDate !== null ||
      request.endDate !== null ||
      (request.keywords && request.keywords.length > 0)
    );
  }


  private isEmpty(str: string) {
    return str === null && str.trim().length > 0;
  }

  protected readonly DateSearchOption = DateSearchOption;
}
