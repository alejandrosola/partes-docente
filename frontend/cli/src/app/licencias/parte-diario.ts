import { Licencia } from "./licencia";

export interface ParteDiario {
    fecha: Date;
    docentes: Licencia[];
}