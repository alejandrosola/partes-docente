package unpsjb.labprog.backend.util;

import java.sql.SQLException;

import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.model.ArticuloLicencia;

@Service
public class ArticuloLicenciaExceptionHandler implements GenericExceptionHandler {

    @Override
    public ResponseEntity<Object> handle(Exception e, Object o) {
        ArticuloLicencia anArticuloLicencia = (ArticuloLicencia) o;

        if (e instanceof DataIntegrityViolationException) {
            DataIntegrityViolationException dataError = (DataIntegrityViolationException) e;
            Throwable error = dataError.getMostSpecificCause();
            if (error instanceof SQLException) {
                SQLException sqlError = (SQLException) error;
                if ("23505".equals(sqlError.getSQLState())) {
                    return Response.error(String.format("Ya existe un artículo de nombre %s", anArticuloLicencia.getNombre()));
                }
    
                if ("22001".equals(sqlError.getSQLState()))
                    return Response.error("Límite de caracteres excedido");
                return Response.error("Error desconocido: " + sqlError.getSQLState() + sqlError.getMessage());
            }
    
            if (error instanceof PropertyValueException) {            
                return Response.error("El campo <nombre> es obligatorio.");
            }
            return Response.error(error.getMessage());
        }
        return Response.error("Error desconocido");
    }
}