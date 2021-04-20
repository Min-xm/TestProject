package com.xm.test.controller;

import com.xm.test.server.Producer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    private Producer producer;

    @GetMapping("/test")
    public String Test(){
        return "123";
    }
}
