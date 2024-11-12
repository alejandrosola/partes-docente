import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';
import { HttpClient } from "@angular/common/http";
import { DataPackage } from "../data-package";

@Injectable({
  providedIn: 'root'
})
export class ReporteConceptoService {
  private reporteUrl = 'rest/personas/reporte'; // URL to web api

  constructor(private http: HttpClient) { }

  get(aMes: string, anAnio: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.reporteUrl}/${aMes}-${anAnio}`); // REST
  }

}