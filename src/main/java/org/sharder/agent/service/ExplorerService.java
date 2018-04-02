package org.sharder.agent.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.sharder.agent.domain.Block;
import org.sharder.agent.domain.ErrorDescription;
import org.sharder.agent.domain.Transaction;
import org.sharder.agent.rpc.RequestManager;
import org.sharder.agent.rpc.RequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ExplorerService
 *
 * @author bubai
 * @date 2018/4/2
 */

@Service
public class ExplorerService {
    private static final Logger logger = LoggerFactory.getLogger(ExplorerService.class);
    @Autowired
    private RequestManager requestManager;

    public ArrayList<Block> getBlocks(BigInteger firstIndex, BigInteger lastIndex) throws IOException {
        ArrayList<Block> blocks = null;
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType",RequestType.GET_BLOCKS.getType());
        params.put("firstIndex",firstIndex.toString());
        params.put("lastIndex",lastIndex.toString());
        Response response = requestManager.requestSyn(RequestManager.TYPE_GET, params);
        String validResponseStr = null;
        if(response.isSuccessful()){
            String responseStr = response.body().string();
            validResponseStr = responseStr.substring(responseStr.indexOf("["),responseStr.lastIndexOf("]")+1);
            logger.debug("response success:{}",validResponseStr);
        }
        ObjectMapper mapper = new ObjectMapper();
        blocks = mapper.readValue(validResponseStr,new TypeReference<ArrayList<Block>>(){});
        return blocks;
    }

    public ArrayList<Transaction> getAccountTxs(String account) throws Exception {
        ArrayList<Transaction> transactions = null;
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType",RequestType.GET_ACCOUNT_TRANSACTIONS.getType());
        params.put("account",account);
        Response response = requestManager.requestSyn(RequestManager.TYPE_GET, params);
        String validResponseStr = null;
        ObjectMapper mapper = new ObjectMapper();
        if(response.isSuccessful()){
            String responseStr = response.body().string();
            logger.debug("response success:{}",responseStr);
            ErrorDescription ed = mapper.readValue(responseStr,ErrorDescription.class);
            if (ed.getErrorDescription() != null) {
                throw new Exception(ed.getErrorDescription());
            }
            validResponseStr = responseStr.substring(responseStr.indexOf("["),responseStr.lastIndexOf("]")+1);
        }
        transactions = mapper.readValue(validResponseStr,new TypeReference<ArrayList<Transaction>>(){});
        return transactions;
    }
}
