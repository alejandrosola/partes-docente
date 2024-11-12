import { Component, OnInit } from '@angular/core';
import { Division } from './division';
import { DivisionService } from './division.service';
import { MatDialog } from '@angular/material/dialog';
import { PopUpComponent } from '../pop-up/pop-up.component';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ResultsPage } from '../results-page';

@Component({
  selector: 'app-divisiones',
  templateUrl: './divisiones.component.html',
  styleUrls: ['./divisiones.component.css', '../styles.css'] 
})
export class DivisionesComponent implements OnInit {
  divisiones!: Division[];
  resultsPage: ResultsPage = <ResultsPage>{};
  pages: number[] = [];
  currentPage: number = 0;
  cantByPage: number = 5;
  cantByPageForm!: FormGroup;
  error = false;
  errorMessage = "";

  constructor(
    private divisionService: DivisionService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.divisiones = [];
    this.cantByPageForm = new FormGroup({
      cantByPageControl: new FormControl(this.cantByPage, [
        Validators.required
      ])
    })
    this.getDivisiones();
  }

  delete(division: Division): void {
    this.error = false;
    this.divisionService.delete(division).subscribe(dataPackage => { 
      if (dataPackage.status != 200) {
        this.error = true;
        this.errorMessage = dataPackage.message;
      } else {
        this.getDivisiones();
      }
     });
  }

  getDivisiones(): void {
    if (this.cantByPageForm.invalid) {
      return;
    }
    this.divisionService.byPage(this.currentPage, this.cantByPage).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
      this.divisiones = <Division[]>this.resultsPage.content;
      if (this.divisiones.length == 0 && this.currentPage != 0)
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
    this.getDivisiones();
  };

  closeAlert() {
    this.error = false;
  }

  openDialog(division: Division) {
    let message = "¿Seguro que desea eliminar la división " + division.anio + "° " + division.numero + "° " + division.turno + "?";
    const dialogRef = this.dialog.open(PopUpComponent, { data: message });
    dialogRef.afterClosed().subscribe(result => {
       if(result) {
        this.delete(division);
       }
    });
  }
}
