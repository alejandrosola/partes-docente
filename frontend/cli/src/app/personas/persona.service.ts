import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';
import { Persona } from './persona';
import { HttpClient } from "@angular/common/http";
import { DataPackage } from "../data-package";

@Injectable({
  providedIn: 'root'
})
export class PersonaService {
  private personasUrl = 'rest/personas'; // URL to web api

  constructor(private http: HttpClient) { }

  all(): Observable<DataPackage> {
    return this.http.get<DataPackage>(this.personasUrl); // REST
  }

  get(id: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(this.personasUrl + "/id/" + id);
  }

  save(persona: Persona): Observable<DataPackage> {
    return this.http[persona.id ? 'put' : 'post']<DataPackage>(this.personasUrl, persona);
  }

  delete(persona: Persona): Observable<DataPackage> {
    return this.http.delete<DataPackage>(this.personasUrl + "/" +  persona.cuil);
  }

  searchPersona(term: string) {
    return this.http.get<DataPackage>(this.personasUrl + "/search/" + term);
  }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.personasUrl}/page?page=${page}&size=${size}`); // REST
  }

}