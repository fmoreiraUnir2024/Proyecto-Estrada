import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  nombre: string = '';
  email: string = '';
  password: string = '';
  confirmPassword: string = '';
  username:string='';
  apellido:string='';

  constructor(private userService: UserService, private router: Router) { }

  onSubmit() {
    if (this.password !== this.confirmPassword) {
      alert('Las contraseñas no coinciden');
      return;
    }

    const user = {
      nombre: this.nombre,
      email: this.email,
      username: this.username,
      password: this.password,
      apellido: this.apellido
    };

    this.userService.addUser(user).subscribe(
      (response) => {
        console.log('Usuario registrado', response);
        this.router.navigate(['/login']);
      },
      (error) => {
        console.error('Error en el registro', error);
      }
    );
  }
}
