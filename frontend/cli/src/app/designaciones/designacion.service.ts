import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';
import { DataPackage } from "../data-package";
import { Designacion } from './designacion';
import { Cargo } from '../cargos/cargo';

@Injectable({
  providedIn: 'root'
})
export class DesignacionService {

  private designacionesUrl = 'rest/designaciones';
  constructor(private http: HttpClient) { }

  all(): Observable<DataPackage> {
    return this.http.get<DataPackage>(this.designacionesUrl); // REST
  }

  get(id: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(this.designacionesUrl + "/id/" + id);
  }

  delete(designacion: Designacion): Observable<DataPackage> {
    return this.http.delete<DataPackage>(`${this.designacionesUrl}/id/${designacion.id}`);
  }

  save(designacion: Designacion): Observable<DataPackage> {
    // Actualizar
    // buscamos division que corresponde
    if (designacion.id) {
      // actualizamos en el backend
      return this.http.put<DataPackage>(this.designacionesUrl, designacion);
    }
    // Insertar
    return this.http.post<DataPackage>(this.designacionesUrl, designacion);
  }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.designacionesUrl}/page?page=${page}&size=${size}`); // REST
  }

  findPersonaFechaCargo(fecha: Date, cargo: Cargo): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.designacionesUrl}/${String(fecha.getFullYear()) + "-" + String(fecha.getMonth() + 1).padStart(2, '0') + "-" + String(fecha.getDate()).padStart(2, '0')}-${cargo.id}`);
  }

  cargosEnHorario(anHora: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.designacionesUrl}/cargos/${anHora}`)
  }
}
