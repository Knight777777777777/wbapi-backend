package com.waterbird.wbapi.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 重新生成的用户ak，sk
 */
@Data
public class UserDevKeyVO implements Serializable {
    private static final long serialVersionUID = 6703326011663561616L;

    private String accessKey;
    private String secretKey;

}