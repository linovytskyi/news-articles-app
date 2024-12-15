import {Component, OnInit} from '@angular/core';
import {ArticleService} from '../../../services/article.service';
import {ActivatedRoute} from '@angular/router';
import {Article} from '../../../models/article/article';
import {KeywordType} from '../../../models/article/keyword-type';
import {TextUtilService} from '../../../services/text-util.service';
import {UserService} from '../../../services/user.service';
import {ToastService} from '../../../services/toast.service';
import {HttpErrorResponse} from '@angular/common/http';

export enum TextViewOption {
  FULL_TEXT,
  SUMMARY
}

@Component({
  selector: 'app-article-page',
  templateUrl: './article-page.component.html',
  styleUrl: './article-page.component.css'
})
export class ArticlePageComponent implements OnInit {

  public article: Article;
  public selectedTextViewOption = TextViewOption.FULL_TEXT;

  private readonly AMOUNT_OF_SENTENCES_TO_MAKE_PARAGRAPH = 3;

  constructor(private articleService: ArticleService,
              private textUtilService: TextUtilService,
              private userService: UserService,
              private toasterService: ToastService,
              private router: ActivatedRoute) {
  }

  public ngOnInit(): void {
    this.router.params.subscribe(params => {
      this.articleService.getArticleById(+params['id']).subscribe(article => {
        this.article = article;
        console.log(article);
      })
    })
  }

  public getTextParagraphs(article: Article): string[] {
    if (this.selectedTextViewOption === TextViewOption.FULL_TEXT) {
      return this.textUtilService.getParagraphs(article.text, this.AMOUNT_OF_SENTENCES_TO_MAKE_PARAGRAPH);
    }
    return this.textUtilService.getParagraphs(article.summary, this.AMOUNT_OF_SENTENCES_TO_MAKE_PARAGRAPH);
  }

  public getFilteredArticleKeywordsByType(type: KeywordType) {
    return this.article.keywords.filter(keyword => keyword.type === type);
  }

  public saveArticle() {
    this.userService.saveArticle(this.article.id).subscribe(
      res => {
        this.toasterService.show('Новина була додана до списку "Збережене"', 'success');
      }, error => {
        this.handleErrorSave(error);
      }
    )
  }

  private handleErrorSave(error: HttpErrorResponse): void {
    if (error.status === 409) {
      this.toasterService.show('Новина вже додана до списку "Збережене"', 'warning');
    }
  }

  protected readonly KeywordType = KeywordType;
  protected readonly TextViewOptions = TextViewOption;
  protected readonly Object = Object;
}
