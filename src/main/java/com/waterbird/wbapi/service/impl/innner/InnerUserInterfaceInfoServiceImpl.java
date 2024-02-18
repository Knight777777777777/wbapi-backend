package com.waterbird.wbapi.service.impl.innner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.waterbird.wbapi.common.ErrorCode;
import com.waterbird.wbapi.exception.BusinessException;
import com.waterbird.wbapi.mapper.UserInterfaceInfoMapper;
import com.waterbird.wbapi.service.UserInterfaceInfoService;
import com.waterbird.wbapicommon.model.entity.UserInterfaceInfo;
import com.waterbird.wbapicommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }
    /**
     * 实现接口中的 getUserInterfaceInfo方法，根据URL和method查询接口信息
     * @param interfaceInfoId
     * @param userId
     * @return 内部接口信息，如果匹配不到就返回 null
     * @throws BusinessException 参数错误时抛出业务异常
     */
    @Override
    public UserInterfaceInfo getUserInterfaceInfo(long interfaceInfoId, long userId) {
        // 参数校验
        if (interfaceInfoId == 0 || userId == 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //创建查询条件包装器
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("interfaceInfoId",interfaceInfoId);
        queryWrapper.eq("userId",userId);
        queryWrapper.gt("leftNum",0);
        return  userInterfaceInfoMapper.selectOne(queryWrapper);
    }
}
