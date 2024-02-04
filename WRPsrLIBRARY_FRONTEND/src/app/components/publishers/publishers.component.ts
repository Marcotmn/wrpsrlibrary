import { Component, OnInit } from '@angular/core';
import { Subscription, catchError, of } from 'rxjs';
import { PublishersService } from 'src/app/service/publishers.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-publishers',
  templateUrl: './publishers.component.html',
  styleUrls: ['./publishers.component.css'],
})
export class PublishersComponent implements OnInit {
  private editoriSubscription: Subscription | undefined;
  tuttiGliEditori: any[] = [];

  nuovoEditore: any = {
    editore: '',
  };

  constructor(
    private publishersService: PublishersService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.visualizzaListaEditori();
  }

  //PER CARICARE TUTTI GLI EDITORI
  private visualizzaListaEditori(): void {
    this.editoriSubscription = this.publishersService
      .getEditori()
      .pipe(
        catchError((error) => {
          console.error('Errore durante il caricamento degli editori', error);
          alert('Si è verificato un errore, ricarica la pagina.');
          return of([]);
        })
      )
      .subscribe({
        next: (editori) => {
          this.tuttiGliEditori = editori;
          console.log('Tutti gli editori', this.tuttiGliEditori);
        },
      });
  }

  //UPLOAD EDITORE
  uploadEditore(): void {
    this.publishersService.uploadEditore(this.nuovoEditore).subscribe(
      (response) => {
        console.log('Editore caricato con successo:', response);
        alert('Editore caricato con successo!');
        this.router.navigate(['/books/allbooks']);
      },
      (error) => {
        console.error("Errore durante il caricamento dell'editore", error);
      }
    );
  }

  ngOnDestroy(): void {
    if (this.editoriSubscription) {
      this.editoriSubscription.unsubscribe();
    }
  }
}
