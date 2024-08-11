package com.titool.log.test.service;

import com.titool.log.test.request.BaseRequest;
import com.titool.log.test.response.BaseResponse;

/**
 * @Descrpition
 * @Date 2024/8/11
 */
public interface TestService {
    BaseResponse getByName(BaseRequest request);
}
