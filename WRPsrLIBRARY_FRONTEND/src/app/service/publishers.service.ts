import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class PublishersService {
  baseURL = environment.baseURL;

  constructor(private http: HttpClient) {}

  // RECUPERA EDITORI
  getEditori(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseURL}editori/elenco-editori`).pipe(
      catchError((error: any) => {
        console.error('Errore durante il recupero degli editori:', error);
        throw error;
      })
    );
  }

  // UPLOAD PUBLISHER
  uploadEditore(libroData: any): Observable<any> {
    return this.http.post(`${this.baseURL}admin/upload-publisher`, libroData);
  }
}
