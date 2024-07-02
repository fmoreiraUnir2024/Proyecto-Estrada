import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FuenteConocimientoDTO } from 'src/app/models/fuenteConocimiento';
import { Proyecto } from 'src/app/models/proyecto';
import { GenerativeLanguageGemeniService } from 'src/app/services/generative-language-gemeni.service';
import { ProjectService } from 'src/app/services/project.service';
import { Document, Packer, Paragraph, TextRun } from 'docx';
import { jsPDF } from 'jspdf';
import { saveAs } from 'file-saver';
import { DocumentFuente } from 'src/app/models/documento';
import Swal from 'sweetalert2';

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
  fuenteOpen: boolean=false;
  alternativas:string[]=[];
  nombreplantilla:any;
  contenidplantilla:any;
  markdownContent = "";  
  markdownfuente = "";
  documentoFuente: DocumentFuente = {
    id: 0,
    nombre: '',
    formato: '',
    contenido: '',
    proyecto_id: null,
  };
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
      this.cargarDocumento();
    });
  }
  downloadIEEE() {
    const content = this.editorContent; // Contenido HTML del editor
    if (content) {
      const ieeeContent = `
        \\documentclass[conference]{IEEEtran}
        \\usepackage[utf8]{inputenc}
        \\title{Generated Document}
        \\author{Your Name}
        \\date{\\today}
        
        \\begin{document}
        
        \\maketitle
        
        \\section{Introduction}
        
        ${content.replace(/<[^>]+>/g, '')}
        
        \\end{document}
      `;
  
      const blob = new Blob([ieeeContent], { type: 'application/x-latex' });
      saveAs(blob, 'document.tex');
    } else {
      console.error('Editor content is not available');
    }
  }
  
  downloadAPA() {
    const content = this.editorContent; // Contenido HTML del editor
    if (content) {
      const apaContent = `
        <html>
        <head>
          <style>
            body { font-family: Arial, sans-serif; margin: 40px; }
            h1 { text-align: center; }
            p { text-align: justify; }
          </style>
        </head>
        <body>
          <h1>Generated Document</h1>
          <h2>Introduction</h2>
          <p>${content}</p>
        </body>
        </html>
      `;
  
      const blob = new Blob([apaContent], { type: 'text/html' });
      saveAs(blob, 'document.html');
    } else {
      console.error('Editor content is not available');
    }
  }
  downloadLaTeX(Contenido: string) {
    const content = this.editorContent; // Contenido HTML del editor
    if (content) {    
      const blob = new Blob([Contenido], { type: 'application/x-latex' });
      saveAs(blob, 'document.tex');
    } else {
      console.error('Editor content is not available');
    }}
  downloadPDF() {
    const content = this.editorContent; // Contenido HTML del editor
    if (content) {
      const pdf = new jsPDF('p', 'pt', 'a4');
      pdf.html(content, {
        callback: (doc) => {
          doc.save('document.pdf');
        },
        x: 10,
        y: 10
      });
    } else {
      console.error('Editor content is not available');
    }}

  downloadWord() {
    const content = this.editorContent; // Contenido HTML del editor
    if (content) {
      const doc = new Document({
        sections: [
          {
            properties: {},
            children: [
              new Paragraph({
                children: [
                  new TextRun({
                    text: content.replace(/<[^>]+>/g, ''), // Remueve etiquetas HTML
                    break: 1
                  })
                ]
              })
            ]
          }
        ]
      });
  
      Packer.toBlob(doc).then(blob => {
        saveAs(blob, 'document.docx');
      });
    } else {
      console.error('Editor content is not available');
    }
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
  ejecutadorIA(numero:number)
  {
    const seleccion:string= this.selectedText;
    if(seleccion.length>250)
    {
      switch (numero) {
        case 1:
          this.cargarReferencias();
          break;
        case 2:
          this.cargarFeedback();
          break;
        case 3:
          this.obtenerfeedback();
          break;
        case 4:
          this.obtenerAlternativas();
          break;
        case 5:
          this.generateDiagram()
          break;
        case 6:
          
          break;
        default:
         
          break;
      }
     
    }else {
      Swal.fire('Warnning', 'Muy corto el texto seleccionado.', 'info');
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
    Swal.fire({
      title: 'Cargando referencias',
      text: 'Por favor, espere...',
      allowOutsideClick: false,
      didOpen: () => {
        Swal.showLoading();
      }
    });
    this.proyectoService.buscarReferencias(seleccion).subscribe(
      (data: any) => {
        this.referencias = JSON.parse(data.replace(/```json|```/g, '').replace("título","titulo"));

        console.log(this.referencias);
        Swal.close();
        Swal.fire('Referencias cargadas', 'Las referencias se han cargado correctamente.', 'success');               
       
      },
      error => {
        Swal.close();
        Swal.fire('Error', 'No se pudo cargar las referencias.', 'error');
        console.error('Error al obtener referencias', error);
      }
    );
  }
  cargarFeedback(): void {
    const seleccion: string = this.selectedText;
    Swal.fire({
      title: 'Cargando Feedback',
      text: 'Por favor, espere...',
      allowOutsideClick: false,
      didOpen: () => {
        Swal.showLoading();
      }
    });
    this.proyectoService.obtenerPuntos(seleccion).subscribe(
      (data: any) => {
        this.feedback = JSON.parse(data.replace(/```json|```/g, ''));
        console.log(this.feedback);
        Swal.close();
        Swal.fire('Feedback cargadas', 'El feedback se han cargado correctamente.', 'success');
      },
      error => {
        Swal.close();
        Swal.fire('Error', 'No se pudo cargar el feedback rápido.', 'error');
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
  downloadLatexPdf() {
    this.proyectoService.downloadPdf(this.editorContent).subscribe(
      (data: Blob) => {
        saveAs(data, 'document.pdf');
        this.isOpen = false;
      },
      (error: any) => {
        console.error('Error al obtener informacion', error);
      }
    );
  } isOpen = false;

  toggleDropdown() { this.isOpen = !this.isOpen;}
  downloadLatex() {}
  downloadPdfNormal() {}
  fuenteConocimiento(){this.fuenteOpen = !this.fuenteOpen;}

  listdocumentoFuente: DocumentFuente[] = [];
  cargarDocumento(): void {
    this.proyectoService.obtenerDocumentos(this.idProyecto).subscribe(
      (data) => {
        this.listdocumentoFuente = data;
      },
      (error) => {
        console.error('Error al cargar las plantillas', error);
      }
    );
  }
  cargarArgumentoFuente(doc:DocumentFuente): void {
    this.proyectoService.argumentoFuente(this.selectedText ,doc.contenido,this.datosProyecto.nombre).subscribe(
      (data) => {
       console.log(data) ;
       this.markdownfuente=data;
      },
      (error) => {
        console.error('Error al cargar las plantillas', error);
      }
    );
  }
 
  
  generateDiagram() {
    Swal.fire({
      title: 'Cargando',
      text: 'Por favor, espere...',
      allowOutsideClick: false,
      didOpen: () => {
        Swal.showLoading();
      }
    });

    this.proyectoService.getDiagramImage(this.datosProyecto).subscribe(
      (data) => {
        console.log(data)
        const mermaidText = data.replace(/```|mermaid|"|/g, '');
        this.proyectoService.generateMermaidImage(mermaidText).subscribe(
          (blob: Blob) => {
            Swal.close();

            saveAs(blob, 'diagram.png');
            Swal.fire('Diagrama generado', 'El diagrama ha sido generado y descargado con éxito.', 'success');
          },
          (error) => {
            Swal.close();
            Swal.fire('Error', 'Error al generar el diagrama.', 'error');
            console.error('Error al generar el diagrama', error);
          }
        );
      },
      (error) => {
        Swal.close();
        Swal.fire('Error', 'Error al obtener el texto del diagrama.', 'error');
        console.error('Error al obtener el texto del diagrama', error);
      }
    );
  }
}
