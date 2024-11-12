import { Component, OnInit } from '@angular/core';
import { Cargo, HorasTurno } from './cargo';
import { CargoService } from './cargo.service';
import { Location } from '@angular/common';
import { Division } from '../divisiones/division';
import { Observable, catchError, debounceTime, distinctUntilChanged, map, of, switchMap, tap } from 'rxjs';
import { DivisionService } from '../divisiones/division.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Persona } from '../personas/persona';
import { PersonaService } from '../personas/persona.service';

@Component({
  selector: 'app-cargos-calendar',
  templateUrl: 'cargos-calendar.component.html',
  styles: [
  ]
})
export class CargosCalendarComponent implements OnInit {
  cargos!: Cargo[];
  horas!: string[];
  dias!: string[];
  searching: boolean = false;
  searchFailed: boolean = false;
  division!: Division;
  fecha: Date = new Date();
  fechaString!: string;
  tabla: { [key: string]: string } = {};
  calendarForm!: FormGroup;
  submitted: boolean = false;
  valid: boolean = false;
  consultante!: number;
  docente!: Persona;
  turno!: string;

  constructor(
    private cargoService: CargoService,
    private location: Location,
    private divisionService: DivisionService,
    private personaService: PersonaService

  ) { }

  ngOnInit(): void {
    this.consultante = 1;
    this.dias = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes'];
    this.horas = HorasTurno.Tarde;
    this.fechaString = String(this.fecha.getFullYear()) + "-" + String(this.fecha.getMonth() + 1).padStart(2, '0') + "-" + String(this.fecha.getDate()).padStart(2, '0');
    
    this.calendarForm = new FormGroup({
      divisionControl: new FormControl(this.division, [
        Validators.required
      ]),
      fechaControl: new FormControl(this.fecha, [
        Validators.required
      ]),
      docenteControl: new FormControl(this.docente, [
      ]),
      turnoControl: new FormControl(this.turno, [
      ])
    })
  }

  cargarTablaEstudiante(): void {
    this.submitted = true;

    this.calendarForm.get('docenteControl')!.clearValidators();
    this.calendarForm.get('turnoControl')!.clearValidators();
    this.calendarForm.get('divisionControl')!.clearValidators();
    this.calendarForm.get('divisionControl')!.setValidators(Validators.required);
    this.calendarForm.get('divisionControl')!.updateValueAndValidity();
    this.calendarForm.get('docenteControl')!.updateValueAndValidity();
    if (this.calendarForm.invalid) {
      this.valid = false;
      return;
    }
    this.horas = HorasTurno[<string>this.division.turno as keyof typeof HorasTurno];
    this.valid = true;
    for (let dia of this.dias) {
      this.cargoService.getCalendarioDiaEstudiante(this.division, dia, this.fechaString.slice(0, 4), this.fechaString.slice(5, 7), this.fechaString.slice(8, 10)).subscribe(dataPackage => {
        for (let hora of this.horas) {
          let temp: { [key: string]: string[] } = <{ [key: string]: string[] }>dataPackage.data;
          this.tabla[hora + dia] = "";
          for (let cargo of temp[hora]) {
            this.tabla[hora + dia] += cargo + "<br>";
          }
          this.tabla[hora + dia] = this.tabla[hora + dia] == ""? "-": this.tabla[hora + dia];
        }
      });
    }
  }

  cargarTablaDocente(): void {
    this.submitted = true;

    this.calendarForm.get('docenteControl')!.clearValidators();
    this.calendarForm.get('divisionControl')!.clearValidators();
    this.calendarForm.get('turnoControl')!.clearValidators();
    this.calendarForm.get('docenteControl')!.setValidators(Validators.required);
    this.calendarForm.get('turnoControl')!.setValidators(Validators.required);
    this.calendarForm.get('divisionControl')!.updateValueAndValidity();
    this.calendarForm.get('docenteControl')!.updateValueAndValidity();
    if (this.calendarForm.invalid) {
      this.valid = false;
      return;
    }

    this.horas = HorasTurno[this.turno as keyof typeof HorasTurno];
    this.valid = true;
    for (let dia of this.dias) {
      this.cargoService.getCalendarioDiaDocente(this.docente, dia, this.turno, this.fechaString.slice(0, 4), this.fechaString.slice(5, 7), this.fechaString.slice(8, 10)).subscribe(dataPackage => {
        for (let hora of this.horas) {
          let temp: { [key: string]: string[] } = <{ [key: string]: string[] }>dataPackage.data;
          this.tabla[hora + dia] = "";
          for (let cargo of temp[hora]) {
            this.tabla[hora + dia] += cargo + "<br>";
          }
          this.tabla[hora + dia] = this.tabla[hora + dia] == ""? "-": this.tabla[hora + dia];
        }
      });
    }

  }

  goBack(): void {
    this.location.back();
  }

  searchDivision = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => (this.searching = true)),
      switchMap((term) =>
        this.divisionService
          .searchDivision(term)
          .pipe(map((response) => <Division[]>response.data))
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

  resultFormat(value: any) {
    return value?.anio + "° " + value?.numero + "° " + value?.turno;
  }

  inputFormat(value: any) {
    return value?.anio + "° " + value?.numero + "° " + value?.turno;
  }

  searchDocente = (text$: Observable<string>) =>
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

  resultDocenteFormat(value: any) {
    return value?.nombre +" " + value?.apellido + " " + "(" + value?.cuil + ")";
  }

  inputDocenteFormat(value: any) {
    return value?.nombre +" " + value?.apellido + " " + "(" + value?.cuil + ")";
  }

}