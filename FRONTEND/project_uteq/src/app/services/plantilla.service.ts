import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Plantilla } from '../models/plantillas';
import baseUrl from './helper';

@Injectable({
  providedIn: 'root'
})
export class PlantillaService {

  constructor(private httpClient: HttpClient) { }

  obtenerPlantillas(): Observable<Plantilla[]> {
    return this.httpClient.get<Plantilla[]>(`${baseUrl}/api/plantillas/usuario`);
  }

  
  crearPlantilla(plantillaDTO: any): Observable<Plantilla> {
    return this.httpClient.post<Plantilla>(`${baseUrl}/api/plantillas`, plantillaDTO);
  }
}
