import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import {ToastService} from '../../services/toast.service';

@Component({
  selector: 'app-toast',
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.css'],
})
export class ToastComponent implements OnInit {
  toast: { message: string; type: string } | null = null;
  private subscription!: Subscription;

  constructor(private toastService: ToastService) {}

  ngOnInit(): void {
    this.subscription = this.toastService.toast$.subscribe((toast) => {
      this.toast = toast;
      setTimeout(() => this.closeToast(), 3000);
    });
  }

  closeToast() {
    this.toast = null;
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
