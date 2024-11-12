import { Persona } from "../personas/persona";
import { ArticuloLicencia } from "./articulo-licencia";

export interface Licencia {
    id: number;
    pedidoDesde: Date;
    pedidoHasta: Date;
    certificadoMedico: boolean;
    articulo: ArticuloLicencia;
    persona: Persona;
}