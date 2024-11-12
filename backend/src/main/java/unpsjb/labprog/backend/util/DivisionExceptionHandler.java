package unpsjb.labprog.backend.util;

import java.sql.SQLException;

import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.model.Division;

@Service
public class DivisionExceptionHandler implements GenericExceptionHandler {

    @Override
    public ResponseEntity<Object> handle(Exception e, Object o) {
        Division aDivision = (Division) o;

        if (e instanceof DataIntegrityViolationException) {
            DataIntegrityViolationException dataError = (DataIntegrityViolationException) e;
            Throwable error = dataError.getMostSpecificCause();
            // Me casé con las bases de datos SQL (Aunque no necesariamente con Postgre)
            if (error instanceof SQLException) {
                SQLException sqlError = (SQLException) error;
                if ("23505".equals(sqlError.getSQLState()))
                    return Response
                            .error("Ya existe una división " + aDivision.getAnio() + "° " + aDivision.getNumero());
                if ("22001".equals(sqlError.getSQLState()))
                    return Response.error("Límite de caracteres excedido");
                if ("23503".equals(sqlError.getSQLState()))
                    return Response.error("No se puede eliminar la división porque un cargo depende de ella");
                return Response.error("Error desconocido: " + sqlError.getSQLState());
            }

            if (error instanceof PropertyValueException) {
                return Response.error("Los campos <anio> y <numero> son obligatorios.");
            }

            return Response.error(e.getMessage());
        }
        return Response.error("Error desconocido");
    }
}
