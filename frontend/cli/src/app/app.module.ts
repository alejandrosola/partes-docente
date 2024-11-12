import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AppRoutingModule } from './app-routing.module';
import { PersonasComponent } from './personas/personas.component';
import { PersonasDetailComponent } from './personas/personas-detail.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from "@angular/common/http";
import { MatDialogModule } from '@angular/material/dialog';
import { PopUpComponent } from './pop-up/pop-up.component';
import { DivisionesComponent } from './divisiones/divisiones.component';
import { DivisionesDetailComponent } from './divisiones/divisiones-detail.component';
import { CargosComponent } from './cargos/cargos.component';
import { CargosDetailComponent } from './cargos/cargos-detail.component';
import { DesignacionesComponent } from './designaciones/designaciones.component';
import { DesignacionesDetailComponent } from './designaciones/designaciones-detail.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { LicenciasComponent } from './licencias/licencias.component';
import { LicenciasDetailComponent } from './licencias/licencias-detail.component';
import { HorariosComponent } from './horarios/horarios.component';
import { HorariosDetailComponent } from './horarios/horarios-detail.component';
import { ParteDiarioComponent } from './licencias/parte-diario.component';
import { CargosCalendarComponent } from './cargos/cargos-calendar.component';
import { ReporteConceptoComponent } from './personas/reporte-concepto.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    PersonasComponent,
    PersonasDetailComponent,
    PopUpComponent,
    DivisionesComponent,
    DivisionesDetailComponent,
    CargosComponent,
    CargosDetailComponent,
    DesignacionesComponent,
    DesignacionesDetailComponent,
    LicenciasComponent,
    LicenciasDetailComponent,
    HorariosComponent,
    HorariosDetailComponent,
    ParteDiarioComponent,
    CargosCalendarComponent,
    ReporteConceptoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatDialogModule,
    NgbModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
