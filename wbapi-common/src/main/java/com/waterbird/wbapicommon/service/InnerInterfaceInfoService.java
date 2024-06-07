package com.waterbird.wbapicommon.service;

import com.waterbird.wbapicommon.model.entity.InterfaceInfo;


/**
* @author lcccccccc
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-12-10 23:16:11
*/
public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询模拟接口是否存在
     */
    InterfaceInfo getInvokeInterfaceInfo(String path,String method);
}
