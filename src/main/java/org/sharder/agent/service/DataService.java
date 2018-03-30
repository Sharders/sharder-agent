package org.sharder.agent.service;

import okhttp3.Response;
import okhttp3.ResponseBody;
import org.sharder.agent.domain.TransactionResponse;
import org.sharder.agent.rpc.RequestManager;
import org.sharder.agent.rpc.RequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * DataService
 *
 * @author bubai
 * @date 2018/3/24
 */

@Service
public class DataService {
    private static final Logger logger = LoggerFactory.getLogger(DataService.class);

    @Autowired
    private RequestManager requestManager;

    public InputStream download(String txID) throws IOException {
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType",RequestType.DOWNLOAD_DATA.getType());
        Response response = requestManager.requestSyn(RequestManager.TYPE_GET, params);
        ResponseBody rb = response.body();
        if(response.isSuccessful()){
            logger.debug("response success:{}",rb.contentType());
        }
        return rb.byteStream();
    }

    public TransactionResponse upload(MultipartFile mfile) throws IOException {
        TransactionResponse tr = new TransactionResponse();
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType",RequestType.UPLOAD_DATA.getType());
        params.put("file",mfile.getContentType());
        //TODO put another params
        File file = null;
        mfile.transferTo(file);
        Response response = requestManager.requestPostMultipartBySyn(params, file);
        ResponseBody rb = response.body();
        if(response.isSuccessful()){
            logger.debug("response success:{}",rb.contentType());
        }
        //TODO convert response json to transcationResponse Object
        return tr;
    }
}
