import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FuenteConocimientoDTO } from 'src/app/models/fuenteConocimiento';
import { Proyecto } from 'src/app/models/proyecto';
import { GenerativeLanguageGemeniService } from 'src/app/services/generative-language-gemeni.service';
import { ProjectService } from 'src/app/services/project.service';
import { Editor } from 'tinymce';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {
  datosProyecto: Proyecto = {
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

  fuente: FuenteConocimientoDTO = {
    cantidad: 0,
    contenido: "",
  };

  idProyecto: any;
  editorContent: string = '';
  selectedText: string = '';
  referencias: any[] = []; 
  nombreplantilla:any;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private proyectoService: ProjectService,
    private geminiService: GenerativeLanguageGemeniService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.idProyecto = params.get('id');
      this.cargarInformacion(this.idProyecto);
      this.cargarFuentes(this.idProyecto);
    });
  }

  setupEditor = (editor: any) => {
    editor.ui.registry.addButton('customButton', {
      text: 'Capturar',
      onAction: () => {
        this.capturarTextoSeleccionado(editor);
      }
    });

    editor.on('NodeChange', () => {
      this.selectedText = editor.selection.getContent({ format: 'text' });
    });
  };

  capturarTextoSeleccionado(editor: any): void {
    const seleccion = editor.selection.getContent({ format: 'text' });
    this.selectedText = seleccion;
  }

  usarTextoSeleccionado(): void {
    const seleccion:string= this.selectedText;
     this.proyectoService.buscarReferencias(seleccion).subscribe(
        (data: any) => {
         
          const cleanedData = data.replace(/```json|```/g, '');
          this.referencias = JSON.parse(cleanedData);
          
        },
        error => {
          console.error( error);
        }
      );
    // Aquí puedes agregar la lógica para usar el texto seleccionado
  }

  guardarContenido(): void {
    if (this.idProyecto && this.editorContent) {
      this.proyectoService.actualizarContenido(this.idProyecto, this.editorContent).subscribe(
        proyectoActualizado => {
          console.log('Contenido actualizado exitosamente', proyectoActualizado);
        },
        error => {
          console.error('Error al actualizar el contenido del proyecto', error);
        }
      );
    } else {
      console.error('No se pudo actualizar el contenido: ID de proyecto o contenido faltante');
    }
  }

  cargarFuentes(id: any): void {
    this.proyectoService.obtenerFuentes(id).subscribe(
      data => {
        this.fuente = data;
      },
      error => {
        console.error('Error al cargar los fuentes', error);
      }
    );
  }

  cargarInformacion(id: any): void {
    this.proyectoService.buscarProyectoPorId(id).subscribe(
      (data: any) => {
        this.datosProyecto = data;
        this.editorContent = data.plantilla.formato;        
        this.editorContent = data.contenido === '' || data.contenido === null ? data.plantilla.formato : data.contenido;
        this.nombreplantilla=data.plantilla.nombre;
        console.log(this.datosProyecto);
      },
      error => {
        console.error('Error al obtener informacion', error);
      }
    );
  }
}
