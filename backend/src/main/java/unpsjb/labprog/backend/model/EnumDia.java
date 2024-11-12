package unpsjb.labprog.backend.model;

public enum EnumDia {
    Lunes(1),
    Martes(2),
    Miércoles(3),
    Jueves(4),
    Viernes(5),
    Sábado(6),
    Domingo(7);

    private final int valor;

    EnumDia(int valor) {
        this.valor = valor;
    }
    
    public int getValor() {
        return valor;
    }
}