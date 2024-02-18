package com.waterbird.wbapi.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.waterbird.wbapicommon.model.entity.InterfaceInfo;

/**
* @author lcccccccc
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-12-10 23:16:11
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    /**
     * 校验
     *
     * @param interfaceInfo
     * @param add
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
