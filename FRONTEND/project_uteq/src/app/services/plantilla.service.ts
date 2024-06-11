import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import baseUrl from './helper';

@Injectable({
  providedIn: 'root'
})
export class PlantillaService {

  constructor(private httpClient: HttpClient) { }

  obtenerPlantillasPorUsuario(): Observable<any[]> {
    return this.httpClient.get<any[]>(`${baseUrl}/api/plantillas/usuario`);
  }
}
