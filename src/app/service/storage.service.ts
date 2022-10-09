import { Injectable } from '@angular/core';
import { User } from '../interface/user';

const JWT_KEY = 'jwt-token'
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  clean(): void {
    window.localStorage.clear();
  }

  saveUser(user: User): void {
    window.localStorage.removeItem(USER_KEY);
    window.localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  getUser(): User {
    let user = window.localStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }
    return null;
  }

  saveJwt(jwt: string) {
    window.localStorage.removeItem(JWT_KEY);
    window.localStorage.setItem(JWT_KEY, jwt);
  }

  getJwt(): string {
    return window.localStorage.getItem(JWT_KEY);
  }

  isLoggedIn(): boolean {
    let user = window.localStorage.getItem(USER_KEY);
    if (user) {
      return true;
    }
    return false;
  }
}
