package com.example.organization.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * IllegalArgumentException 일때만 code: BAD_REQUEST, message: 작성한 메시지
     *
     * @param e
     * @return
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> badRequestException(IllegalArgumentException e) {
        logger.warn("error", e);
        DefaultApiException defaultApiException =
            new DefaultApiException("BAD_REQUEST", e.getMessage());
        return new ResponseEntity<>(defaultApiException, HttpStatus.BAD_REQUEST);
    }

    /**
     * 나머지 Exception들
     *
     * @param e
     * @return
     */
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> badRequestException(RuntimeException e) {
        logger.warn("error", e);
        DefaultApiException defaultApiException =
            new DefaultApiException("INTERNAL_SERVER_ERROR", e.getMessage());
        return new ResponseEntity<>(defaultApiException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
