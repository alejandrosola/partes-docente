package unpsjb.labprog.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class Response {

	public static ResponseEntity<Object> response(HttpStatus status, String message, Object responseObj) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("status", status.value());
		map.put("message", message);		
		map.put("data", responseObj);

		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	public static ResponseEntity<Object> ok(Object responseObj) {
		return response(HttpStatus.OK, "OK", responseObj);
	}

	public static ResponseEntity<Object> ok(Object responseObj, String message) {
		return response(HttpStatus.OK, message, responseObj);
	}

	public static ResponseEntity<Object> badRequest() {
		return response(HttpStatus.OK, "Bad request", null);
	}

	public static ResponseEntity<Object> badRequest(String message) {
		return response(HttpStatus.OK, message, null);
	}

	public static ResponseEntity<Object> notFound() {
		return response(HttpStatus.OK, "Not Found", null);
	}
	
	public static ResponseEntity<Object> notFound(String message) {
		return response(HttpStatus.NOT_FOUND, message, null);
	}

	public static ResponseEntity<Object> error(String message) {
		return response(HttpStatus.CONFLICT, message, null);
	}

}
