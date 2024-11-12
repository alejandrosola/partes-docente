import { Component, OnInit } from '@angular/core';
import { Cargo } from './cargo';
import { ActivatedRoute, Router } from '@angular/router';
import { CargoService } from './cargo.service';
import { Location } from '@angular/common';
import { Division } from '../divisiones/division';
import { DivisionService } from '../divisiones/division.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Horario } from '../horarios/horario';
import { MatDialog } from '@angular/material/dialog';
import { PopUpComponent } from '../pop-up/pop-up.component';
import { Observable, catchError, debounceTime, distinctUntilChanged, map, of, switchMap, tap } from 'rxjs';

@Component({
  selector: 'app-cargos-detail',
  templateUrl: './cargos-detail.component.html',
  styleUrls: ['./cargos-detail.component.css',
    '../../styles.css']
})
export class CargosDetailComponent implements OnInit {
  cargo!: Cargo;
  errorMessage = "";
  error = false;
  submitted = false;
  editCargoForm!: FormGroup;
  searching: boolean = false;
  searchFailed: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private cargoService: CargoService,
    private divisionService: DivisionService,
    private location: Location,
    private router: Router,
    private dialog: MatDialog,
  ) { }

  ngOnInit() {
    this.cargo = <Cargo>{};
    this.get();

    this.editCargoForm = new FormGroup({
      nombreControl: new FormControl(this.cargo.nombre, [
        Validators.required
      ]),
      tipoDesignacionControl: new FormControl(this.cargo.tipoDesignacion, [
        Validators.required
      ]),
      cargaHorariaControl: new FormControl(this.cargo.cargaHoraria, [
        Validators.required
      ]),
      fechaInicioControl: new FormControl(this.cargo.fechaInicio, [
        Validators.required
      ]),
      fechaFinControl: new FormControl(this.cargo.fechaFin, [
      ]),
      divisionControl: new FormControl(this.cargo.division, [
        Validators.required
      ])
    })
  }

  get(): void {
    const id = this.route.snapshot.paramMap.get("id")!;
    if (id === "new") {
      this.cargo = <Cargo>{};
      this.cargo.horarios = <Horario[]>[];
    } else {
      this.cargoService.get(+id).subscribe(dataPackage => this.cargo = <Cargo>dataPackage.data);
    }

  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    // Cambiar validators del campo división dependiendo del tipo designación
    this.submitted = true;
    this.editCargoForm.get('tipoDesignacionControl')!.valueChanges.subscribe(value => {
      if (value === 'CARGO') {
        this.editCargoForm.get('divisionControl')!.clearValidators();
      } else {
        this.editCargoForm.get('divisionControl')!.setValidators(Validators.required);
      }

      this.editCargoForm.get('divisionControl')!.updateValueAndValidity();
      if (this.editCargoForm.invalid) {
        return;
      }
    });

    this.error = false;

    this.cargoService.save(this.cargo).subscribe(dataPackage => {
      if (dataPackage.status != 200) {
        this.error = true;
        this.errorMessage = dataPackage.message;
      } else {
        this.router
          .navigateByUrl("/", {
            skipLocationChange: true,
          })
          .then(() =>
            this.router.navigate(["/cargos/id/" + (<Cargo>dataPackage.data).id]));
      }
    });
  }

  addHorario(): void {
    console.log(this.cargo.horarios);
    this.cargo.horarios = this.cargo.horarios.concat([<Horario>{}]);
    console.log(this.cargo.horarios);
  }

  removeHorario(horario: Horario): void {
    let horarios = this.cargo.horarios;
    horarios.splice(horarios.indexOf(horario), 1);
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

  openDialog(horario: Horario) {
    const dialogRef = horario.dia && horario.hora ? this.dialog.open(PopUpComponent, { data: `¿Seguro que desea eliminar el horario ${horario.dia} ${horario.hora}?` }) :
      this.dialog.open(PopUpComponent, { data: "¿Seguro que desea eliminar el horario?" });

    dialogRef.afterClosed().subscribe(confirm => {
      if (confirm) {
        this.removeHorario(horario);
      }
    });
  }

  
}
