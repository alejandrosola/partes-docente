import { Component, OnInit } from '@angular/core';
import { ParteDiario } from './parte-diario';
import { ParteDiarioService } from './parte-diario.service';
import { Location } from '@angular/common';


@Component({
  selector: 'app-parte-diario',
  templateUrl: 'parte-diario.component.html',
  styleUrls: ['../styles.css']
})
export class ParteDiarioComponent implements OnInit {
  parte!: ParteDiario;
  fecha!: Date;
  fechaString!: string;

  constructor(
    private parteService: ParteDiarioService,
    private location: Location
  ) { }

  ngOnInit() {
    this.fecha = new Date();
    this.fechaString = String(this.fecha.getFullYear()) + "-" + String(this.fecha.getMonth() + 1).padStart(2, '0') + "-" + String(this.fecha.getDate()).padStart(2, '0');
    this.getParteDiario();
  }

  getParteDiario(): void {
    this.parteService.get(this.fechaString.slice(0, 4), this.fechaString.slice(5, 7), this.fechaString.slice(8, 10)).subscribe((dataPackage) => {
      this.parte = <ParteDiario>dataPackage.data;
    });
  }

  goBack(): void {
    this.location.back();
  }
}
