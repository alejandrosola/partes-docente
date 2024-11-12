package unpsjb.labprog.backend.util;

import java.sql.SQLException;

import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.model.Persona;

@Service
public class PersonaExceptionHandler implements GenericExceptionHandler {

    @Override
    public ResponseEntity<Object> handle(Exception e, Object o) {

        Persona aPersona = (Persona) o;

        if (e instanceof DataIntegrityViolationException) {
            DataIntegrityViolationException dataError = (DataIntegrityViolationException) e;
            Throwable error = dataError.getMostSpecificCause();

            // Me casé con las bases de datos SQL (Aunque no necesariamente con Postgre)
            if (error instanceof SQLException) {
                SQLException sqlError = (SQLException) error;
                if ("23505".equals(sqlError.getSQLState()))
                    return Response.error("Ya existe una persona con cuil " + aPersona.getCuil());
                if ("22001".equals(sqlError.getSQLState()))
                    return Response.error("Límite de caracteres excedido");
                if ("23503".equals(sqlError.getSQLState()))
                    return Response.error("No se puede eliminar la persona porque otra tabla depende de ella");
                return Response.error("Error desconocido: " + sqlError.getSQLState());
            }

            if (error instanceof PropertyValueException) {
                return Response.error(
                        "Los campos <dni> <cuil> <nombre> <apellido> <sexo> <domicilio> y <teléfono> son obligatorios.");
            }
            return Response.error(e.getMessage());
        }
        return Response.error("Error desconocido");
    }

}
