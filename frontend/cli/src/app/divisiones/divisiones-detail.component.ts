import { Component, OnInit } from '@angular/core';
import { Division } from './division';
import { ActivatedRoute } from '@angular/router';
import { DivisionService } from './division.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';

@Component({
  selector: 'app-divisiones-detail',
  templateUrl: './divisiones-detail.component.html',
  styleUrls: ['./divisiones-detail.component.css',
    '../../styles.css']
})
export class DivisionesDetailComponent implements OnInit {
  division!: Division;
  editDivisionForm!: FormGroup;
  submitted = false;
  error = false;
  errorMessage = "";

  constructor(
    private route: ActivatedRoute,
    private divisionService: DivisionService,
    private location: Location,) { }

  ngOnInit() {
    this.division = <Division>{};
    this.get();

    this.editDivisionForm = new FormGroup({
      anioControl: new FormControl(this.division.anio, [
        Validators.required
      ]),
      numeroControl: new FormControl(this.division.numero, [
        Validators.required
      ]),
      orientacionControl: new FormControl(this.division.orientacion, [

      ]),
      turnoControl: new FormControl(this.division.turno, [

      ])
    })
  }

  goBack(): void {
    this.location.back();
  }

  get(): void {
    const id = this.route.snapshot.paramMap.get("id")!;
    if (id === "new") {
      this.division = <Division>{};
    } else {
      this.divisionService.get(+id).subscribe(dataPackage => this.division = <Division>dataPackage.data);
    }
  }



  save(): void {
    this.submitted = true;
    this.error = false;

    if (this.editDivisionForm.invalid) {
      return;
    }

    this.divisionService.save(this.division).subscribe(dataPackage => {
      if (dataPackage.status != 200) {
        this.error = true;
        this.errorMessage = dataPackage.message;
      } else {
        this.goBack()
      }
    });
  }

  getInputValueById(id: string): string | undefined {
    let input = document.getElementById(id) as HTMLInputElement | null;
    return input?.value;
  }



}
