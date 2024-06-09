package com.waterbird.wbapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.waterbird.wbapi.common.ErrorCode;
import com.waterbird.wbapi.exception.BusinessException;
import com.waterbird.wbapi.exception.ThrowUtils;
import com.waterbird.wbapi.mapper.InterfaceInfoMapper;
import com.waterbird.wbapicommon.entity.InterfaceInfo;
import com.waterbird.wbapi.service.InterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author lcccccccc
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2023-12-10 23:16:11
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {
    /**
     * 接口信息的校验
     * @param interfaceInfo
     * @param add
     */
    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
     String name = interfaceInfo.getName();
     String description = interfaceInfo.getDescription();
     String url = interfaceInfo.getUrl();
     String requestHeader = interfaceInfo.getRequestHeader();
     String responseHeader = interfaceInfo.getResponseHeader();
     String method = interfaceInfo.getMethod();
        // 创建时，参数不能为空
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name,description,url,requestHeader,responseHeader,method), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }
}




