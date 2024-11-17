import {Component, OnInit} from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import {AppModule} from './app.module';
import {NgClass, NgForOf} from '@angular/common';

export enum NavigationOptions {
  FEED="feed",
  SEARCH="search",
  ANALYTICS="analytics"
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent implements OnInit {
  title = 'application-ui';
  selectedNavigation: string;

  constructor(private router: Router) {
  }

  public ngOnInit(): void {
    this.navigateTo(NavigationOptions.FEED);
  }

  public navigateTo(option: NavigationOptions): void {
    this.router.navigate([option]);
    this.selectedNavigation = option;
  }

  public isSelected(option: NavigationOptions) {
    return this.selectedNavigation === option;
  }

  protected readonly NavigationOptions = NavigationOptions;
}
