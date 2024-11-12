import { Component } from '@angular/core';
import { Designacion } from './designacion';
import { MatDialog } from '@angular/material/dialog';
import { PopUpComponent } from '../pop-up/pop-up.component';
import { DesignacionService } from './designacion.service';
import { ResultsPage } from '../results-page';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-cargos',
  templateUrl: './designaciones.component.html',
  styleUrls: ['./designaciones.component.css', '../styles.css']
})
export class DesignacionesComponent {
  designaciones!: Designacion[];
  resultsPage: ResultsPage = <ResultsPage>{};
  pages: number[] = [];
  currentPage: number = 0;
  cantByPage: number = 5;
  cantByPageForm!: FormGroup;

  constructor(
    private designacionService: DesignacionService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.designaciones = [];
    this.cantByPageForm = new FormGroup({
      cantByPageControl: new FormControl(this.cantByPage, [
        Validators.required
      ])
    })
    this.getDesignaciones();
  }

  getDesignaciones(): void {
    if (this.cantByPageForm.invalid) {
      return;
    }
    this.designacionService.byPage(this.currentPage, this.cantByPage).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
      this.designaciones = <Designacion[]>this.resultsPage.content;
      if (this.designaciones.length == 0 && this.currentPage != 0)
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
    this.getDesignaciones();
  };

  delete(designacion: Designacion): void {
    this.designacionService.delete(designacion).subscribe(dataPackage => {
      this.getDesignaciones();
    });
  }

  openDialog(designacion: Designacion) {
    let message = "";
    if (designacion.cargo.tipoDesignacion === "ESPACIOCURRICULAR") {
      message = `¿Seguro que desea eliminar la designación ${designacion.cargo.nombre} de división ${designacion.cargo.division.anio}° ${designacion.cargo.division.numero}° turno ${designacion.cargo.division.turno} a ${designacion.persona.nombre}?`;
    } else {
      message = `¿Seguro que desea eliminar la designación ${designacion.cargo.nombre} a ${designacion.persona.nombre}?`;
    }
    const dialogRef = this.dialog.open(PopUpComponent, { data: message });
    dialogRef.afterClosed().subscribe(confirm => {
      if (confirm) {
        this.delete(designacion);
      }
    });
  }
}
