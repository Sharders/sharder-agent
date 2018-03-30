package org.sharder.agent.exception;

import org.sharder.agent.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public JsonResult defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        JsonResult result = new JsonResult();
        e.printStackTrace();
        String errorInfo = e.getClass().getName() + ":" + e.getMessage();
        logger.error(errorInfo);
        result.setResult(errorInfo);
        result.setStatus("error");
        return result;
    }
}
