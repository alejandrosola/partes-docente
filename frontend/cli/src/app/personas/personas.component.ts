import { Component, OnInit } from '@angular/core';

import { Persona } from './persona';
import { PersonaService } from './persona.service';
import { MatDialog } from '@angular/material/dialog';
import { PopUpComponent } from '../pop-up/pop-up.component';
import { ResultsPage } from "../results-page";
import { FormControl, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-plays',
  templateUrl: './personas.component.html',
  styleUrls: ['./personas.component.css', '../styles.css']
})
export class PersonasComponent implements OnInit {

  personas!: Persona[];
  resultsPage: ResultsPage = <ResultsPage>{};
  pages: number[] = [];
  currentPage: number = 0;
  cantByPage: number = 5;
  cantByPageForm!: FormGroup;
  error = false;
  errorMessage = "";

  constructor(
    private personaService: PersonaService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.personas = [];
    this.cantByPageForm = new FormGroup({
      cantByPageControl: new FormControl(this.cantByPage, [
        Validators.required
      ])
    })
    this.getPersonas();
  }

  getPersonas(): void {
    if (this.cantByPageForm.invalid) {
      return;
    }
    this.personaService.byPage(this.currentPage, this.cantByPage).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
      this.personas = <Persona[]>this.resultsPage.content;
      if (this.personas.length == 0 && this.currentPage != 0)
        this.showPage(-1);
      this.pages = Array.from(Array(this.resultsPage.totalPages).keys());
    });
  }

  delete(aPersona: Persona): void {
    this.error = false;
    this.personaService.delete(aPersona).subscribe(dataPackage => { 
      if (dataPackage.status != 200) {
        this.error = true;
        this.errorMessage = dataPackage.message;
      } else {
        this.getPersonas();
      }
     });
  }

  closeAlert() {
    this.error = false;
  }

  openDialog(aPersona: Persona) {
    let message = "¿Seguro que desea eliminar a " + aPersona.nombre + "?";
    const dialogRef = this.dialog.open(PopUpComponent, { data: message });
    dialogRef.afterClosed().subscribe(confirm => {
      if (confirm) {
        this.delete(aPersona);
      }
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
    this.getPersonas();
  };

}
