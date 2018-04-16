package org.sharder.agent.service;

import okhttp3.Response;
import org.sharder.agent.domain.Account;
import org.sharder.agent.domain.DataTransactionResponse;
import org.sharder.agent.rpc.RequestManager;
import org.sharder.agent.rpc.RequestType;
import org.sharder.agent.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * AccountService
 *
 * @author bubai
 * @date 2018/3/24
 */

@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private RequestManager requestManager;

    /**
     * Create an Account
     * @param secretPhrase String
     * @return Account
     * @throws Exception
     */
    public Account getAccountId(String secretPhrase) throws Exception {
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType",RequestType.GET_ACCOUNT_ID.getType());
        params.put("secretPhrase",secretPhrase);
        Response response = requestManager.requestSyn(RequestManager.TYPE_POST, params);
        return ResponseUtils.convert(response, Account.class);
    }

    /**
     * Send Money to a Account
     * @param recipient
     * @param recipientPublicKey
     * @param amount
     * @param secretPhrase
     * @return
     */
    public DataTransactionResponse sendMoney(String recipient, String recipientPublicKey, BigDecimal amount, String secretPhrase) {
        return null;
    }

    /**
     * Send Message to a Account
     * @param account Account
     * @param passPhrase String
     * @throws Exception
     */
    public void sendMessage(Account account, String passPhrase) throws Exception {
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType",RequestType.SEND_MSG.getType());
        params.put("secretPhrase",passPhrase);
        params.put("recipient",account.getAccountRS());
        params.put("recipientPublicKey",account.getPublicKey());
        params.put("message",account.getAccountRS() + "was created and broadcast to the network");
        params.put("deadline","60");
        params.put("feeNQT","0");
        Response response = requestManager.requestSyn(RequestManager.TYPE_POST, params);
        ResponseUtils.convert(response, Void.class);
    }

    /**
     * Get Account Info by accountID or accountRS
     * @param account
     * @return
     */
    public Account getAccountInfo(String account) throws Exception {
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType",RequestType.GET_ACCOUNT.getType());
        params.put("account",account);
        Response response = requestManager.requestSyn(RequestManager.TYPE_GET, params);
        return ResponseUtils.convert(response, Account.class);
    }
}
