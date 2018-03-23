package org.sharder.agent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * IndexController
 *
 * @author bubai
 * @date 2018/3/22
 */
@Controller
@ApiIgnore
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "forward:/doc.html";
    }
}
