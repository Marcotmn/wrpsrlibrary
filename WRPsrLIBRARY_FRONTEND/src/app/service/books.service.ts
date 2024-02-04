import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ActivatedRoute } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class BooksService {
  baseURL = environment.baseURL;

  constructor(private http: HttpClient, private route: ActivatedRoute) {}

  //RECUOERA TUTTI I LIBRI IN DB
  getLibri(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseURL}libri/elenco`).pipe(
      catchError((error: any) => {
        console.error('Errore durante il recupero dei libri', error);
        throw error;
      })
    );
  }

  // RECUPERA UN LIBRO SPECIFICO PER ID
  getLibro(idLibro: string): Observable<any> {
    return this.http.get<any>(`${this.baseURL}libri/${idLibro}`).pipe(
      catchError((error: any) => {
        console.error('Errore durante il recupero del libro', error);
        throw error;
      })
    );
  }
}
