package com.titool.log.test.request;

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
public class BaseRequest {
    private String id;
    private String name;
    private Integer age;
}
