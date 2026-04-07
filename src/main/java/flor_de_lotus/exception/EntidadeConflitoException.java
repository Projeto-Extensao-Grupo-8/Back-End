package flor_de_lotus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntidadeConflitoException extends RuntimeException {
    public EntidadeConflitoException(String message) {
        super(message);
    }
}
