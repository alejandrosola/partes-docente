import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";

import { Horario } from "./horario";
import { DataPackage } from "../data-package";

@Injectable({
  providedIn: "root",
})
export class HorarioService {
  private horariosUrl = "rest/horarios"; // URL to web api

  constructor(private http: HttpClient) {}

  get(id: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.horariosUrl}/${id}`);
  }

  save(horario: Horario): Observable<DataPackage> {
    return this.http[horario.id ? "put" : "post"]<DataPackage>(
      this.horariosUrl,
      horario
    );
  }

  byPage(page: number, cant: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(
      `${this.horariosUrl}?page=${page}&cant=${cant}`
    );
  }

  remove(id: number): Observable<DataPackage> {
    return this.http["delete"]<DataPackage>(`${this.horariosUrl}/${id}`);
  }
}