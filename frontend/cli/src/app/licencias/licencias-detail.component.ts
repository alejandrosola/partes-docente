import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Licencia } from './licencia';
import { Persona } from '../personas/persona';
import { ArticuloLicencia } from './articulo-licencia';
import { LicenciaService } from './licencia.service';
import { PersonaService } from '../personas/persona.service';
import { ArticuloLicenciaService } from './articulo-licencia.service';
import { Observable, catchError, debounceTime, distinctUntilChanged, map, of, switchMap, tap } from 'rxjs';

@Component({
    selector: 'app-cargos-detail',
    templateUrl: './licencias-detail.component.html',
    styleUrls: ['../../styles.css']
})
export class LicenciasDetailComponent implements OnInit {
    licencia!: Licencia;
    articulos!: ArticuloLicencia[];
    errorMessage = "";
    error = false;
    submitted = false;
    editLicenciaForm!: FormGroup;
    searching: boolean = false;
    searchFailed: boolean = false;

    constructor(
        private route: ActivatedRoute,
        private licenciaService: LicenciaService,
        private personaService: PersonaService,
        private articuloService: ArticuloLicenciaService,
        private location: Location) { }

    ngOnInit() {
        this.licencia = <Licencia>{};
        this.articulos = [];
        this.get();

        this.editLicenciaForm = new FormGroup({
            pedidoDesdeControl: new FormControl(this.licencia.pedidoDesde, [
                Validators.required
            ]),
            pedidoHastaControl: new FormControl(this.licencia.pedidoHasta, [
                Validators.required
            ]),
            certificadoControl: new FormControl(this.licencia.certificadoMedico, [
            ]),
            personaControl: new FormControl(this.licencia.persona, [
                Validators.required
            ]),
            articuloControl: new FormControl(this.licencia.articulo, [
                Validators.required
            ])
        });

        this.licencia.certificadoMedico = false;
    }

    get(): void {
        const id = this.route.snapshot.paramMap.get("id")!;
        if (id === "new") {
            this.licencia = <Licencia>{};
        } else {
            this.licenciaService.get(+id).subscribe(dataPackage => this.licencia = <Licencia>dataPackage.data);
        }

        this.articuloService.all().subscribe(dataPackage => {
            this.articulos = <ArticuloLicencia[]>dataPackage.data;
        });
    }


    goBack(): void {
        this.location.back();
    }

    save(): void {
        this.submitted = true;
        if (this.editLicenciaForm.invalid) {
            return;
        }

        this.error = false;

        this.licenciaService.save(this.licencia).subscribe(dataPackage => {
            if (dataPackage.status != 200) {
                this.error = true;
                this.errorMessage = dataPackage.message;
            } else {
                this.goBack();
            }
        });
    }

    searchPersona = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => (this.searching = true)),
      switchMap((term) =>
        this.personaService
          .searchPersona(term)
          .pipe(map((response) => <Persona[]>response.data))
          .pipe(
            tap(() => (this.searchFailed = false)),
            catchError(() => {
              this.searchFailed = true;
              return of([]);
            })
          )
      ),
      tap(() => (this.searching = false))
    );

    searchArticulo = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => (this.searching = true)),
      switchMap((term) =>
        this.articuloService
          .searchArticulo(term)
          .pipe(map((response) => <Persona[]>response.data))
          .pipe(
            tap(() => (this.searchFailed = false)),
            catchError(() => {
              this.searchFailed = true;
              return of([]);
            })
          )
      ),
      tap(() => (this.searching = false))
    );

    resultPersonaFormat(value: any) {
      return value?.nombre +" " + value?.apellido + " " + "(" + value?.cuil + ")";
    }
  
    inputPersonaFormat(value: any) {
      return value?.nombre +" " + value?.apellido + " " + "(" + value?.cuil + ")";
    }

    resultArticuloFormat(value: any) {
        return value?.nombre + " " + value?.descripcion;
    }

    inputArticuloFormat(value: any) {
        return value?.nombre + " " + value?.descripcion;
    }

}
