package org.sharder.agent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * IndexController
 *
 * @author bubai
 * @date 2018/3/22
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "Sharder Agent!";
    }
}
