package com.iooiee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VipController {

    @GetMapping("/level1")
    public String getLevel1Resource(){
        return "获得level1资源。。。。";
    }
    @GetMapping("/level2")
    public String getLevel2Resource(){
        return "获得level2资源。。。。";
    }

    @GetMapping("/level3")
    public String getLevel3Resource(){
        return "获得level3资源。。。。";
    }
}
