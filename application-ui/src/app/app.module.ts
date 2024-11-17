import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {ArticlePageComponent} from './components/article/article-page/article-page.component';
import {SearchComponent} from './components/search/search/search.component';
import {AnalyticsComponent} from './components/analytics/analytics/analytics.component';
import {TopicSelectionComponent} from './components/topic-selection/topic-selection/topic-selection.component';
import {NewsFeedComponent} from './components/news-feed/news-feed.component';
import {NgxEchartsDirective, NgxEchartsModule} from 'ngx-echarts';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    NewsFeedComponent,
    ArticlePageComponent,
    SearchComponent,
    AnalyticsComponent,
    TopicSelectionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgxEchartsModule.forRoot({
      echarts: () => import('echarts')
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
