import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DataPackage } from "../data-package";
import { Licencia } from './licencia';

@Injectable({
  providedIn: 'root'
})
export class LicenciaService {
  private licenciasUrl = 'rest/licencias';
  constructor(private http: HttpClient) { }

  all(): Observable<DataPackage> {
    return this.http.get<DataPackage>(this.licenciasUrl); // REST
  }
  get(id: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(this.licenciasUrl + "/id/" + id);
  }

  save(licencia: Licencia): Observable<DataPackage> {
    // Actualizar
    // buscamos division que corresponde
    if (licencia.id) {
      // actualizamos en el backend
      return this.http.put<DataPackage>(this.licenciasUrl, licencia);
    }
    // Insertar
    return this.http.post<DataPackage>(this.licenciasUrl, licencia);
  }

  delete(licencia: Licencia): Observable<DataPackage> {
    return this.http.delete<DataPackage>(this.licenciasUrl + "/id/" + licencia.id);
  }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.licenciasUrl}/page?page=${page}&size=${size}`); // REST
  }
}