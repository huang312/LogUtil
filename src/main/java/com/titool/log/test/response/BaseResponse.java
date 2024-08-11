package com.titool.log.test.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Descrpition
 * @Date 2024/8/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    private Boolean result;
    private String message;
}
