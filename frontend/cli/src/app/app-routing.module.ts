import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PersonasComponent } from './personas/personas.component';
import { PersonasDetailComponent } from './personas/personas-detail.component';
import { DivisionesComponent } from './divisiones/divisiones.component';
import { DivisionesDetailComponent } from './divisiones/divisiones-detail.component';
import { CargosComponent } from './cargos/cargos.component';
import { CargosDetailComponent } from './cargos/cargos-detail.component';
import { DesignacionesComponent } from './designaciones/designaciones.component';
import { DesignacionesDetailComponent } from './designaciones/designaciones-detail.component';
import { LicenciasComponent } from './licencias/licencias.component';
import { LicenciasDetailComponent } from './licencias/licencias-detail.component';
import { HorariosComponent } from './horarios/horarios.component';
import { HorariosDetailComponent } from './horarios/horarios-detail.component';
import { ParteDiarioComponent } from './licencias/parte-diario.component';
import { CargosCalendarComponent } from './cargos/cargos-calendar.component';
import { ReporteConceptoComponent } from './personas/reporte-concepto.component';

const routes: Routes = [{ path: '', component: HomeComponent },
  { path: 'personas', component: PersonasComponent },
  { path: 'personas/id/:id', component: PersonasDetailComponent },
  { path: 'divisiones', component: DivisionesComponent },
  { path:'divisiones/id/:id', component: DivisionesDetailComponent },
  { path: 'cargos', component: CargosComponent },
  { path: 'cargos/id/:id', component: CargosDetailComponent },
  { path: 'designaciones', component: DesignacionesComponent },
  { path: 'designaciones/id/:id', component: DesignacionesDetailComponent},
  { path: 'licencias', component: LicenciasComponent},
  { path: 'licencias/id/:id', component: LicenciasDetailComponent},
  { path: 'licencias/partediario', component: ParteDiarioComponent},
  { path: 'horarios', component: HorariosComponent },
  { path: 'horarios/id/:id', component: HorariosDetailComponent },
  { path: 'cargos/calendar', component: CargosCalendarComponent },
  { path: 'personas/reporte', component: ReporteConceptoComponent}
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
