import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModificaLibroComponent } from './modifica-libro.component';

describe('ModificaLibroComponent', () => {
  let component: ModificaLibroComponent;
  let fixture: ComponentFixture<ModificaLibroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModificaLibroComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModificaLibroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
