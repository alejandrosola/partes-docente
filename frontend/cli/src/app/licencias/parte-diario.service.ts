import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DataPackage } from "../data-package";

@Injectable({
  providedIn: 'root'
})
export class ParteDiarioService {
  private partesUrl = 'rest/licencias/partediario';
  constructor(private http: HttpClient) { }

  get(anio: string, mes: string, dia: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.partesUrl}/${anio}-${mes}-${dia}`);
  }


}