import { Component, OnInit } from '@angular/core';
import { PlantillaService } from 'src/app/services/plantilla.service';
import { Plantilla } from '../../models/plantillas';
import * as PizZip from 'pizzip';
import * as Docxtemplater from 'docxtemplater';
import * as FileSaver from 'file-saver';
import { AuthService } from 'src/app/services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-gestionar-plantillas',
  templateUrl: './gestionar-plantillas.component.html',
  styleUrls: ['./gestionar-plantillas.component.css']
})
export class GestionarPlantillasComponent implements OnInit {
  nuevaPlantilla: Plantilla = {
    id: 0,
    nombre: '',
    formato: '',
    usuario_id: null
  };
  archivoSeleccionado: File | null = null;
  plantillas: Plantilla[] = [];
  contenidoArchivo = '';

  constructor(private plantillaService: PlantillaService, private authService: AuthService) { }

  ngOnInit(): void {
    this.cargarPlantillas();
  }

  cargarPlantillas(): void {
    this.plantillaService.obtenerPlantillas().subscribe(
      (data) => {
        this.plantillas = data;
      },
      (error) => {
        console.error('Error al cargar las plantillas', error);
      }
    );
  }

  onFileChange(event: any): void {
    const file = event.target.files[0];
    this.archivoSeleccionado = file;

    const reader = new FileReader();
    reader.onload = (e: any) => {
      const arrayBuffer = e.target.result;
      if (file.type === "application/vnd.openxmlformats-officedocument.wordprocessingml.document") {
        this.readDocx(arrayBuffer);
      } else if (file.type === "text/plain") {
        const text = new TextDecoder().decode(arrayBuffer);
        this.nuevaPlantilla.formato = text;
        this.contenidoArchivo = text;
        console.log(this.nuevaPlantilla.formato);
      }
    };
    reader.readAsArrayBuffer(file);
  }

  readDocx(arrayBuffer: ArrayBuffer) {
    const zip = new PizZip(arrayBuffer);
    const doc = new Docxtemplater(zip, {
      paragraphLoop: true,
      linebreaks: true,
    });

    const text = doc.getFullText();
    this.nuevaPlantilla.formato = text;
    this.contenidoArchivo = text;
    console.log(this.nuevaPlantilla.formato);
  }

  guardarPlantilla(): void {
    const usuario = this.authService.getUser(); // Asegúrate de obtener el usuario de alguna manera
    if (usuario && this.archivoSeleccionado) {
      const plantillaDTO = {
        nombre: this.nuevaPlantilla.nombre,
        formato: this.nuevaPlantilla.formato,
        usuario_id: usuario.id // Asegúrate de que el usuario tenga un id
      };

      if (this.nuevaPlantilla.id) {
        // this.plantillaService.actualizarPlantilla(this.nuevaPlantilla.id, plantillaDTO).subscribe(
        //   (response) => {
        //     console.log('Plantilla actualizada exitosamente', response);
        //     this.cargarPlantillas(); // Recargar las plantillas después de actualizar
        //     this.resetForm();
        //   },
        //   (error) => {
        //     console.error('Error al actualizar la plantilla', error);
        //   }
        // );
      } else {
        this.plantillaService.crearPlantilla(plantillaDTO).subscribe(
          (response) => {
            console.log('Plantilla guardada exitosamente', response);
            this.cargarPlantillas(); 
            this.resetForm();
          },
          (error) => {
            console.error('Error al guardar la plantilla', error);
          }
        );
      }
    }
  }
  limpiar()
  {
    this.resetForm();
  }
  seleccionarPlantilla(plantilla: Plantilla): void {
    this.nuevaPlantilla = { ...plantilla };
    this.contenidoArchivo = plantilla.formato;
  }

  eliminarPlantilla(plantilla: Plantilla): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: "No podrás revertir esta acción!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.plantillaService.eliminarPlantilla(plantilla.id).subscribe(
          (response) => {
            console.log('Plantilla eliminada exitosamente', response);
            this.cargarPlantillas(); // Recargar las plantillas después de eliminar
            Swal.fire(
              'Eliminada!',
              'La plantilla ha sido eliminada.',
              'success'
            );
          },
          (error) => {
            console.error('Error al eliminar la plantilla', error);
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'No se pudo eliminar la plantilla porque está relacionada con un proyecto.'
            });
          }
        );
      }
    });
  }

  resetForm(): void {
    this.nuevaPlantilla = {
      id: 0,
      nombre: '',
      formato: '',
      usuario_id: null
    };
    this.contenidoArchivo = '';
    this.archivoSeleccionado = null;
  }
}
