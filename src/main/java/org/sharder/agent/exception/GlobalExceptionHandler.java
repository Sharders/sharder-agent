package org.sharder.agent.exception;

import org.sharder.agent.domain.ErrorDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * GlobalExceptionHandler
 *
 * @author bubai
 * @date 2018/3/26
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * handle global exceptions
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<String> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        e.printStackTrace();
        String errorInfo = e.getClass().getName() + ":" + e.getMessage();
        logger.error(errorInfo);
        ResponseEntity responseEntity = new ResponseEntity(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }

    @ExceptionHandler(value = SharderAgentException.class)
    @ResponseBody
    public ResponseEntity<ErrorDescription> sharderAgentErrorHandler(HttpServletRequest req, SharderAgentException e) throws Exception {
        e.printStackTrace();
        String errorInfo = e.getClass().getName() + ":" + e.getMessage();
        logger.error(errorInfo);
        ResponseEntity responseEntity = new ResponseEntity(e.getErrorDescription(), HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }
}
