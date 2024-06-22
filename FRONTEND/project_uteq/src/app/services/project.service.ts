import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Proyecto } from '../models/proyecto'; // Asegúrate de tener un modelo para Proyecto
import baseUrl from './helper';
import { DocumentFuente } from '../models/documento';
import { FuenteConocimientoDTO } from '../models/fuenteConocimiento';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private httpClient: HttpClient) { }

  crearProyecto(proyecto: Proyecto): Observable<Proyecto> {
    return this.httpClient.post<Proyecto>(`${baseUrl}/api/proyectos`, proyecto);
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
}
