import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { ActivatedRoute } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class GestioneLibriService {
  baseURL = environment.baseURL;

  constructor(private http: HttpClient, private route: ActivatedRoute) {}

  //METODO UPLOAD LIBRO
  uploadLibro(libroData: any): Observable<any> {
    return this.http.post(`${this.baseURL}admin/upload-libro`, libroData);
  }

  //MODIFICA LIBRO
  updateLibro(idLibro: string, libroData: any): Observable<any> {
    return this.http.put(
      `${this.baseURL}admin/modifica-libro/${idLibro}`,
      libroData
    );
  }

  //CANCELLA LIBRO
  deleteLibro(idLibro: string): Observable<any> {
    return this.http.delete(`${this.baseURL}admin/cancella-libro/${idLibro}`);
  }
}
