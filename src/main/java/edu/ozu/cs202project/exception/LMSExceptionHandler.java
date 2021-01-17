package edu.ozu.cs202project.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class LMSExceptionHandler {

    public static final int SERVER_ERROR_CODE = 500;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ResponseEntityExceptionHandler delegate =
            new DelegateExceptionHandler();

    public static class DelegateExceptionHandler
            extends ResponseEntityExceptionHandler {
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleException(
            Exception exception, WebRequest request) throws Exception {

        String message = "error: " + exception.getMessage()
                + ", cause: " + exception.getCause();

        logger.warn(message.replaceAll("[\r\n]", " "));

        int status = SERVER_ERROR_CODE;

        if (exception instanceof LMSException) {
            LMSException myException = (LMSException) exception;
            status = Integer.parseInt(myException.getCode());
        } else {
            ResponseEntity response =
                    delegate.handleException(exception, request);
            if (response != null) {
                status = response.getStatusCodeValue();
            }
        }
        return ResponseEntity
                .status(status)
                .body(generateError(
                        String.valueOf(status),
                        exception.getMessage(),
                        String.valueOf(exception.getCause())
                        )
                );
    }


    public final Map<String, Object> generateError(
            String code, String message, String details) {

        Map<String, Object> error = new HashMap<>();
        error.put("code", code);
        error.put("message", message);
        error.put("details", details);
        return Collections.singletonMap("error", error);
    }

}
