import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DataPackage } from "../data-package";
import { Licencia } from './licencia';

@Injectable({
  providedIn: 'root'
})
export class ArticuloLicenciaService {
  private articulosUrl = 'rest/articulos';
  constructor(private http: HttpClient) { }

  all(): Observable<DataPackage> {
    return this.http.get<DataPackage>(this.articulosUrl); // REST
  }

  get(id: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(this.articulosUrl + "/id/" + id);
  }

  searchArticulo(term: string) {
    return this.http.get<DataPackage>(this.articulosUrl + "/search/" + term);
  }


  save(licencia: Licencia): Observable<DataPackage> {
    // Actualizar
    // buscamos division que corresponde
    if (licencia.id) {
      // actualizamos en el backend
      return this.http.put<DataPackage>(this.articulosUrl, licencia);
    }
    // Insertar
    return this.http.post<DataPackage>(this.articulosUrl, licencia);
  }

  delete(licencia: Licencia): Observable<DataPackage> {
    return this.http.delete<DataPackage>(this.articulosUrl + "/id/" + licencia.id);
  }
}