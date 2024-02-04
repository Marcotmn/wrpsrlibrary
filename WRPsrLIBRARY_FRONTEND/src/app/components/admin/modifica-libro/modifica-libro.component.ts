import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BooksService } from 'src/app/service/books.service';
import { GestioneLibriService } from 'src/app/service/gestione-libri.service';
import { PublishersService } from 'src/app/service/publishers.service';

@Component({
  selector: 'app-modifica-libro',
  templateUrl: './modifica-libro.component.html',
  styleUrls: ['./modifica-libro.component.css'],
})
export class ModificaLibroComponent implements OnInit {
  libroModel: any = {
    titolo: '',
    editore: '',
  };

  editori: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private gestioneLibriService: GestioneLibriService,
    private booksService: BooksService,
    private publishersService: PublishersService
  ) {}

  ngOnInit(): void {
    this.caricaEditori();

    const idLibro = this.route.snapshot.paramMap.get('idLibro');
    if (idLibro) {
      this.booksService.getLibro(idLibro).subscribe({
        next: (libro) => {
          this.libroModel = libro;
        },
        error: (error) => {
          console.error('Errore nel recupero dei dettagli del libro', error);
        },
      });
    }
  }

  selezionaEditore(editore: any) {
    this.libroModel.editore = editore;
    console.log('Editore selezionato:', this.libroModel.editore);
  }

  //METODO PER MODIFICARE IL LIBRO
  salvaModifiche(): void {
    const idLibro = this.route.snapshot.paramMap.get('idLibro');
    if (idLibro !== null) {
      this.gestioneLibriService.updateLibro(idLibro, this.libroModel).subscribe(
        (response) => {
          console.log('Libro modificato con successo:', response);
          alert('Il libro è stato modificato correttamente');
          this.router.navigate(['/books', idLibro]);
        },
        (error) => {
          console.error('Errore durante la modifica del libro', error);
          alert('Devi selezionare un editore o inserire il titolo!');
        }
      );
    }
  }

  //CARICARE GLI EDITORI PER DROPDOWN
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
