package unpsjb.labprog.backend.util;

import java.sql.SQLException;

import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.model.Cargo;
import unpsjb.labprog.backend.model.EnumTipoDesignacion;

@Service
public class CargoExceptionHandler implements GenericExceptionHandler {

    @Override
    public ResponseEntity<Object> handle(Exception e, Object o) {
        Cargo aCargo = (Cargo) o;

        if (e instanceof HttpMessageNotReadableException) {
            return Response.error("Ingrese un día de la semana");
        }

        if (e instanceof DataIntegrityViolationException) {
            DataIntegrityViolationException dataError = (DataIntegrityViolationException) e;
            Throwable error = dataError.getMostSpecificCause();
            if (error instanceof SQLException) {
                SQLException sqlError = (SQLException) error;
                if ("23505".equals(sqlError.getSQLState())) {
                    if (aCargo.getTipoDesignacion().equals(EnumTipoDesignacion.ESPACIOCURRICULAR)) {
                        return Response.error(String.format(
                                "Ya existe un espacio curricular de nombre %s y división %d° %d°",
                                aCargo.getNombre(), aCargo.getDivision().getAnio(), aCargo.getDivision().getNumero()));
                    }
                    return Response.error(String.format("Ya existe un cargo de nombre %s", aCargo.getNombre()));
                }

                if ("22001".equals(sqlError.getSQLState()))
                    return Response.error("Límite de caracteres excedido");

                if ("23503".equals(sqlError.getSQLState()))
                    return Response.error("No se puede eliminar el cargo porque una designación depende de él");

                return Response.error("Error desconocido: " + sqlError.getSQLState());
            }

            if (error instanceof PropertyValueException) {
                return Response
                        .error("Los campos <nombre> <cargaHoraria> <fechaInicio> y <tipoDesignación> son obligatorios.");
            }
            return Response.error(error.getMessage());
        }
        return Response.error("Error desconocido " + e.getMessage());
    }
}