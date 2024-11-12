import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DataPackage } from "../data-package";
import { Division } from './division';

@Injectable({
  providedIn: 'root'
})
export class DivisionService {
  private divisionesUrl = 'rest/divisiones';
  constructor(private http: HttpClient) { }

  all(): Observable<DataPackage> {
    return this.http.get<DataPackage>(this.divisionesUrl); // REST
  }
  get(id: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(this.divisionesUrl + "/id/" + id);
  }

  save(division: Division): Observable<DataPackage> {
    // Actualizar
    // buscamos division que corresponde
    if (division.id) {
      // actualizamos en el backend
      return this.http.put<DataPackage>(this.divisionesUrl, division);
    }
    // Insertar
    return this.http.post<DataPackage>(this.divisionesUrl, division);
  }

  delete(division: Division): Observable<DataPackage> {
    return this.http.delete<DataPackage>(this.divisionesUrl + "/" + division.anio + "-" + division.numero);
  }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.divisionesUrl}/page?page=${page}&size=${size}`); // REST
  }

  searchDivision(term: string) {
    return this.http.get<DataPackage>(this.divisionesUrl + "/search/" + term);
  }
}