import { Component, OnInit } from '@angular/core';
import { Licencia } from './licencia';
import { ResultsPage } from '../results-page';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { LicenciaService } from './licencia.service';
import { PopUpComponent } from '../pop-up/pop-up.component';

@Component({
  selector: 'app-licencias',
  templateUrl: './licencias.component.html',
  styleUrls: ['../styles.css']
})
export class LicenciasComponent implements OnInit {
  licencias!: Licencia[];
  resultsPage: ResultsPage = <ResultsPage>{};
  pages: number[] = [];
  currentPage: number = 0;
  cantByPage: number = 5;
  cantByPageForm!: FormGroup;
  
  constructor(
    private licenciaService: LicenciaService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.licencias = [];
    this.cantByPageForm = new FormGroup({
      cantByPageControl: new FormControl(this.cantByPage, [
        Validators.required
      ])
    })
    this.getLicencias();
  }

  delete(licencia: Licencia): void {
    this.licenciaService.delete(licencia).subscribe(dataPackage => {
      this.getLicencias();
     });
  }

  getLicencias(): void {
    if (this.cantByPageForm.invalid) {
      return;
    }
    this.licenciaService.byPage(this.currentPage, this.cantByPage).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
      this.licencias = <Licencia[]>this.resultsPage.content;
      if (this.licencias.length == 0 && this.currentPage != 0)
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
    this.getLicencias();
  };

  openDialog(licencia: Licencia) {
    let message = `¿Seguro que desea eliminar la licencia ${licencia.articulo.nombre} a la persona ${licencia.persona.nombre} ${licencia.persona.apellido}?`;
    const dialogRef = this.dialog.open(PopUpComponent, { data: message });
    dialogRef.afterClosed().subscribe(confirm => {
       if(confirm) {
        this.delete(licencia);
       }
    });
  }
}
