import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { ApiConstants } from 'src/api-constants';
import { User } from '../interface/user';
import { UserSignupRequest } from '../interface/user-signup-request';
import { Utils } from '../utils';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly apiUrl = ApiConstants.API_HOST + '/users';

  constructor(private http: HttpClient) { }

  register$ = (userSignupReq: UserSignupRequest) => <Observable<User>>
  this.http.post<User>(this.apiUrl, userSignupReq)
  .pipe(
    tap(console.log),
    catchError(Utils.handleErrorResponse)
  );
}
