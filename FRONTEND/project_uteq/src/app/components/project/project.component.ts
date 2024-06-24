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
  feedback: any = {
    fortalezas: [],
    areasParaMejorar: [],
    puntuacionGeneral: 0
  };
  alternativas:string[]=[];
  nombreplantilla:any;
  contenidplantilla:any;
  markdownContent = '';
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
  ejecutadorIA()
  {
    const seleccion:string= this.selectedText;
    if(seleccion.length>250)
    {
      this.cargarReferencias();

    }else {
      console.log("Es muy corto")
    }
  }
  obtenerAlternativas()
  {
    const seleccion: string = this.selectedText;
    console.log("Estas llamando al botón desde grammar ")
     this.proyectoService.obtenerAlternativas(seleccion).subscribe(
       (data: any) => {
         this.alternativas=data.replaceAll("*","").replaceAll("## Alternativa"," ").split('GPQ');
        console.log(this.alternativas) 
      },
      error => {
        console.error('Error al obtener referencias', error);
      }
     );
  }
  cargarReferencias(): void {
    const seleccion: string = this.selectedText;
    this.proyectoService.buscarReferencias(seleccion).subscribe(
      (data: any) => {
        this.referencias = JSON.parse(data.replace(/```json|```/g, '').replace("título","titulo"));

        console.log(this.referencias);
        this.cargarFeedback();
      },
      error => {
        console.error('Error al obtener referencias', error);
      }
    );
  }
  cargarFeedback(): void {
    const seleccion: string = this.selectedText;
    this.proyectoService.obtenerPuntos(seleccion).subscribe(
      (data: any) => {
        this.feedback = JSON.parse(data.replace(/```json|```/g, ''));
        console.log(this.feedback);
      },
      error => {
        console.error('Error al obtener feedback', error);
      }
    );
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
        this.contenidplantilla = data.plantilla.formato;        
        this.editorContent = data.contenido === '' || data.contenido === null ? data.plantilla.formato : data.contenido;
        this.nombreplantilla=data.plantilla.nombre;
      
        console.log(this.datosProyecto);
      },
      error => {
        console.error('Error al obtener informacion', error);
      }
    );
  }
  obtenerfeedback(): void
  {
     const seleccion: string = this.selectedText;
     const pla:string=this.contenidplantilla ;
    this.proyectoService.analisisPlantilla(seleccion,pla).subscribe(
      (data: any) => {
       this.markdownContent=data;
      },
      error => {
        console.error('Error al obtener informacion', error);
      }
    );
  }
}
