package com.waterbird.wbapicommon.service;
import com.waterbird.wbapicommon.model.entity.User;


/**
* @author lcccccccc
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-12-10 23:16:18
*/
public interface InnerUserService {
    /**
     * 数据库查询是否已经分配给用户密钥
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);
}
