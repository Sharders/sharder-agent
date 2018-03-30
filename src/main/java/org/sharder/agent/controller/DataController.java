package org.sharder.agent.controller;

import io.swagger.annotations.*;
import org.sharder.agent.domain.TaggedData;
import org.sharder.agent.service.DataService;
import org.sharder.agent.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * DataController
 *
 * @author bubai
 * @date 2018/3/18
 */

@RestController
@RequestMapping("/v1/data")
@Api(value = "Data operations")
public class DataController {
    private static final Logger logger = LoggerFactory.getLogger(DataController.class);
    @Autowired
    private DataService dataService;

    /**
     * Storing data to chain
     * @param
     * @return ResponseEntity<JsonResult>
     */
    @ApiOperation(value = "upload a file", notes = "")
    @ApiImplicitParams({
        @ApiImplicitParam(name="file", value = "file", required = true, dataType = "MultipartFile", paramType = "requestBody"),
    })
    @RequestMapping(headers=("content-type=multipart/*"),method = RequestMethod.POST)
    public ResponseEntity<JsonResult> store(@RequestParam("file") MultipartFile file) throws IOException {
        //TODO
        dataService.upload(file);
        JsonResult result = new JsonResult();
        return ResponseEntity.ok(result);
    }

    /**
     * retrieve data from chain
     * @param
     * @return ResponseEntity<JsonResult>
     */
    @ApiOperation(value = "download a file", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name="txId", value = "data", required = true, dataType = "String", paramType = "path"),
    })
    @RequestMapping(path = "/{txId}", method = RequestMethod.GET, produces = "image/png")
    public ResponseEntity<InputStreamResource> retrieve(@PathVariable("txId") String txId) throws IOException {
        //TODO
        logger.debug("txID:{}",txId);
        InputStream is = dataService.download(txId);
        InputStreamResource isr = new InputStreamResource(is);

//        String contentType = Files.probeContentType(isr.getFile().toPath());
        logger.info("store sth");
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/png"))
                .contentLength(36556).body(isr);
    }
}
