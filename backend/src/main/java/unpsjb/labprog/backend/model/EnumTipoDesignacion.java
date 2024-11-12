package unpsjb.labprog.backend.model;

public enum EnumTipoDesignacion {
    CARGO("CARGO"),
    ESPACIOCURRICULAR("ESPACIOCURRICULAR");

    private final String valor;

    EnumTipoDesignacion(String valor) {
        this.valor = valor;
    }
    
    public String getValor() {
        return valor;
    }
}
