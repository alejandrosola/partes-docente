import { Component, OnInit } from '@angular/core';
import { ReporteConcepto } from './reporte-concepto';
import { ReporteConceptoService } from './reporte-concepto.service';
import { Location } from '@angular/common';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-reporte-concepto',
  templateUrl: './reporte-concepto.component.html',
  styles: [
  ]
})
export class ReporteConceptoComponent implements OnInit {
  reportes!: ReporteConcepto[];
  fechaString!: string;
  reporteForm!: FormGroup;
  fecha: Date = new Date();

  constructor(
    private reporteService: ReporteConceptoService,
    private location: Location
  ) { }

  ngOnInit() {
    this.fechaString = String(this.fecha.getFullYear()) + "-" + String(this.fecha.getMonth() + 1).padStart(2, '0') + "-" + String(this.fecha.getDate()).padStart(2, '0');
    this.reporteForm = new FormGroup({
      fechaControl: new FormControl(this.fecha, [
        Validators.required
      ])
    })
  }

  getReporteConcepto(): void {
    this.reporteService.get(this.fechaString.slice(5, 7), this.fechaString.slice(0, 4)).subscribe((dataPackage) => {
      this.reportes = <ReporteConcepto[]>dataPackage.data;
    });
  }

  goBack(): void {
    this.location.back();
  }
}
