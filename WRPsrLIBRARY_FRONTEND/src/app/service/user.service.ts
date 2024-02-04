import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Token } from '@angular/compiler';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  baseURL = environment.baseURL;

  constructor(private http: HttpClient) {}

  // RECUPERIAMO LE INFO UTENTE CORRENTE DECODIFICANDO IL TOKEN
  getCurrentUser(): Observable<any> {
    const token = localStorage.getItem('jwtToken');
    if (token) {
      const user = this.decodeToken(token);
      return new Observable((observer) => {
        observer.next(user);
        observer.complete();
      });
    } else {
      throw new Error('Token non trovato.');
    }
  }

  // METODO PER DECODIFICARE IL TOKEN
  private decodeToken(token: string): any {
    const tokenPayload = JSON.parse(atob(token.split('.')[1]));
    return tokenPayload;
  }
}
