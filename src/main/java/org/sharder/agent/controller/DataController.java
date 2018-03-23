package org.sharder.agent.controller;

import io.swagger.annotations.*;
import org.sharder.agent.domain.TaggedData;
import org.sharder.agent.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * DataController
 *
 * @author bubai
 * @date 2018/3/18
 */

@RestController
@RequestMapping(path = "/v1/data", method = RequestMethod.GET)
@Api(value = "Data operations")
public class DataController {
    private static final Logger logger = LoggerFactory.getLogger(DataController.class);

    /**
     * Storing data to chain
     * @param
     * @return ResponseEntity<JsonResult>
     */
    @ApiOperation(value = "", notes = "")
    @ApiImplicitParams({
        @ApiImplicitParam(name="data", value = "data", required = true, dataType = "TaggedData", paramType = "requestBody"),
    })
    @RequestMapping(path = "/",method = RequestMethod.POST)
    public ResponseEntity<JsonResult> store(@ModelAttribute TaggedData td ) {
        //TODO

        JsonResult result = new JsonResult();
        logger.info("store sth");
        return ResponseEntity.ok(result);
    }

}
