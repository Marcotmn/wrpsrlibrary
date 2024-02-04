import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ActivatedRoute } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class MylibraryService {
  baseURL = environment.baseURL;

  constructor(private http: HttpClient, private route: ActivatedRoute) {}

  //METODO AGGIUNGERE LIBRO ALLA LIBRERIA PERSONALE
  aggiungiLibro(idLibro: string): Observable<any> {
    return this.http
      .post(`${this.baseURL}libreria-personale/aggiungi/${idLibro}`, {})
      .pipe(
        catchError((error: any) => {
          console.error(
            "Errore durante l'aggiunta del libro alla libreria personale",
            error
          );
          throw error;
        })
      );
  }

  //METODO AGGIUNGERE LIBRO ALLA LIBRERIA PERSONALE
  cancellaLibro(idLibro: string): Observable<any> {
    return this.http
      .delete<any>(`${this.baseURL}libreria-personale/cancella/${idLibro}`)
      .pipe(
        catchError((error: any) => {
          console.error(
            'Errore durante la rimozione del libro dalla libreria personale',
            error
          );
          throw error;
        })
      );
  }

  // METODO PER RECUPERARE TUTTA LA LIBRERIA PERSONALE
  getLibreriaPersonale(): Observable<any[]> {
    return this.http
      .get<any[]>(`${this.baseURL}libreria-personale/elenco`)
      .pipe(
        catchError((error: any) => {
          console.error(
            'Errore durante il recupero della libreria personale',
            error
          );
          throw error;
        })
      );
  }
}
