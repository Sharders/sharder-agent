package org.sharder.agent.controller;

import io.swagger.annotations.*;
import okhttp3.ResponseBody;
import org.sharder.agent.domain.TransactionResponse;
import org.sharder.agent.service.DataService;
import org.sharder.agent.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * DataController
 *
 * @author bubai
 * @date 2018/3/18
 */

@RestController
@RequestMapping("/v1/data")
@Api(value = "Data operations", tags = {"数据操作"})
public class DataController {
    private static final Logger logger = LoggerFactory.getLogger(DataController.class);
    @Autowired
    private DataService dataService;

    /**
     * Storing data to chain
     * @param file
     * @param passPhrase
     * @return ResponseEntity<JsonResult>
     * @throws IOException
     */
    @ApiOperation(value = "upload a file", notes = "upload a file to chain")
    @ApiImplicitParams({
        @ApiImplicitParam(name="file", value = "file", required = true, dataType = "MultipartFile", paramType = "body"),
        @ApiImplicitParam(name="passPhrase", value = "passPhrase", required = true, paramType = "body"),
    })
    @RequestMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, method = RequestMethod.POST)
    public ResponseEntity<JsonResult> store(@RequestParam(value = "file", required = true) MultipartFile file, @RequestParam(value = "passPhrase", required = true) String passPhrase) throws IOException {
        logger.debug(file.getOriginalFilename());
        TransactionResponse tr =  dataService.upload(passPhrase, file);
        JsonResult result = new JsonResult();
        result.setStatus("ok");
        result.setResult(tr);
        return ResponseEntity.ok(result);
    }

    /**
     * retrieve data from chain
     * @param txId transaction id
     * @return ResponseEntity<JsonResult>
     * @throws IOException
     */
    @ApiOperation(value = "retrieve a file", notes = "retrieve a file with transaction id")
    @ApiImplicitParams({
            @ApiImplicitParam(name="txId", value = "transaction id", required = true, paramType = "path"),
    })
    @RequestMapping(path = "/{txId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> retrieve(@PathVariable("txId") String txId) throws IOException {
        //TODO
        ResponseBody rb = dataService.download(txId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(rb.contentType().toString()))
                .contentLength(rb.contentLength())
                .body(rb.bytes());
    }
}
