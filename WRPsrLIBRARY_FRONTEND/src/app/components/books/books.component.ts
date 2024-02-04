import { Component, OnInit } from '@angular/core';
import { catchError, of } from 'rxjs';
import { BooksService } from 'src/app/service/books.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css'],
})
export class BooksComponent implements OnInit {
  private tuttiLibriSubscription: Subscription | undefined;
  tuttiLibri: any[] = [];

  constructor(private booksService: BooksService) {}

  ngOnInit(): void {
    this.visualizzaTuttiLibri();
  }

  //PER CARICARE TUTTI I LIBRI
  private visualizzaTuttiLibri(): void {
    this.tuttiLibriSubscription = this.booksService
      .getLibri()
      .pipe(
        catchError((error) => {
          console.error('Errore durante il caricamento dei libri', error);
          alert('Si è verificato un errore, ricarica la pagina.');
          return of([]);
        })
      )
      .subscribe({
        next: (libri) => {
          this.tuttiLibri = libri;
          console.log('Tutti i libri', this.tuttiLibri);
        },
      });
  }

  ngOnDestroy(): void {
    if (this.tuttiLibriSubscription) {
      this.tuttiLibriSubscription.unsubscribe();
    }
  }
}
