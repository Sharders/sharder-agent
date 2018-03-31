package org.sharder.agent.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.sharder.agent.domain.Account;
import org.sharder.agent.domain.TransactionResponse;
import org.sharder.agent.rpc.RequestManager;
import org.sharder.agent.rpc.RequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.UUID;

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

    /**
     * download file
     * @param txID transaction id
     * @return ResponseBody
     * @throws IOException
     */
    public ResponseBody download(String txID) throws IOException {
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType",RequestType.DOWNLOAD_DATA.getType());
        params.put("transaction",txID.trim());
        Response response = requestManager.requestSyn(RequestManager.TYPE_GET, params);
        ResponseBody rb = response.body();
        if(response.isSuccessful()){
            logger.debug("response success:{}",rb.contentType());
        }
        return rb;
    }

    /**
     * TODO add params to adjust business logic
     * @param secretPhrase secretPhrase
     * @param mfile MultipartFile
     * @return TransactionResponse
     * @throws IOException
     */
    public TransactionResponse upload(String secretPhrase, MultipartFile mfile) throws IOException {
        TransactionResponse tr = new TransactionResponse();
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType",RequestType.UPLOAD_DATA.getType());
        params.put("type",mfile.getContentType());
        params.put("filename",mfile.getOriginalFilename());
        params.put("feeNQT","0");
        params.put("deadline","60");
        params.put("secretPhrase",secretPhrase);
        File file = multipartToFile(mfile);
        Response response = requestManager.requestPostMultipartBySyn(params, file);
        String responseStr = null;
        if(response.isSuccessful()){
            responseStr = response.body().string();
            logger.debug("response success:{}",responseStr);
        }
        ObjectMapper mapper = new ObjectMapper();
        tr = mapper.readValue(responseStr,TransactionResponse.class);
        return tr;
    }

    /**
     * MultipartFile convert to File
     *
     * @param multfile origin file
     * @return File to file
     * @throws IOException
     */
    private File multipartToFile(MultipartFile multfile) throws IOException {
        CommonsMultipartFile cf = (CommonsMultipartFile)multfile;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File file = fi.getStoreLocation();
        if(file.length() < 2048){
            File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +
                    file.getName());
            multfile.transferTo(tmpFile);
            return tmpFile;
        }
        return file;
    }
}
