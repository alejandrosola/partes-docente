import { Component } from '@angular/core';
import { Persona } from './persona';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { PersonaService } from './persona.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-personas-detail',
  templateUrl: './personas-detail.component.html',
  styleUrls: ['./personas-detail.component.css',
    '../../styles.css'],
})
export class PersonasDetailComponent {
  persona!: Persona;
  editPersonaForm!: FormGroup;
  submitted = false;
  error = false;
  errorMessage = "";

  constructor(
    private route: ActivatedRoute,
    private personaService: PersonaService,
    private location: Location,
  ) {
  }

  ngOnInit() {
    this.persona = <Persona>{};
    this.get();
    this.editPersonaForm = new FormGroup({
      dniControl: new FormControl(this.persona.dni, [
        Validators.required
      ]),
      cuilControl: new FormControl(this.persona.cuil, [
        Validators.required,
        Validators.maxLength(30)
      ]),
      nombreControl: new FormControl(this.persona.nombre, [
        Validators.required,
        Validators.maxLength(90)
      ]),
      apellidoControl: new FormControl(this.persona.apellido, [
        Validators.required,
        Validators.maxLength(90)
      ]),
      tituloControl: new FormControl(this.persona.titulo, [
        Validators.maxLength(90)
      ]),
      sexoControl: new FormControl(this.persona.sexo),
      domicilioControl: new FormControl(this.persona.domicilio, [
        Validators.required,
        Validators.maxLength(90)
      ]),
      telefonoControl: new FormControl(this.persona.telefono, [
        Validators.required,
        Validators.maxLength(90)
      ]),
    })

  }


  get(): void {
    const id = this.route.snapshot.paramMap.get("id")!;
    if (id === "new") {
      this.persona = <Persona>{};
    } else {
      this.personaService.get(+id).subscribe(dataPackage => this.persona = <Persona>dataPackage.data);
    }
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.submitted = true;

    this.error = false;
    if (this.editPersonaForm.invalid) {
      return;
    }

    this.personaService.save(this.persona).subscribe(dataPackage => {
      if (dataPackage.status != 200) {
        this.error = true;
        this.errorMessage = dataPackage.message;
      } else {
        this.goBack()
      }
    });
  }

}
