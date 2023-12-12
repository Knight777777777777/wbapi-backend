package com.waterbird.wbapi.common;

import java.io.Serializable;
import lombok.Data;

/**
 * 批量删除请求
 *

 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}