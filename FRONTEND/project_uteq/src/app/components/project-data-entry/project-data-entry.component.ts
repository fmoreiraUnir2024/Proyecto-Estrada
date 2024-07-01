import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {DocumentFuente } from '../../models/documento'
import * as PizZip from 'pizzip';
import * as Docxtemplater from 'docxtemplater';

import { ProjectService } from 'src/app/services/project.service';
import { Proyecto } from 'src/app/models/proyecto';
import { PDFDocument } from 'pdf-lib';



@Component({
  selector: 'app-project-data-entry',
  templateUrl: './project-data-entry.component.html',
  styleUrls: ['./project-data-entry.component.css']
})
export class ProjectDataEntryComponent  implements OnInit {
  documentoFuente: DocumentFuente = {
    id: 0,
    nombre: '',
    formato: '',
    contenido: '',
    proyecto_id: null,
  };
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

  archivosSeleccionados: File[] = [];
  contenidoArchivo = {};
  listdocumentoFuente: DocumentFuente[] = [];
  constructor( private router: Router,private route: ActivatedRoute, private proyectoService: ProjectService) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.documentoFuente.proyecto_id = params.get('id');
      this.cargarInformacion(this.documentoFuente.proyecto_id)
      this.cargarDocumento();
    });
  }


  cargarDocumento(): void {
    this.proyectoService.obtenerDocumentos(this.documentoFuente.proyecto_id).subscribe(
      (data) => {
        this.listdocumentoFuente = data;
      },
      (error) => {
        console.error('Error al cargar las plantillas', error);
      }
    );
  }

  cargarInformacion(id: any)
  {
    this.proyectoService.buscarProyectoPorId(id).subscribe(
      (data: any) => {
        this.datosProyecto = data;
      },
      (error) => {
        console.error('Error al obtener plantillas', error);
      }
    );

  }
  onFileChange(event: any): void {
    const files = event.target.files;
    this.archivosSeleccionados = Array.from(files);

    for (const file of this.archivosSeleccionados) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const arrayBuffer = e.target.result;
        if (file.type === "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
         {
          this.documentoFuente.nombre=file.name;
          this.readDocx(arrayBuffer);
        } else if (file.type === "text/plain") {
          const text = new TextDecoder().decode(arrayBuffer);
          this.documentoFuente.contenido = text;
          this.documentoFuente.nombre=file.name;
          this.documentoFuente.formato=file.name.split('.').pop()+'';
          console.log(this.documentoFuente.formato);
        }
      };
      reader.readAsArrayBuffer(file);
    }
  }
  readDocx(arrayBuffer: ArrayBuffer) {
    const zip = new PizZip(arrayBuffer);
    const doc = new Docxtemplater(zip, {
      paragraphLoop: true,
      linebreaks: true,
    });

    const text = doc.getFullText();
    this.documentoFuente.contenido = text;

    this.documentoFuente.formato='.docx'
    console.log(this.documentoFuente.formato);
  }

  guardarDocumento(): void {
    this.proyectoService.guardarDocumento(this.documentoFuente).subscribe(
      (data) => {
        console.log('Documento no pudo se guardado', data);
      },
      (error) => {
        console.error('Error al guardar el documento', error);
      }
    );
  }


}
