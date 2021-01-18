package pers.xiaomuma.base.dome.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.xiaomuma.base.common.api.ResultDTO;
import pers.xiaomuma.base.security.UserContext;

@RestController
@RequestMapping("test")
public class DomeController {

    @GetMapping(value = "a")
    public ResultDTO<Integer> test() {
        Integer accountId = UserContext.getUserId();
        //throw new BaseBusinessException("dddd");
        return ResultDTO.success(accountId);
    }
}
