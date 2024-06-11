import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service'; // Asegúrate de que la ruta sea correcta
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  onSubmit() {
    const loginData = {
      username: this.username,
      password: this.password
    };

    this.authService.generateToken(loginData).subscribe(
      (response: any) => {
        console.log('Token generado', response);
        this.authService.loginUser(response.token);

        this.authService.getCurrentUser().subscribe(
          (user: any) => {
            this.authService.setUser(user);
            console.log('Usuario autenticado', user);
            this.router.navigate(['/dashboard']); // Cambia '/dashboard' por la ruta a la que quieras redirigir después del login
          },
          (error: any) => {
            console.error('Error al obtener el usuario actual', error);
          }
        );
      },
      (error: any) => {
        console.error('Error en el login', error);
      }
    );
  }
}
