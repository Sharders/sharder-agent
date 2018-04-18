package org.sharder.agent.controller;

import okhttp3.ResponseBody;
import org.sharder.agent.domain.Data;
import org.sharder.agent.domain.DataTransactionResponse;
import org.sharder.agent.exception.SharderAgentException;
import org.sharder.agent.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * DataController
 *
 * @author bubai
 * @date 2018/3/18
 */

@RestController
@RequestMapping("/v1/data")
public class DataController {
    private static final Logger logger = LoggerFactory.getLogger(DataController.class);
    @Autowired
    private DataService dataService;

    /**
     * Storing file data to chain
     * @param file
     * @param passPhrase
     * @return ResponseEntity<DataTransactionResponse>
     * @throws IOException
     */
    @RequestMapping(path = "/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, method = RequestMethod.POST)
    public ResponseEntity<DataTransactionResponse> storeFile(@RequestParam( value = "file", required = true) MultipartFile file,
                                            @RequestParam(value = "passPhrase", required = true) String passPhrase,
                                            @RequestParam(value = "clientAccount", required = true) String clientAccount) throws Exception {
        logger.debug(file.getOriginalFilename());
        DataTransactionResponse tr =  dataService.upload(passPhrase, file, clientAccount);
        return ResponseEntity.ok(tr);
    }

    /**
     * Storing text data to chain
     * @param data
     * @param passPhrase
     * @return ResponseEntity<DataTransactionResponse>
     * @throws IOException
     */
    @RequestMapping(path = "/text", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE}, method = RequestMethod.POST)
    public ResponseEntity<DataTransactionResponse> storeData(@RequestParam( value = "fileName", required = true) String fileName,
                                                             @RequestParam( value = "data", required = true) String data,
                                                             @RequestParam( value = "fileType", required = true) String fileType,
                                                             @RequestParam(value = "passPhrase", required = true) String passPhrase,
                                                             @RequestParam(value = "clientAccount", required = true) String clientAccount) throws Exception {
        DataTransactionResponse tr =  dataService.upload(passPhrase, data, fileName, fileType, clientAccount);
        return ResponseEntity.ok(tr);
    }

    /**
     * Retrieve file data from chain
     * @param txId transaction id
     * @return ResponseEntity<byte[]>
     * @throws IOException
     */
    @RequestMapping(path = "/file/{txId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> retrieveFile(@PathVariable("txId") String txId) throws IOException, SharderAgentException {
        ResponseBody rb = dataService.download(txId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(rb.contentType().toString()))
                .contentLength(rb.contentLength())
                .body(rb.bytes());
    }

    /**
     * Retrieve text data from chain
     * @param txId transaction id
     * @return ResponseEntity<Data.Attachment>
     * @throws IOException
     */
    @RequestMapping(path = "/text/{txId}", method = RequestMethod.GET)
    public ResponseEntity<Data> retrieveData(@PathVariable("txId") String txId) throws Exception {
        Data data = dataService.retrive(txId);
        return ResponseEntity.ok(data);
    }
}
