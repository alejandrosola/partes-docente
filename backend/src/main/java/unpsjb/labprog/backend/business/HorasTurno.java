package unpsjb.labprog.backend.business;

import java.util.HashMap;
import java.util.Map;

import unpsjb.labprog.backend.model.EnumTurno;

public class HorasTurno {
    public static final Map<EnumTurno, String[]> HORAS = new HashMap<>();

    static {
        HORAS.put(EnumTurno.Mañana, new String[]{"07:45", "08:25", "09:05", "09:15", "09:55", "10:35", "10:45", "11:25", "12:05", "12:45", "13:25"});
        HORAS.put(EnumTurno.Tarde, new String[]{"13:30", "14:10", "14:50", "15:00", "15:40", "16:20", "16:30", "17:10", "17:50", "18:30"});
        HORAS.put(EnumTurno.Vespertino, new String[]{"18:45", "19:25", "20:05"});
        HORAS.put(EnumTurno.Noche, new String[]{"22:00", "22:40", "23:20"});
    }
}