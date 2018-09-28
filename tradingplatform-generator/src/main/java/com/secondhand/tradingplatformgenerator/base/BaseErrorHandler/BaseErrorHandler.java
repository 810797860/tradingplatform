package com.secondhand.tradingplatformgenerator.base.BaseErrorHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author 81079
 */

@ControllerAdvice
public class BaseErrorHandler {
    private final Logger logger = LoggerFactory.getLogger(BaseErrorHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void validationBadRequest(IllegalArgumentException e){
        logger.info("Returning HTTP 400 Bad Request", e);
    }
}
