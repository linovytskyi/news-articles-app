import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {NewsFeedComponent} from './components/news-feed/news-feed.component';
import {ArticlePageComponent} from './components/article/article-page/article-page.component';
import {SearchComponent} from './components/search/search/search.component';
import {AnalyticsComponent} from './components/analytics/analytics/analytics.component';
import {AuthPageComponent} from './components/auth/auth-page.component';
import {AuthGuard} from './auth.guard';
import {SavedArticlesComponent} from './components/article/saved-articles/saved-articles.component';

export const routes: Routes = [
  {path: 'auth', component: AuthPageComponent},
  {path: 'feed', component: NewsFeedComponent, canActivate: [AuthGuard]},
  {path: 'article/:id', component: ArticlePageComponent, canActivate: [AuthGuard]},
  {path: 'search', component: SearchComponent, canActivate: [AuthGuard]},
  {path: 'analytics', component: AnalyticsComponent, canActivate: [AuthGuard]},
  {path: 'saved', component: SavedArticlesComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
