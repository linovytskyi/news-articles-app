import {Component, OnInit} from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import {AppModule} from './app.module';
import {NgClass, NgForOf} from '@angular/common';
import {AuthService} from './services/auth.service';
import {LocalStorageService} from './services/local-storage.service';

export enum NavigationOptions {
  FEED="feed",
  SEARCH="search",
  ANALYTICS="analytics",
  SAVED="saved"
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent implements OnInit {
  title = 'application-ui';
  selectedNavigation: string;

  constructor(private router: Router,
              private authService: AuthService,
              private localStorage: LocalStorageService) {
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

  public isUserLoggedIn(): boolean {
    return !!this.localStorage.getAuthToken();
  }

  public logout(): void {
    this.authService.logout();
    this.router.navigate(['/auth']);
  }

  protected readonly NavigationOptions = NavigationOptions;
}
