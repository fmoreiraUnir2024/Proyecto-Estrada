import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { HttpClient } from '@angular/common/http';
import baseUrl from 'src/app/services/helper';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  isLoggedIn: boolean = false;
  userName: string = '';
  proyectos: any[] = [];

  constructor(private authService: AuthService, private httpClient: HttpClient, public router: Router) {}

  ngOnInit(): void {
    this.authService.isLoggedIn$.subscribe((loggedIn: boolean) => {
      this.isLoggedIn = loggedIn;
      if (loggedIn) {
        this.loadUser();
        this.loadProyectos();
      }
    });
  }

  loadUser(): void {
    const user = this.authService.getUser();
    this.userName = user ? user.username : '';
  }

  loadProyectos(): void {
    this.httpClient.get(`${baseUrl}/api/proyectos/usuario/${this.authService.getUser().id}`).subscribe(
      (data: any) => {
        this.proyectos = data;
      },
      (error) => {
        console.error('Error al cargar proyectos', error);
      }
    );
  }

  logout(): void {
    this.authService.logout();
  }
  DocumentoProyecto(proyectoId: number): void {
    this.router.navigate(['/project-data-entry', proyectoId]);
    
  }
  abriInformacion(proyectoId: number): void {
    this.router.navigate(['/create-project/', proyectoId]);
    
  }
  DescargarDocumentoProyecto(proyectoId: number): void {
    this.router.navigate(['/project-data-entry', proyectoId, '/documentos']);
  }
  verTodaInformacionProyecto(proyectoId: number): void {
    this.router.navigate(['/project', proyectoId]);
    
  }
}
