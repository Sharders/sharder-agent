package org.sharder.agent.controller;

import org.sharder.agent.domain.Account;
import org.sharder.agent.service.AccountService;
import org.sharder.agent.utils.PassPhraseGenerator;
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
@RequestMapping("/v1/account")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;

    /**
     * Create an account without secret phrase
     * @return ResponseEntity<Account>
     * auto generate secretPhrase for account instead of param (refer to nxt bridge)
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestParam String passPhrase) throws Exception {
        String newPassPhrase = PassPhraseGenerator.makeRandomSecretPhrase();
//        String passPhrase = PassPhraseGenerator.generateFromMnemonicWords();
        Account account = accountService.getAccountId(newPassPhrase);
        accountService.sendMessage(account,passPhrase);
        account.setPassPhrase(newPassPhrase);
        return ResponseEntity.ok(account);
    }

}
