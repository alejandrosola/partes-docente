import { Division } from "../divisiones/division";
import { Horario } from "../horarios/horario";

export interface Cargo {
    id: number;
    nombre: string;
    cargaHoraria: number;
    fechaInicio: Date;
    fechaFin: Date;
    division: Division;
    tipoDesignacion: string;
    horarios: Horario[];
}

export const HorasTurno = {
    'Mañana': ['07:45', '08:25', '09:05', '09:15', '09:55', '10:35', '10:45', '11:25', '12:05', '12:45', '13:25'],
    'Tarde': ['13:30', '14:10', '14:50', '15:00', '15:40', '16:20', '16:30', '17:10', '17:50', '18:30'],
    'Vespertino': ["18:45", "19:25", "20:05"],
    'Noche': ["22:00", "22:40", "23:20"]
}