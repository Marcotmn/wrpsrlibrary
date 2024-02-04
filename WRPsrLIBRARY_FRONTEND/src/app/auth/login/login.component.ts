import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  email: string = '';
  password: string = '';
  constructor(private authSrv: AuthService, private router: Router) {}

  ngOnInit(): void {}
  accedi(): void {
    this.authSrv.login(this.email, this.password).subscribe(
      (response) => {
        if (response.accessToken) {
          const decodedToken = this.authSrv.jwtHelper.decodeToken(
            response.accessToken
          );
          localStorage.setItem('userData', JSON.stringify(decodedToken));
          this.router.navigate(['books/allbooks']);
        }
      },
      (error) => {
        console.error('Errore durante la richiesta HTTP:', error);
        alert('Username o password errati');
      }
    );
  }
}
