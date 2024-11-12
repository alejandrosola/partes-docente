export interface Division {
    id: number;
    anio: number;
    numero: number;
    orientacion: string;
    turno: EnumTurno;
}

export enum EnumTurno {
    maniana= "Maniana",
    tarde = "Tarde",
    vespertino = "Vespertino",
    noche = "Noche"
}