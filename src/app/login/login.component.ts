import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NotifierService } from 'angular-notifier';
import { UserLoginRequest } from '../interface/user-login-request';
import { UserLoginResponse } from '../interface/user-login-response';
import { AuthService } from '../service/auth.service';
import { StorageService } from '../service/storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService, private storageService: StorageService, private notifier: NotifierService) { }

  ngOnInit(): void {}

  login(loginForm: NgForm) {
    this.authService.login$(loginForm.value as UserLoginRequest)
    .subscribe({
      next: response => {
        let userLoginResp = <UserLoginResponse> response;
        this.storageService.saveJwt(userLoginResp.jwtToken);
        this.storageService.saveUser(userLoginResp.user);
        window.location.reload();
      },
      error: error => {
        this.notifier.notify('error', error.message);
      }
    });
  }
}
