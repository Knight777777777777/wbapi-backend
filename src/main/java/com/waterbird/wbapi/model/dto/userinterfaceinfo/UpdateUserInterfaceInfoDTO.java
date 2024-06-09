package com.waterbird.wbapi.model.dto.userinterfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@Data
public class UpdateUserInterfaceInfoDTO implements Serializable {

    private static final long serialVersionUID = 1472097902521779075L;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 接口id
     */
    private Long interfaceId;
    /**
     * 乐观锁数量
     */
    private Long lockNum;
}