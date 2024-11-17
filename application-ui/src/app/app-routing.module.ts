import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {NewsFeedComponent} from './components/news-feed/news-feed.component';
import {ArticlePageComponent} from './components/article/article-page/article-page.component';
import {SearchComponent} from './components/search/search/search.component';
import {AnalyticsComponent} from './components/analytics/analytics/analytics.component';

export const routes: Routes = [
  {path: 'feed', component: NewsFeedComponent},
  {path: 'article/:id', component: ArticlePageComponent},
  {path: 'search', component: SearchComponent},
  {path: 'analytics', component: AnalyticsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
