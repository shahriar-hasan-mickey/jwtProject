package com.jwtProject.jwtProject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtProjectController {
    @RequestMapping({"/jwtProject"})
    public String firstPage(){
        return "Hello World from JWT PROJECT";
    }
}
