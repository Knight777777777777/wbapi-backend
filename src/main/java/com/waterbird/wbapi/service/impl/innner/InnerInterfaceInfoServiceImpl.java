package com.waterbird.wbapi.service.impl.innner;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.waterbird.wbapi.common.ErrorCode;
import com.waterbird.wbapi.exception.BusinessException;
import com.waterbird.wbapi.mapper.InterfaceInfoMapper;
import com.waterbird.wbapicommon.model.entity.InterfaceInfo;
import com.waterbird.wbapicommon.service.InnerInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;


import javax.annotation.Resource;
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    /**
     * 实现接口中的 getInvokeInterfaceInfo方法，根据URL和method查询接口信息
     * @param url
     * @param method
     * @return 内部接口信息，如果匹配不到就返回 null
     * @throws BusinessException 参数错误时抛出业务异常
     */
    @Override
    public InterfaceInfo getInvokeInterfaceInfo(String url, String method) {
        // 参数校验
        if (StrUtil.hasBlank(url, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<InterfaceInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(InterfaceInfo::getUrl, url).eq(InterfaceInfo::getMethod, method);
        return interfaceInfoMapper.selectOne(lambdaQueryWrapper);
    }
}
