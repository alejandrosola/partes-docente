package unpsjb.labprog.backend.util;

import java.sql.SQLException;

import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.Response;

@Service
public class DesignacionExceptionHandler implements GenericExceptionHandler {

    @Override
    public ResponseEntity<Object> handle(Exception e, Object o) {

        if (e instanceof DataIntegrityViolationException) {
            DataIntegrityViolationException dataError = (DataIntegrityViolationException) e;
            Throwable error = dataError.getMostSpecificCause();
            if (error instanceof SQLException) {
                SQLException sqlError = (SQLException) error;
                if ("22001".equals(sqlError.getSQLState()))
                    return Response.error("Límite de caracteres excedido");
                return Response.error("Error desconocido: " + sqlError.getSQLState() + " " + sqlError.getMessage());
            }

            if (error instanceof PropertyValueException) {
                return Response
                        .error("Los campos <situacionRevista> <fechaInicio> <carto> y <persona> son obligatorios.");
            }
            return Response.error(e.getMessage());

        }
        return Response.error("Error desconocido");
    }

}
