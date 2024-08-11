package com.titool.log.test.service;

import com.titool.log.LogAnnotation;
import com.titool.log.test.request.BaseRequest;
import com.titool.log.test.response.BaseResponse;
import org.springframework.stereotype.Service;

/**
 * @Descrpition
 * @Date 2024/8/11
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    @LogAnnotation(desc = "通过名称获取用户信息", keyArgIndex = 0, keyArgsProps = {"name"})
    public BaseResponse getByName(BaseRequest request) {
        BaseResponse baseResponse = new BaseResponse();
        if (request.getName().equals("tom")) {
            request.setAge(10);
            request.setId("1001");
            baseResponse.setResult(Boolean.TRUE);
            baseResponse.setMessage(request.toString());
        } else {
            baseResponse.setResult(Boolean.FALSE);
            baseResponse.setMessage("Wrong Name: "+request.getName());
        }
        return baseResponse;
    }
}
