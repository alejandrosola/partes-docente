package unpsjb.labprog.backend.util;

import java.sql.SQLException;

import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.model.Licencia;

@Service
public class LicenciaExceptionHandler implements GenericExceptionHandler {
    @Override
    public ResponseEntity<Object> handle(Exception e, Object o) {

        Licencia aLicencia = (Licencia) o;

        if (e instanceof DataIntegrityViolationException) {
            DataIntegrityViolationException dataError = (DataIntegrityViolationException) e;
            Throwable error = dataError.getMostSpecificCause();

            // Me casé con las bases de datos SQL (Aunque no necesariamente con Postgre)
            if (error instanceof SQLException) {
                SQLException sqlError = (SQLException) error;
                if ("23505".equals(sqlError.getSQLState()))
                    return Response.error("Ya existe una licencia con id " + aLicencia.getId());
                if ("22001".equals(sqlError.getSQLState()))
                    return Response.error("Límite de caracteres excedido");
                return Response.error("Error desconocido: " + sqlError.getSQLState());
            }

            if (error instanceof PropertyValueException) {
                return Response.error(
                        "Los campos <pedidoDesde> <pedidoHasta> <domicilio> <certificadoMedico> <articulo> y <persona> son obligatorios.");
            }

            return Response.error(e.getMessage());
        }
        if (e instanceof UnsupportedOperationException) {
            return Response.error(e.getMessage());
        }
        return Response.error("Error desconocido");
    }
}
