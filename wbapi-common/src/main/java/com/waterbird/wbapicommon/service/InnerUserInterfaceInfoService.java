package com.waterbird.wbapicommon.service;
import com.waterbird.wbapicommon.model.entity.UserInterfaceInfo;

/**
 * @author lcccccccc
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
 * @createDate 2023-12-16 18:42:23
 */
public interface InnerUserInterfaceInfoService{
    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
    /**
     * 从数据库中查询用户调用接口记录
     */
    UserInterfaceInfo getUserInterfaceInfo(long interfaceInfoId,long userId);
}
