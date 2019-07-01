package com.liutaiyue.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/demo")
@RestController
@ResponseBody
public class demoController {
    @GetMapping("/user/{index}")
    @PreAuthorize("hasRole('USER')")
    public String getIndex(@PathVariable("index") String index){
        return "my name is "+index;
    }
}
