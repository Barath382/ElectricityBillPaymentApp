import { Component } from '@angular/core';
import { AuthServiceService } from '../../auth/auth-service.service';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { NgbPopoverModule, NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [RouterLink,NgbPopoverModule,RouterLinkActive,NgbTooltipModule],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent {
  constructor(private authService: AuthServiceService, private router: Router) {}

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
