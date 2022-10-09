import { Component } from '@angular/core';
import { NotifierService } from 'angular-notifier';
import { UserRole } from './enum/user-role.enum';
import { User } from './interface/user';
import { AuthService } from './service/auth.service';
import { StorageService } from './service/storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  isLoggedIn = false;
  user: User;
  readonly UserRole = UserRole;

  constructor(private storageService: StorageService, private authService: AuthService, private notifier: NotifierService) {
    this.isLoggedIn = storageService.isLoggedIn();
    this.user = storageService.getUser();
  }

  logout() {
    console.log('adfajdlkf');
    this.authService.logout$
    .subscribe({
      next: response => {
        this.storageService.clean();
        window.location.reload();
      },
      error: error => {
        this.notifier.notify('error', error.message);
      }
    })
  }
}
