package Web2.Web2_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RuntimeErrorException extends RuntimeException {

    public RuntimeErrorException(String message) {
        super(message);
    }

}
