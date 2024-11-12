package unpsjb.labprog.backend.util;

import org.springframework.http.ResponseEntity;

interface GenericExceptionHandler {
    public ResponseEntity<Object> handle(Exception e, Object o);
}
