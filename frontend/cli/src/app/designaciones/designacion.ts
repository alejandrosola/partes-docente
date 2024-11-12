import { Cargo } from "../cargos/cargo";
import { Persona } from "../personas/persona";

export interface Designacion {
    id: number;
    situacionRevista: string;
    fechaInicio: Date;
    fechaFin: Date;
    cargo: Cargo;
    persona: Persona;
}

