import { Component } from '@angular/core';
import {SearchRequest} from '../../../models/dto/search-request';
import {SearchService} from '../../../services/search.service';
import {SearchResponse} from '../../../models/dto/search-response';

export enum DateSearchOption {
  DATE,
  DATE_RANGE
}

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent {

  dateSearchOption = DateSearchOption.DATE

  searchRequest: SearchRequest = {
    text: '',
    topic: '',
    keywords: [],
    source: '',
    startDate: null,
    endDate: null,
    searchDate: null,
    pageNumber: 0,
    pageSize: 5
  };

  keywordsInput: string = '';
  searchResults: SearchResponse;

  constructor(private searchService: SearchService) {}

  addKeyword(): void {
    if (this.keywordsInput.trim()) {
      this.searchRequest.keywords.push(this.keywordsInput.trim());
      this.keywordsInput = ''; // Clear the input after adding
    }
  }

  removeKeyword(keyword: string): void {
    this.searchRequest.keywords = this.searchRequest.keywords.filter(k => k !== keyword);
  }

  searchArticles(): void {
    console.log(this.searchRequest)
    this.searchService.searchArticles(this.searchRequest).subscribe(
      res => this.searchResults = res
    )
  }

  protected readonly DateSearchOption = DateSearchOption;
}
