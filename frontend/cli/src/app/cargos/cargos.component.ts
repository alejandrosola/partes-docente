import { Component } from '@angular/core';
import { Cargo } from './cargo';
import { CargoService } from './cargo.service';
import { MatDialog } from '@angular/material/dialog';
import { PopUpComponent } from '../pop-up/pop-up.component';
import { ResultsPage } from '../results-page';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-cargos',
  templateUrl: './cargos.component.html',
  styleUrls: ['./cargos.component.css', '../styles.css']
})
export class CargosComponent {
  cargos!: Cargo[];
  resultsPage: ResultsPage = <ResultsPage>{};
  pages: number[] = [];
  currentPage: number = 0;
  cantByPage: number = 5;
  cantByPageForm!: FormGroup;
  error = false;
  errorMessage = "";

  constructor(
    private cargoService: CargoService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.cargos = [];
    this.cantByPageForm = new FormGroup({
      cantByPageControl: new FormControl(this.cantByPage, [
        Validators.required
      ])
    })
    this.getCargos();
  }

  getCargos(): void {
    if (this.cantByPageForm.invalid) {
      return;
    }
    this.cargoService.byPage(this.currentPage, this.cantByPage).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
      this.cargos = <Cargo[]>this.resultsPage.content;
      if (this.cargos.length == 0  && this.currentPage != 0)
        this.showPage(-1);
      this.pages = Array.from(Array(this.resultsPage.totalPages).keys());
    });
  }

  showPage(pageId: number): void {
    let page = pageId;
    if (pageId == -2) { // First
      page = 0;
    }
    if (pageId == -1) { // Previous
      page = this.currentPage > 0 ? this.currentPage - 1 : this.currentPage;
    }
    if (pageId == -3) { // Next
      page = !this.resultsPage.last ? this.currentPage + 1 : this.currentPage;
    }
    if (pageId == -4) { // Last
      page = this.resultsPage.totalPages - 1;
    }
    if (pageId > 1 && this.pages.length >= pageId) { // Number
      page = this.pages[pageId - 1] + 1;
    }
    this.currentPage = page;
    this.getCargos();
  };

  delete(cargo: Cargo): void {
    this.error = false;
    this.cargoService.delete(cargo).subscribe(dataPackage => { 
      if (dataPackage.status != 200) {
        this.error = true;
        this.errorMessage = dataPackage.message;
      } else {
        this.getCargos();
      }
     });
  }

  closeAlert() {
    this.error = false;
  }

  openDialog(cargo: Cargo) {
    let message = "";
    if (cargo.tipoDesignacion === "ESPACIOCURRICULAR") {
      message = `¿Seguro que desea eliminar el cargo ${cargo.nombre} de división ${cargo.division.anio}° ${cargo.division.numero}° turno ${cargo.division.turno}?`;
    } else {
      message = `¿Seguro que desea eliminar el cargo ${cargo.nombre}?`;
    }
    const dialogRef = this.dialog.open(PopUpComponent, { data: message });
    dialogRef.afterClosed().subscribe(confirm => {
      if (confirm) {
        this.delete(cargo);
      }
    });
  }
}
