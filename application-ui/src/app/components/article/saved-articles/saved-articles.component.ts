import {Component, OnInit} from '@angular/core';
import {UserService} from '../../../services/user.service';
import {UserSavedArticle} from '../../../models/user/user-saved-article';
import {SearchArticle} from '../../../models/search/search-article';
import {Router} from '@angular/router';
import {ToastService} from '../../../services/toast.service';

@Component({
  selector: 'app-saved-articles',
  templateUrl: './saved-articles.component.html',
  styleUrl: './saved-articles.component.css'
})
export class SavedArticlesComponent implements OnInit {

  constructor(private userService: UserService,
              private toasterService: ToastService,
              private router: Router) {
  }

  public savedArticles: SearchArticle[]

  public ngOnInit(): void {
    this.userService.getSavedArticles().subscribe(
      res => this.savedArticles = res
    )
  }

  public navigateToArticle(articleId: number): void {
    this.router.navigate(['/article', articleId]);
  }

  public deleteArticle(articleId: number): void {
    this.userService.deleteSavedArticle(articleId).subscribe(
      res => {
        this.toasterService.show('Новина була видалена зі списку "Збережене"', 'success')
        this.savedArticles = this.savedArticles.filter(article => article.id !== articleId)
      }
    )
  }
}
