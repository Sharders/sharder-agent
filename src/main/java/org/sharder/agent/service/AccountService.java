package org.sharder.agent.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.sharder.agent.domain.Account;
import org.sharder.agent.rpc.RequestManager;
import org.sharder.agent.rpc.RequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public Account getAccountId(String secretPhrase) throws IOException {
        Account account;
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType",RequestType.GET_ACCOUNT_ID.getType());
        params.put("secretPhrase",secretPhrase);
        Response response = requestManager.requestSyn("nxt",RequestManager.TYPE_POST, params);
        String responseStr = null;
        if(response.isSuccessful()){
            responseStr = response.body().string();
            logger.debug("response success:{}",responseStr);
        }
        ObjectMapper mapper = new ObjectMapper();
        account = mapper.readValue(responseStr,Account.class);
        return account;
    }

}
