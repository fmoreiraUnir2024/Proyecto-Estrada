import { Component, OnInit } from '@angular/core';
import { PlantillaService } from 'src/app/services/plantilla.service';
import { ProjectService } from 'src/app/services/project.service';
import { AuthService } from 'src/app/services/auth.service';
import { Proyecto } from '../../models/proyecto';
import Swal from 'sweetalert2';
import { ActivatedRoute, Router } from '@angular/router';

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
  idProyecto: any;
  plantillas: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
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

    this.route.paramMap.subscribe(params => {
      this.idProyecto = params.get('id');
      if (this.idProyecto) {
        this.cargarInformacion(this.idProyecto);
      } else {
        const usuario = this.authService.getUser();
        this.proyecto.usuario_id = usuario.id;
      }
    });
  }

  cargarInformacion(id: any): void {
    this.proyectoService.buscarProyectoPorId(id).subscribe(
      (data: any) => {
        this.proyecto=data;
        this.proyecto.tipo_articulo = data.tipoArticulo.replace(/\s+/g, '');
        this.proyecto.plantilla_id=data.plantilla.id;
        this.proyecto.usuario_id=data.usuario.id;
        console.log(data);
      },
      error => {
        console.error('Error al obtener informacion', error);
      }
    );
  }

  crearProyecto(): void {
    if (!this.proyecto.nombre || !this.proyecto.descripcion || !this.proyecto.tipo_articulo || !this.proyecto.plantilla_id) {
      Swal.fire('Error', 'Por favor, completa todos los campos.', 'error');
      return;
    }
    if (this.idProyecto) {
    
      this.proyectoService.actualizarProyecto(this.proyecto).subscribe(
        (data) => {
          Swal.fire('Éxito', 'Proyecto actualizado con éxito.', 'success');
          this.router.navigate(['/dashboard']);
        },
        (error) => {
          Swal.fire('Error', 'Error al actualizar el proyecto.', 'error');
          console.error('Error al actualizar proyecto', error);
        }
      );
    } else {    
      this.proyectoService.crearProyecto(this.proyecto).subscribe(
        (data) => {
          Swal.fire('Éxito', 'Proyecto creado con éxito.', 'success');
          this.router.navigate(['/dashboard']);
        },
        (error) => {
          Swal.fire('Error', 'Error al crear el proyecto.', 'error');
          console.error('Error al crear proyecto', error);
        }
      );
    }
  }

  verTodaInformacionProyecto(proyectoId: number): void {
    this.router.navigate(['/project', proyectoId]);
    
  }
}
