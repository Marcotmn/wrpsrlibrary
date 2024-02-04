import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Route, RouterModule } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MylibraryComponent } from './components/mylibrary/mylibrary.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { BooksComponent } from './components/books/books.component';
import { BookDetailsComponent } from './components/book-details/book-details.component';
import { PublishersComponent } from './components/publishers/publishers.component';
import { AdminComponent } from './components/admin/admin.component';
import { HomeComponent } from './home/home.component';
import { AuthGuard } from './auth/auth.guard';
import { TokenInterceptor } from './auth/token.interceptor';
import { CommonModule } from '@angular/common';
import { RegisterAdminComponent } from './auth/register-admin/register-admin.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './auth/login/login.component';
import { RegisterUserComponent } from './auth/register-user/register-user.component';
import { ModificaLibroComponent } from './components/admin/modifica-libro/modifica-libro.component';

const routes: Route[] = [
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: 'registrazione',
    component: RegisterUserComponent,
  },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'editori/upload-editore',
    component: PublishersComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'admin/modifica-libro/:idLibro',
    component: ModificaLibroComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'registrazione-admin',
    component: RegisterAdminComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'books/allbooks',
    component: BooksComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'books/:idLibro',
    component: BookDetailsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'mylibrary/elenco',
    component: MylibraryComponent,
    canActivate: [AuthGuard],
  },
];

@NgModule({
  declarations: [
    AppComponent,
    MylibraryComponent,
    NavbarComponent,
    BooksComponent,
    BookDetailsComponent,
    PublishersComponent,
    AdminComponent,
    HomeComponent,
    RegisterUserComponent,
    RegisterAdminComponent,
    LoginComponent,
    ModificaLibroComponent,
  ],

  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    RouterModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
  ],

  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
  ],

  bootstrap: [AppComponent],
})
export class AppModule {}
