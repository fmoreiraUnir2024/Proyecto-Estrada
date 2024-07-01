import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Proyecto } from '../models/proyecto'; // Asegúrate de tener un modelo para Proyecto
import baseUrl from './helper';
import { DocumentFuente } from '../models/documento';
import { FuenteConocimientoDTO } from '../models/fuenteConocimiento';
import { dataia } from '../models/data';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  dataia: dataia= {
    doc: '',
    plan: ''
  }
  constructor(private httpClient: HttpClient) { }

  crearProyecto(proyecto: Proyecto): Observable<Proyecto> {
    return this.httpClient.post<Proyecto>(`${baseUrl}/api/proyectos`, proyecto);
  }
  
  actualizarProyecto(proyecto: Proyecto): Observable<Proyecto> {
    return this.httpClient.put<Proyecto>(`${baseUrl}/api/proyectos`, proyecto);
  }
  buscarProyectoPorId(id: any): Observable<Proyecto> {
    return this.httpClient.get<Proyecto>(`${baseUrl}/api/proyectos/${id}`);
  }
  guardarDocumento( documentoFuente: DocumentFuente )
  {
    return this.httpClient.post<Proyecto>(`${baseUrl}/api/documentos`, documentoFuente);
  }
  obtenerDocumentos(id: any): Observable<DocumentFuente[]> {
    return this.httpClient.get<DocumentFuente[]>(`${baseUrl}/api/documentos/${id}`);
  }
  obtenerFuentes(id: any): Observable<FuenteConocimientoDTO> {
    return this.httpClient.get<FuenteConocimientoDTO>(`${baseUrl}/api/documentos/analisis/${id}`);
  }
  actualizarContenido(id: number, contenido: string): Observable<Proyecto> {
    return this.httpClient.put<Proyecto>(`${baseUrl}/api/proyectos/contenido/${id}`, contenido);
  }
  obtenerPuntos(contenido: string): Observable<string> {
    const url = `${baseUrl}/gpt/v1/analisis`;
   const body = { contents: [{ parts: [{ text: contenido }] }] };
   const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
 
   return this.httpClient.post(url, body, { headers, responseType: 'text' }).pipe(
     catchError(this.handleError)
   );
   }

  buscarReferencias(contenido: string): Observable<string> {
   const url = `${baseUrl}/gpt/v1/chat`;
  const body = { contents: [{ parts: [{ text: contenido }] }] };
  const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  return this.httpClient.post(url, body, { headers, responseType: 'text' }).pipe(
    catchError(this.handleError)
  );
  }
  obtenerAlternativas(contenido: string): Observable<string> {
    const url = `${baseUrl}/gpt/v1/alt`;
   const body = { contents: [{ parts: [{ text: contenido }] }] };
   const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
 
   return this.httpClient.post(url, body, { headers, responseType: 'text' }).pipe(
     catchError(this.handleError)
   );
   }

   analisisPlantilla(contenido: string, plantilla:string): Observable<string> {
    const url = `${baseUrl}/gpt/v1/ia/planitlla`;
    this.dataia.doc=contenido;
    this.dataia.plan=plantilla;
   const body = { contents: [{ parts: [{ text: contenido }] }] };
   const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
   return this.httpClient.post(url, this.dataia, { headers, responseType: 'text' }).pipe(
     catchError(this.handleError)
   );
   }
  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error.message);
    return throwError('Something bad happened; please try again later.');
  }
 
  downloadPdf(contenido: string): Observable<Blob> {
    const url = `${baseUrl}/openia/v1/completions/`;
    this.dataia.doc = contenido;
    this.dataia.plan = '';
    return this.httpClient.post(url, this.dataia, { responseType: 'blob' });
  }
  
  argumentoFuente(mitexto: string, fuente:string, titulo:string): Observable<string> {
    const url = `${baseUrl}/openia/v1/fuentes/`;
    this.dataia.doc = `Título del Proyecto: ${titulo} Resumen del proyecto: ${mitexto}`     
    this.dataia.plan = fuente;
    return this.httpClient.post(url, this.dataia, { responseType: 'text' });
  }
  
 
  getDiagramImage(resumen: Proyecto): Observable<string> {
    const url = `${baseUrl}/openia/v1/mermaid/`;
    const dataia = { doc: `${resumen.nombre} ${resumen.descripcion} ${resumen.tipo_articulo}` };
    return this.httpClient.post(url, dataia, { responseType: 'text' });
  }

  generateMermaidImage(mermaidCode: string): Observable<Blob> {
    const encodedMermaidCode = encodeURIComponent(mermaidCode);
    const url = `https://mermaid.ink/img/${encodedMermaidCode}`;
    return this.httpClient.get(url, { responseType: 'blob' });
  }
}
