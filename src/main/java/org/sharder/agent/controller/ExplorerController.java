package org.sharder.agent.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.sharder.agent.domain.Account;
import org.sharder.agent.domain.Block;
import org.sharder.agent.domain.Transaction;
import org.sharder.agent.service.AccountService;
import org.sharder.agent.service.ExplorerService;
import org.sharder.agent.utils.JsonResult;
import org.sharder.agent.utils.PassPhraseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.math.BigDecimal;
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
@Api(value = "Block Info Request", tags={"区块浏览器数据获取接口"})
public class ExplorerController {
    private static final Logger logger = LoggerFactory.getLogger(ExplorerController.class);
    @Autowired
    private ExplorerService explorerService;


    @ApiOperation(value = "getLastedBlock", notes = "获取区块信息（喊交易详情）")
    @ApiImplicitParams({
        @ApiImplicitParam(name="firstIndex", value = "按区块高度倒序开始索引为止，非必须，不填则为最新高度", required = false, dataType = "integer", paramType = "path"),
        @ApiImplicitParam(name="lastIndex", value = "按区块高度倒序 截止索引，如2（假设firstIndex为0）则表示，获取最新3条区块信息，以此类推", dataType = "integer", required = true, paramType = "path")
    })
    @RequestMapping(value = "/blocks/{firstIndex}/{lastIndex}", method = RequestMethod.GET)
    public ResponseEntity<JsonResult> getBlocks(@PathVariable(required = false) BigInteger firstIndex, @PathVariable(required = true) BigInteger lastIndex) throws IOException {
        JsonResult result = new JsonResult();
        ArrayList<Block> blocks = explorerService.getBlocks(firstIndex, lastIndex);
        result.setResult(blocks);
        result.setStatus("ok");
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "getAccountTxs", notes = "获取指定用户的交易数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name="account", value = "账户ID", required = true, dataType = "string", paramType = "path"),
    })
    @RequestMapping(value = "/blocks/{account}/txs", method = RequestMethod.GET)
    public ResponseEntity<JsonResult> getAccountTxs(@PathVariable(required = true) String account) throws Exception {
        JsonResult result = new JsonResult();
        ArrayList<Transaction> transactions = explorerService.getAccountTxs(account);
        result.setResult(transactions);
        result.setStatus("ok");
        return ResponseEntity.ok(result);
    }
}
