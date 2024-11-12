import { Component, OnInit } from '@angular/core';
import { Designacion } from './designacion';
import { ActivatedRoute } from '@angular/router';
import { DesignacionService } from './designacion.service';
import { Location } from '@angular/common';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CargoService } from '../cargos/cargo.service';
import { Persona } from '../personas/persona';
import { Cargo } from '../cargos/cargo';
import { Division } from '../divisiones/division';
import { PersonaService } from '../personas/persona.service';
import { Observable, catchError, debounceTime, distinctUntilChanged, map, of, switchMap, tap } from 'rxjs';

@Component({
  selector: 'app-cargos-detail',
  templateUrl: './designacion-detail.component.html',
  styleUrls: ['./designacion-detail.component.css',
    '../../styles.css']
})
export class DesignacionesDetailComponent implements OnInit {
  designacion!: Designacion;
  errorMessage: string = "";
  error = false;
  submitted = false;
  editDesignacionForm!: FormGroup;
  cargos!: Cargo[];
  searching: boolean = false;
  searchFailed: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private designacionService: DesignacionService,
    private cargoService: CargoService,
    private personaService: PersonaService,
    private location: Location) { }

  ngOnInit() {
    this.designacion = <Designacion>{};
    this.cargos = [];
    this.get();

    this.editDesignacionForm = new FormGroup({
      situacionRevistaControl: new FormControl(this.designacion.situacionRevista, [
        Validators.required,
        Validators.maxLength(45)
      ]),
      fechaInicioControl: new FormControl(this.designacion.fechaInicio, [
        Validators.required
      ]),
      fechaFinControl: new FormControl(this.designacion.fechaFin, [
      ]),
      cargoControl: new FormControl(this.designacion.cargo, [
        Validators.required
      ]),
      personaControl: new FormControl(this.designacion.persona, [
        Validators.required
      ]),
    })
  }

  get(): void {
    const id = this.route.snapshot.paramMap.get("id")!;
    if (id === "new") {
      this.designacion = <Designacion>{};
      this.designacion.cargo = <Cargo>{};
      this.designacion.cargo.division = <Division>{};
    } else {
      this.designacionService.get(+id).subscribe(dataPackage => this.designacion = <Designacion>dataPackage.data);
    }

    this.cargoService.all().subscribe(dataPackage => {
      this.cargos = <Cargo[]>dataPackage.data;
    });
  }


  goBack(): void {
    this.location.back();
  }

  save(): void {
    // Cambiar validators del campo división dependiendo del tipo designación
    this.submitted = true;

    this.error = false;

    this.designacionService.save(this.designacion).subscribe(dataPackage => {
      if (dataPackage.status != 200) {
        this.error = true;
        this.errorMessage = dataPackage.message;
      } else {
        this.goBack();
      }
    })
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

    resultPersonaFormat(value: any) {
      return value?.nombre +" " + value?.apellido + " " + "(" + value?.cuil + ")";
    }
  
    inputPersonaFormat(value: any) {
      return value?.nombre +" " + value?.apellido + " " + "(" + value?.cuil + ")";
    }

    searchCargo = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => (this.searching = true)),
      switchMap((term) =>
        this.cargoService
          .searchCargo(term)
          .pipe(map((response) => <Cargo[]>response.data))
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

    resultCargoFormat(value: any) {
      return value?.division? value?.nombre + " " + value?.division.anio + "° " + value?.division.numero + "° " + value?.tipoDesignacion:
      value?.nombre + " " + value?.tipoDesignacion;
    }

    inputCargoFormat(value: any) {
      return value?.division? value?.nombre + " " + value?.division.anio + "° " + value?.division.numero + "° " + value?.tipoDesignacion:
      value?.nombre + " " + value?.tipoDesignacion;
    }
}
