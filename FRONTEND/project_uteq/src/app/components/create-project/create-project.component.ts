import { Component, OnInit } from '@angular/core';
import { PlantillaService } from 'src/app/services/plantilla.service';
import { ProjectService } from 'src/app/services/project.service';
import { AuthService } from 'src/app/services/auth.service';
import { Proyecto } from '../../models/proyecto';

@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.css']
})
export class CreateProjectComponent implements OnInit {
  proyecto: Proyecto = {
    nombre: '',
    descripcion: '',
    tipo_articulo: '',
    usuario_id: 0,
    plantilla_id: 0,
    proyecto_id: 0,
    
    resumen: '',
    palabrasClave: '',
    contenido: '',
  };
  plantillas: any[] = [];

  constructor(
    private plantillaService: PlantillaService,
    private proyectoService: ProjectService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.plantillaService.obtenerPlantillas().subscribe(
      (data: any) => {
        this.plantillas = data;
      },
      (error) => {
        console.error('Error al obtener plantillas', error);
      }
    );

 
    const usuario = this.authService.getUser();
    this.proyecto.usuario_id = usuario.id;
  }

  crearProyecto(): void {
    this.proyectoService.crearProyecto(this.proyecto).subscribe(
      (data) => {
        console.log('Proyecto creado con éxito', data);
      },
      (error) => {
        console.error('Error al crear proyecto', error);
      }
    );
  }
}
