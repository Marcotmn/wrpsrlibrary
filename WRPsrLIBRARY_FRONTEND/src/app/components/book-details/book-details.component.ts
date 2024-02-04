import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, catchError, throwError } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { BooksService } from 'src/app/service/books.service';
import { GestioneLibriService } from 'src/app/service/gestione-libri.service';
import { MylibraryService } from 'src/app/service/mylibrary.service';

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.css'],
})
export class BookDetailsComponent implements OnInit {
  private libroSubscription: Subscription | undefined;
  libro: any;
  isLibroNellaLibreria = false;

  constructor(
    private myLibraryService: MylibraryService,
    private booksService: BooksService,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
    private gestioneLibriService: GestioneLibriService
  ) {}

  ngOnInit(): void {
    this.visualizzaDettagliLibro();
    const idLibro = this.route.snapshot.paramMap.get('idLibro');
    if (idLibro) {
      this.checkLibroNellaLibreria(idLibro);
    }
  }

  //VISUALIZZA I DETTAGLI DEL LIBRO RECUPERANDO L'ID DALL'URL
  private visualizzaDettagliLibro(): void {
    const idLibro = this.route.snapshot.paramMap.get('idLibro');
    if (idLibro) {
      this.libroSubscription = this.booksService.getLibro(idLibro).subscribe({
        next: (libro) => {
          this.libro = libro;
          this.checkLibroNellaLibreria(idLibro);
        },
        error: (error) => {
          console.error(
            'Errore durante il recupero dei dettagli del libro',
            error
          );
        },
      });
    }
  }

  //CONTROLLA SE IL LIBRO è PRESENTE NELLA LIBRERIA
  private checkLibroNellaLibreria(idLibro: string): void {
    this.myLibraryService.getLibreriaPersonale().subscribe({
      next: (libri) => {
        this.isLibroNellaLibreria = libri.some(
          (libro) => libro.idLibro === idLibro
        );
      },
      error: (error) => {
        console.error(
          'Errore durante il recupero della libreria personale',
          error
        );
      },
    });
  }

  //METODO AGGIUNGI CANCELLA DALLA LIBRERIA PERSONALE
  aggiungiOcancellaLibro() {
    const idLibro = this.route.snapshot.paramMap.get('idLibro');
    if (idLibro) {
      if (this.isLibroNellaLibreria) {
        //SE IL LIBRO è PRESENTE ALLORA SWITCHA IL TASTO DA AGGIUNGI A RIMUOVI
        this.libroSubscription = this.myLibraryService
          .cancellaLibro(idLibro)
          .subscribe({
            next: () => {
              this.isLibroNellaLibreria = false;
              console.log(
                'Libro rimosso dalla libreria personale con successo'
              );
              alert('Libro rimosso dalla libreria personale!');
            },
            error: (error) => {
              console.error(
                'Errore durante la rimozione del libro dalla libreria personale',
                error
              );
            },
          });
      } else {
        // ALTRIMENTI AGGIUNGI ALLA LIBRERIA PERSONALE
        this.libroSubscription = this.myLibraryService
          .aggiungiLibro(idLibro)
          .subscribe({
            next: () => {
              this.isLibroNellaLibreria = true;
              console.log(
                'Libro aggiunto alla libreria personale con successo'
              );
              alert('Libro aggiunto alla libreria personale!');
            },
            error: (error) => {
              console.error(
                "Errore durante l'aggiunta del libro alla libreria personale",
                error
              );
            },
          });
      }
    }
  }

  ngOnDestroy(): void {
    if (this.libroSubscription) {
      this.libroSubscription.unsubscribe();
    }
  }

  //BOOLEANO PER VERIFICARE SE L'UTENTE CORRENTE è L'ADMIN
  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  //PER VISUALIZZARE I PULSANTI CANCELLA LIBRO O MODIFICA
  cancellaLibro() {
    if (this.isAdmin()) {
      const idLibro = this.route.snapshot.paramMap.get('idLibro');
      if (idLibro) {
        this.gestioneLibriService.deleteLibro(idLibro).subscribe(
          () => {
            console.log('Libro cancellato con successo');
            alert('Il libro è stato cancellato dal database!');
            this.router.navigate(['/books/allbooks']);
          },
          (error) => {
            if (error.status === 403) {
              alert(
                'Non puoi cancellare il libro perché è aggiunto a una o più librerie personali.'
              );
            } else {
              console.error('Errore durante la cancellazione del libro', error);
            }
          }
        );
      }
    } else {
      console.error('Utente non autorizzato a cancellare il libro');
    }
  }

  //MODIFICA IL LIBRO REINDIRIZZA ALLA PAGINA DI MODIFICA
  modificaLibro() {
    const idLibro = this.route.snapshot.paramMap.get('idLibro');
    if (idLibro) {
      this.router.navigate(['/admin/modifica-libro', idLibro]);
    }
  }
}
