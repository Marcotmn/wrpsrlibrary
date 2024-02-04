import { Component, OnInit, HostListener } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  headerClass = 'header-transparent';

  @HostListener('window:scroll')
  onWindowScroll() {
    const scrollPos: number =
      window.scrollY ||
      document.documentElement.scrollTop ||
      document.body.scrollTop ||
      0;
    if (scrollPos > 0) {
      this.headerClass = 'header-opaque';
    } else {
      this.headerClass = 'header-transparent';
    }
  }

  constructor(public authService: AuthService) {}

  ngOnInit(): void {}
}
