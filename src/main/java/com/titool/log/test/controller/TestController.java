package com.titool.log.test.controller;

import com.titool.log.test.request.BaseRequest;
import com.titool.log.test.response.BaseResponse;
import com.titool.log.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Descrpition
 * @Date 2024/8/11
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/test")
    public BaseResponse getByName(@RequestParam(name = "name") String name) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setName(name);
        return testService.getByName(baseRequest);
    }
}
