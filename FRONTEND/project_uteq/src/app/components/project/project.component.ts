import { Component, ElementRef,  OnInit,  ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FuenteConocimientoDTO } from 'src/app/models/fuenteConocimiento';
import { Proyecto } from 'src/app/models/proyecto';
import { ProjectService } from 'src/app/services/project.service';


@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent   implements OnInit{
  constructor( private router: Router,private route: ActivatedRoute, private proyectoService: ProjectService) {}
  datosProyecto: Proyecto ={
    nombre: '',
    descripcion: '',
    tipo_articulo: '',
    usuario_id: 0,
    plantilla_id: 0,
    proyecto_id: 0,

    resumen: '',
    palabrasClave: '',
    contenido: '',
  }
  fuente: FuenteConocimientoDTO={
    cantidad: 0,
    contenido: "",
  }
  idProyecto: any;
  editorContent: string = '';
  isOpen = [false, false, false];

  toggleAccordion(index: number): void {
    this.isOpen[index] = !this.isOpen[index];
  }
  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {   
      this.idProyecto=   params.get('id');
      this.cargarInformacion( this.idProyecto)
      this.cargarFuentes( this.idProyecto)
      
     
    });
  }
  guardarContenido(): void {
    if (this.idProyecto && this.editorContent) {
      this.proyectoService.actualizarContenido(this.idProyecto, this.editorContent).subscribe(
        (proyectoActualizado) => {
          console.log('Contenido actualizado exitosamente', proyectoActualizado);
        },
        (error) => {
          console.error('Error al actualizar el contenido del proyecto', error);
        }
      );
    } else {
      console.error('No se pudo actualizar el contenido: ID de proyecto o contenido faltante');
    }
  }
  cargarFuentes(id: any): void {
    this.proyectoService.obtenerFuentes(id).subscribe(
      (data) => {
        this.fuente = data;

      },
      (error) => {
        console.error('Error al cargar los fuentes', error);
      }
    );
  }

  cargarInformacion(id: any)
  {
    this.proyectoService.buscarProyectoPorId(id).subscribe(
      (data: any) => {
        this.datosProyecto = data;
        this.editorContent= data.plantilla.formato
        this.editorContent= (data.contenido==='' || data.contenido===null ) ?   data.plantilla.formato :  data.contenido;
        console.log(this.datosProyecto)
      },
      (error) => {
        console.error('Error al obtener informacion', error);
      }
    );

  }
}
