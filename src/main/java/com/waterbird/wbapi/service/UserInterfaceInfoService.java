package com.waterbird.wbapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.waterbird.wbapi.model.entity.InterfaceInfo;
import com.waterbird.wbapi.model.entity.UserInterfaceInfo;


/**
* @author lcccccccc
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2023-12-16 18:42:23
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    /**
     * 校验
     *
     * @param userinterfaceInfo
     * @param add
     */
    void validUserInterfaceInfo(UserInterfaceInfo userinterfaceInfo, boolean add);

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
