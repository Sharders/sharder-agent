package org.sharder.agent.service;

import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.sharder.agent.domain.Data;
import org.sharder.agent.domain.DataTransactionResponse;
import org.sharder.agent.domain.ErrorDescription;
import org.sharder.agent.exception.SharderAgentException;
import org.sharder.agent.rpc.RequestManager;
import org.sharder.agent.rpc.RequestType;
import org.sharder.agent.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
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

    /**
     * Download file
     * @param txID transaction id
     * @return ResponseBody
     * @throws IOException
     */
    public ResponseBody download(String txID) throws IOException, SharderAgentException {
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType",RequestType.DOWNLOAD_DATA.getType());
        params.put("transaction",txID.trim());
        Response response = requestManager.requestSyn(RequestManager.TYPE_GET, params);
        ResponseBody rb = response.body();
        if(response.isSuccessful()){
            logger.debug("response success:{}",rb.contentType());
            if(rb.contentType() == null){
              throw new SharderAgentException(new ErrorDescription("This Transaction contains no file,try text data",110));
            }
        }
        return rb;
    }

    /**
     * Retrive text data
     * @param txID
     * @return Data
     * @throws Exception
     */
    public Data retrive(String txID) throws Exception {
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType",RequestType.GET_DATA.getType());
        params.put("transaction",txID.trim());
        Response response = requestManager.requestSyn(RequestManager.TYPE_GET, params);
        return ResponseUtils.convert(response, Data.class);
    }


    /**
     * Upload file
     * @param secretPhrase String
     * @param mfile MultipartFile
     * @param clientAccount String
     * @return DataTransactionResponse
     * @throws IOException
     */
    public DataTransactionResponse upload(String secretPhrase, MultipartFile mfile, String clientAccount) throws Exception {
        DataTransactionResponse tr = new DataTransactionResponse();
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType",RequestType.UPLOAD_DATA.getType());
        params.put("type",mfile.getContentType());
        params.put("filename",mfile.getOriginalFilename());
        params.put("feeNQT","0");
        params.put("deadline","60");
        params.put("channel",clientAccount);
        params.put("secretPhrase",secretPhrase);
        File file = multipartToFile(mfile);
        Response response = requestManager.requestPostMultipartBySyn(params, file);
        return ResponseUtils.convert(response,DataTransactionResponse.class);
    }

    /**
     * Upload text data
     * @param secretPhrase String
     * @param data String
     * @param fileName String
     * @param fileType String
     * @param clientAccount String
     * @return DataTransactionResponse
     * @throws IOException
     */
    public DataTransactionResponse upload(String secretPhrase, String data,
                                          String fileName, String fileType, String clientAccount) throws Exception {
        DataTransactionResponse tr = new DataTransactionResponse();
        HashMap<String,String> params = new HashMap<>();
        params.put("requestType",RequestType.UPLOAD_DATA.getType());
        params.put("type",fileType);
        params.put("data",data);
        params.put("name",fileName);
        params.put("feeNQT","0");
        params.put("deadline","60");
        params.put("channel",clientAccount);
        params.put("secretPhrase",secretPhrase);
        Response response = requestManager.requestSyn(RequestManager.TYPE_POST , params);
        return ResponseUtils.convert(response,DataTransactionResponse.class);
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
