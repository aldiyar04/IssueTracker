import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap, catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { ApiConstants } from 'src/api-constants';
import { UserLoginRequest } from '../interface/user-login-request';
import { UserLoginResponse } from '../interface/user-login-response';
import { Utils } from '../utils';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login$ = (userLoginReq: UserLoginRequest) => <Observable<UserLoginResponse>>
  this.http.post(`${ApiConstants.API_HOST}/signin`, userLoginReq)
  .pipe(
    tap(console.log),
    catchError(Utils.handleErrorResponse)
  );

  logout$ = this.http.delete(`${ApiConstants.API_HOST}/signout`)
  .pipe(
    tap(console.log),
    catchError(Utils.handleErrorResponse)
  );
}
