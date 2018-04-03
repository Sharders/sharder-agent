package org.sharder.agent.controller;

import io.swagger.annotations.*;
import org.sharder.agent.domain.Account;
import org.sharder.agent.service.AccountService;
import org.sharder.agent.utils.JsonResult;
import org.sharder.agent.utils.PassPhraseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * AccountController
 *
 * @author bubai
 * @date 2018/3/18
 */

@RestController
@RequestMapping("/v1/account")
@Api(value = "Account Operations", tags={"账户操作接口"})
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;


    /**
     * Create an account without secret phrase
     * @return ResponseEntity<JsonResult>
     * auto generate secretPhrase for account instead of param (refer to nxt bridge)
     * TODO broadcast to the network after create an account immediately by send the new account a message
     */
    @ApiOperation(value = "Create a new account", notes = "Create a new account with your passPhrase")
    @ApiImplicitParam(name="passPhrase", value = "the passPhrase of invoker account, not the account's to be created", required = true, paramType = "body")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<JsonResult> createAccount(@RequestParam String passPhrase) throws IOException {
        String newPassPhrase = PassPhraseGenerator.makeRandomSecretPhrase();
//        String passPhrase = PassPhraseGenerator.generateFromMnemonicWords();
        JsonResult result = new JsonResult();
        Account account = accountService.getAccountId(newPassPhrase);
        accountService.senMessage(account,passPhrase);
        account.setPassPhrase(newPassPhrase);
        result.setResult(account);
        result.setStatus("ok");
        return ResponseEntity.ok(result);
    }

    /**
     * Create an account with random passphrase
     * @return ResponseEntity<JsonResult>
     *
     *
    @ApiOperation(value = "Create a account", notes = "Create a account ID with passPhrase")
    @ApiImplicitParams({
        @ApiImplicitParam(name="passPhrase", value = "passPhrase", required = true, dataType = "String", paramType = "requestBody"),
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<JsonResult> createAccount(@RequestParam String passPhrase) throws IOException {
        JsonResult result = new JsonResult();
        Account account = accountService.getAccountId(passPhrase);
        result.setResult(account);
        result.setStatus("ok");
        return ResponseEntity.ok(result);
    }
     */

    /**
     * Send SS to an account
     * @param secretPhrase
     * @return ResponseEntity<JsonResult>
     */
    @ApiIgnore
    @ApiOperation(value = "Send SS", notes = "Send SS to an account")
    @ApiImplicitParams({
        @ApiImplicitParam(name="recipient", value = "recipient address", required = true, paramType = "requestBody"),
        @ApiImplicitParam(name="recipientPublicKey", value = "the publickey of recipient", required = true, paramType = "requestBody"),
        @ApiImplicitParam(name="amount", value = "amountSS", required = true, dataType = "int", paramType = "requestBody"),
        @ApiImplicitParam(name="secretPhrase", value = "secretPhrase", required = true, paramType = "requestBody"),
    })
    @RequestMapping(value = "/sendSS", method = RequestMethod.POST)
    public ResponseEntity<JsonResult> sendMoney(@RequestParam String recipient,
                                                @RequestParam String recipientPublicKey,
                                                @RequestParam BigDecimal amount,
                                                @RequestParam String secretPhrase) {
        JsonResult result = new JsonResult();
//        try {
//            Account account = accountService.sendMoney(recipient, recipientPublicKey, amount, secretPhrase);
//            result.setResult(account);
//            result.setStatus("ok");
//        } catch (Exception e) {
//            e.printStackTrace();
//            String errorInfo = e.getClass().getName() + ":" + e.getMessage();
//            result.setResult(errorInfo);
//            result.setStatus("error");
//            logger.error(errorInfo);
//        }
        return ResponseEntity.ok(result);
    }

}
