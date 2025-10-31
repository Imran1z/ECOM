package com.ecom.controllers;

import com.ecom.HelloResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class first {

    @GetMapping("/")
    public HelloResponse hello(){
        return new HelloResponse("Hello Imran");
    }

    @GetMapping("/{name}")
    public HelloResponse helloName(@PathVariable String name){
        return new HelloResponse("Hello "+name);
    }


    @PostMapping("/")
    public String helloPOst(@RequestBody String name){
        return name;
    }
}
