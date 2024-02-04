import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthData } from './auth-data';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { BehaviorSubject, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserService } from '../service/user.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  baseURL = environment.baseURL;
  user!: AuthData;
  jwtHelper = new JwtHelperService();
  private authSubj = new BehaviorSubject<null | AuthData>(null);
  user$ = this.authSubj.asObservable();

  constructor(
    private http: HttpClient,
    private router: Router,
    private userService: UserService
  ) {}

  private isAuthenticated = new BehaviorSubject<boolean>(false);

  login(email: string, password: string): Observable<any> {
    const credentials = { email, password };
    return this.http
      .post<any>('http://localhost:3001/auth/login', credentials)
      .pipe(
        map((response) => {
          if (response.accessToken) {
            localStorage.setItem('token', response.accessToken);
            this.isAuthenticated.next(true);
          }
          return response;
        })
      );
  }

  logout() {
    this.authSubj.next(null);
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  //REGISTRAZIONE USER
  registrazioneUser(data: {
    nome: string;
    cognome: string;
    username: string;
    email: string;
    password: string;
  }): Observable<any> {
    return this.http.post(`${this.baseURL}auth/register-user`, data);
  }

  //REGISTRAZIONE ADMIN
  registrazioneAdmin(data: {
    nome: string;
    cognome: string;
    username: string;
    email: string;
    password: string;
  }): Observable<any> {
    return this.http.post(`${this.baseURL}auth/register-admin`, data);
  }

  getCurrentUserInfo(): Observable<any> {
    return this.userService.getCurrentUser();
  }

  getToken(): string | null {
    const utenteLoggato = localStorage.getItem('token');
    if (utenteLoggato) {
      const datiUtente: AuthData = JSON.parse(utenteLoggato);
      return datiUtente.accessToken;
    }
    return null;
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }
  isAdmin(): boolean {
    const token = localStorage.getItem('token');

    if (token) {
      const decodedToken = this.jwtHelper.decodeToken(token);
      return decodedToken.role === 'ADMIN';
    }

    return false;
  }
}
