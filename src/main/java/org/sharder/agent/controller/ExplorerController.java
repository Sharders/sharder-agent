package org.sharder.agent.controller;

import org.sharder.agent.domain.Block;
import org.sharder.agent.domain.BlockHeight;
import org.sharder.agent.domain.Transaction;
import org.sharder.agent.service.ExplorerService;
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
    public ResponseEntity<ArrayList<Block>> getBlocks(@PathVariable(required = false) BigInteger firstIndex,
                                                @PathVariable(required = true) BigInteger lastIndex) throws IOException {
        ArrayList<Block> blocks = explorerService.getBlocks(firstIndex, lastIndex);
        return ResponseEntity.ok(blocks);
    }

    /**
     * Get block info with transactions by height
     * @param height
     * @return ResponseEntity<Block>
     * @throws Exception
     */
    @RequestMapping(value = "/blocks/{height}", method = RequestMethod.GET)
    public ResponseEntity<Block> getBlock(@PathVariable(required = true) BigInteger height) throws Exception {
        Block block = explorerService.getBlock(height);
        return ResponseEntity.ok(block);
    }

    /**
     * Get transactions of indicate account
     * @param account
     * @param firstIndex
     * @param lastIndex
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/blocks/txs/{account}/{firstIndex}/{lastIndex}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Transaction>> getAccountTxs(@PathVariable(required = true) String account,
                                                    @PathVariable(required = false) BigInteger firstIndex,
                                                    @PathVariable(required = false) BigInteger lastIndex) throws Exception {
        ArrayList<Transaction> transactions = explorerService.getAccountTxs(account, firstIndex, lastIndex);
        return ResponseEntity.ok(transactions);
    }

    /**
     * Get lasted block height
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/blocks/height", method = RequestMethod.GET)
    public ResponseEntity<BlockHeight> getLastedBlockHeight() throws Exception {
        BlockHeight blockHeight = explorerService.getLastedBlockHeight();
        return ResponseEntity.ok(blockHeight);
    }

}
