import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';
import { DataPackage } from "../data-package";
import { Cargo } from './cargo';
import { Division } from '../divisiones/division';
import { Persona } from '../personas/persona';

@Injectable({
  providedIn: 'root'
})
export class CargoService {
  private cargosUrl = 'rest/cargos';
  constructor(private http: HttpClient) { }

  all(): Observable<DataPackage> {
    return this.http.get<DataPackage>(this.cargosUrl); // REST
  }

  get(id: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(this.cargosUrl + "/id/" + id);
  }

  delete(cargo: Cargo): Observable<DataPackage> {
    return this.http.delete<DataPackage>(`${this.cargosUrl}/id/${cargo.id}`);
  }

  save(cargo: Cargo): Observable<DataPackage> {
    // Actualizar
    // buscamos division que corresponde
    if (cargo.id) {
      // actualizamos en el backend
      return this.http.put<DataPackage>(this.cargosUrl, cargo);
    }
    // Insertar
    return this.http.post<DataPackage>(this.cargosUrl, cargo);
  }

  searchCargo(term: string) {
    return this.http.get<DataPackage>(this.cargosUrl + "/search/" + term);
  }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.cargosUrl}/page?page=${page}&size=${size}`); // REST
  }

  getCalendarioDiaEstudiante(aDivision: Division, aDiaDeSemana: string, anAnio: string, aMes: string, aDia: string) {
    return this.http.get<DataPackage>(`${this.cargosUrl}/calendar-division/${aDiaDeSemana}-${aDivision.anio}-${aDivision.numero}-${anAnio}-${aMes}-${aDia}`);
  }

  getCalendarioDiaDocente(aDocente: Persona, aDiaDeSemana: string, aTurno: string, anAnio: string, aMes: string, aDia: string) {
    return this.http.get<DataPackage>(`${this.cargosUrl}/calendar-docente/${aDiaDeSemana}-${aTurno}-${aDocente.cuil}-${anAnio}-${aMes}-${aDia}`);
  }
}
