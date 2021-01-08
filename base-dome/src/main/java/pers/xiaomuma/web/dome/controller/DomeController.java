package pers.xiaomuma.web.dome.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class DomeController {

    @GetMapping(value = "a")
    public String test() {
        return "hello word";
    }
}
