package es.safareyes.schoolevents.Errores;

public class ReglaNegocioException extends RuntimeException {
    public ReglaNegocioException(String mensaje) { super(mensaje); }
}
