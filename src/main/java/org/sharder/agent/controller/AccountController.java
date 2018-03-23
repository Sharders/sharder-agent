package org.sharder.agent.controller;

import io.swagger.annotations.*;
import org.sharder.agent.domain.Account;
import org.sharder.agent.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AccountController
 *
 * @author bubai
 * @date 2018/3/18
 */

@RestController
@RequestMapping(path = "/v1/account")
@Api(value = "SharderToken Contract")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    /**
     * Transmit SS SharderToken to a specified Eth address,response with transaction hash immediately
     * @param
     * @return ResponseEntity<JsonResult>
     */
    @ApiOperation(value = "Create a account", notes = "")
    @ApiImplicitParams({
        @ApiImplicitParam(name="p1", value = "paraminfo1", required = true, dataType = "String", paramType = "requestBody"),
        @ApiImplicitParam(name="p2", value = "paraminfo2", required = true, dataType = "String", paramType = "requestBody")
    })
    @RequestMapping(path = "/",method = RequestMethod.POST)
    public ResponseEntity<JsonResult> createAccount(@RequestParam String p1, @RequestParam String p2) {
        JsonResult result = new JsonResult();
        logger.info("Create Account ...");
        try {
            Account account = new Account();
            //TODO
            result.setResult(account);
            result.setStatus("ok");
        } catch (Exception e) {
            String errorInfo = e.getClass().getName() + ":" + e.getMessage();
            result.setResult(errorInfo);
            result.setStatus("error");
            logger.error(errorInfo);
        }
        return ResponseEntity.ok(result);
    }


}
