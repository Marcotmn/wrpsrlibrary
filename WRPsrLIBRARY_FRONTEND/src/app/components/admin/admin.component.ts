import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BooksService } from 'src/app/service/books.service';
import { GestioneLibriService } from 'src/app/service/gestione-libri.service';
import { PublishersService } from 'src/app/service/publishers.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
})
export class AdminComponent implements OnInit {
  //PAYLOAD LIBRO
  libroModel: any = {
    titolo: '',
    editore: '',
  };

  //INIZIALIZZO UN ARRAY EDITORI VUOTO DA RIEMPIRE E POTERLI SELEZIONARE DAL DROPDOWN
  editori: any[] = [];

  constructor(
    private gestioneLibriService: GestioneLibriService,
    private publishersService: PublishersService,
    private booksService: BooksService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // CARICA LA LISTA DEGLI EDITORI ALL'APERTURA DELLA PAGINA
    this.caricaEditori();
  }

  selezionaEditore(editore: any) {
    this.libroModel.editore = editore;
    console.log('Editore selezionato:', this.libroModel.editore);
  }

  //METODO CARICAMENTO LIBRO
  uploadLibro(libroForm: NgForm) {
    if (!this.libroModel.editore || !this.libroModel.titolo) {
      console.error('Titolo o editore mancante.');
      alert('Devi riempire tutti i campi!');
      return;
    }

    this.booksService.getLibri().subscribe(
      (libri: any[]) => {
        const libroEsistente = libri.some(
          (libro) => libro.titolo === this.libroModel.titolo
        );

        if (libroEsistente) {
          alert('Il libro è già presente nel database.');
        } else {
          const libroData = {
            titolo: this.libroModel.titolo,
            editore: this.libroModel.editore,
          };

          this.gestioneLibriService.uploadLibro(libroData).subscribe(
            (response: any) => {
              console.log('Libro caricato con successo:', response);
              console.log('Risposta del servizio di caricamento:', response);

              this.caricaEditori();

              //SVUOTA IL FORM DOPO IL SALVATAGGIO
              libroForm.resetForm();
              this.libroModel = { titolo: '', editore: '' };

              //RECUPERA L'ID DEL LIBRO CARICATO PER NAVIGARE ALLA PAGINA DI DETTAGLIO
              const idLibroCaricato = response.idLibro;
              alert('Libro caricato con successo!');
              this.router.navigate(['/books', idLibroCaricato]);
            },
            (error) => {
              console.error('Errore durante il caricamento del libro:', error);
              alert(
                'Errore durante il caricamento del libro. Si prega di riprovare.'
              );
            }
          );
        }
      },
      (error) => {
        console.error('Errore durante il recupero dei libri:', error);
        alert('Errore durante il recupero dei libri. Si prega di riprovare.');
      }
    );
  }

  //METODO PER CARICARE GLI EDITORI E SALVARLI NELL'ARRAY
  caricaEditori() {
    this.publishersService.getEditori().subscribe(
      (editori: any[]) => {
        console.log('Editori caricati con successo:', editori);
        this.editori = editori;
      },
      (error) => {
        console.error('Errore durante il caricamento degli editori:', error);
      }
    );
  }
}
