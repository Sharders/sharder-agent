package org.sharder.agent.filter;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestNotFoundFilter for 404
 *
 * @author bubai
 *
 */
@RestController
public class RestNotFoundFilter implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleError() {
        return new ResponseEntity("Request resource not found.", HttpStatus.NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}