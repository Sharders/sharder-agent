package org.sharder.agent.controller;

import io.swagger.annotations.*;
import org.sharder.agent.domain.Account;
import org.sharder.agent.service.AccountService;
import org.sharder.agent.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(value = "Account Operations")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;
    /**
     * Create an account with secret phrase
     * @param secretPhrase
     * @return ResponseEntity<JsonResult>
     */
    @ApiOperation(value = "Create a account", notes = "Create a account ID with secretPhrase")
    @ApiImplicitParams({
        @ApiImplicitParam(name="secretPhrase", value = "secretPhrase", required = true, dataType = "String", paramType = "requestBody"),
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<JsonResult> createAccount(@RequestParam String secretPhrase) {
        JsonResult result = new JsonResult();
        try {
            Account account = accountService.getAccountId(secretPhrase);
            result.setResult(account);
            result.setStatus("ok");
        } catch (Exception e) {
            e.printStackTrace();
            String errorInfo = e.getClass().getName() + ":" + e.getMessage();
            result.setResult(errorInfo);
            result.setStatus("error");
            logger.error(errorInfo);
        }
        return ResponseEntity.ok(result);
    }


}
