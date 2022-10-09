import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { NotifierService } from 'angular-notifier';
import { UserSignupRequest } from '../interface/user-signup-request';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  constructor(private userService: UserService, private notifier: NotifierService, private router: Router) {}

  ngOnInit(): void {}

  registerUser(signupForm: NgForm) {
    this.userService.register$(signupForm.value as UserSignupRequest)
    .subscribe({
      next: userResponse => {
        signupForm.reset();
        this.router.navigate(['/login']);
        this.notifier.notify('success', 'You registered successfully!');
      },
      error: error => {
        this.notifier.notify('error', error.message);
      }
    });
  }
}
