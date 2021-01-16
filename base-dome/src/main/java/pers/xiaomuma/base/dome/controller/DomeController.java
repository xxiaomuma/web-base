package pers.xiaomuma.base.dome.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.xiaomuma.base.common.api.ResultDTO;

@RestController
@RequestMapping("test")
public class DomeController {

    @GetMapping(value = "a")
    public ResultDTO<Void> test() {
        //throw new BaseBusinessException("dddd");
        return ResultDTO.success();
    }
}
