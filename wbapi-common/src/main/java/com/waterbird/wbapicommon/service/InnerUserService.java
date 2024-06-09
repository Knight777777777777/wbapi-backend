package com.waterbird.wbapicommon.service;
import com.waterbird.wbapicommon.entity.User;


/**
* @author lcccccccc
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-12-10 23:16:18
*/
public interface InnerUserService {
    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    User getUserById(Long userId);
}
