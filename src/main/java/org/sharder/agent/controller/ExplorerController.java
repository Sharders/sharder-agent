package org.sharder.agent.controller;

import org.sharder.agent.domain.Block;
import org.sharder.agent.domain.BlockHeight;
import org.sharder.agent.domain.Transaction;
import org.sharder.agent.service.ExplorerService;
import org.sharder.agent.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * ExplorerController
 *
 * @author bubai
 * @date 2018/4/2
 */

@RestController
@RequestMapping("/v1/explorer")
public class ExplorerController {
    private static final Logger logger = LoggerFactory.getLogger(ExplorerController.class);
    @Autowired
    private ExplorerService explorerService;


    /**
     * Get indicate blocks with index
     * @param firstIndex
     * @param lastIndex
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/blocks/{firstIndex}/{lastIndex}", method = RequestMethod.GET)
    public ResponseEntity<JsonResult> getBlocks(@PathVariable(required = false) BigInteger firstIndex,
                                                @PathVariable(required = true) BigInteger lastIndex) throws IOException {
        JsonResult result = new JsonResult();
        ArrayList<Block> blocks = explorerService.getBlocks(firstIndex, lastIndex);
        result.setResult(blocks);
        result.setStatus("ok");
        return ResponseEntity.ok(result);
    }

    /**
     * Get transactions of indicate account
     * @param account
     * @param firstIndex
     * @param lastIndex
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/blocks/{account}/txs", method = RequestMethod.GET)
    public ResponseEntity<JsonResult> getAccountTxs(@PathVariable(required = true) String account,
                                                    @PathVariable(required = false) BigInteger firstIndex,
                                                    @PathVariable(required = false) BigInteger lastIndex) throws Exception {
        JsonResult result = new JsonResult();
        ArrayList<Transaction> transactions = explorerService.getAccountTxs(account, firstIndex, lastIndex);
        result.setResult(transactions);
        result.setStatus("ok");
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/blocks/height", method = RequestMethod.GET)
    public ResponseEntity<JsonResult> getLastedBlockHeight() throws Exception {
        JsonResult result = new JsonResult();
        BlockHeight blockHeight = explorerService.getLastedBlockHeight();
        result.setResult(blockHeight);
        result.setStatus("ok");
        return ResponseEntity.ok(result);
    }

}
