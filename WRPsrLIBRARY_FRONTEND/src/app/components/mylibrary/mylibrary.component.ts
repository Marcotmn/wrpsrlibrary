import { Component, OnInit } from '@angular/core';
import { MylibraryService } from 'src/app/service/mylibrary.service';

@Component({
  selector: 'app-mylibrary',
  templateUrl: './mylibrary.component.html',
  styleUrls: ['./mylibrary.component.css'],
})
export class MylibraryComponent implements OnInit {
  libreriaPersonale!: any[];

  constructor(private myLibraryService: MylibraryService) {}

  ngOnInit(): void {
    this.caricaLibreriaPersonale();
  }

  //GET DELLA LIBRERIA PERSONALE
  caricaLibreriaPersonale() {
    this.myLibraryService.getLibreriaPersonale().subscribe(
      (data) => {
        this.libreriaPersonale = data;
      },
      (error) => {
        console.error(
          'Errore durante il recupero della libreria personale',
          error
        );
      }
    );
  }

  //AGGIUNGI LIBRI ALLA LIBRERIA PERSONALE
  aggiungiLibro(idLibro: string) {
    this.myLibraryService.aggiungiLibro(idLibro).subscribe(
      () => {
        console.log('Libro aggiunto alla libreria personale con successo');
        this.caricaLibreriaPersonale();
      },
      (error) => {
        console.error(
          "Errore durante l'aggiunta del libro alla libreria personale",
          error
        );
      }
    );
  }

  //CANCELLA LIBRI DALLA LIBRERIA PERSONALE
  cancellaLibro(idLibro: string) {
    this.myLibraryService.cancellaLibro(idLibro).subscribe(
      () => {
        console.log('Libro rimosso dalla libreria personale con successo');
        this.caricaLibreriaPersonale();
      },
      (error) => {
        console.error(
          'Errore durante la rimozione del libro dalla libreria personale',
          error
        );
      }
    );
  }
}
