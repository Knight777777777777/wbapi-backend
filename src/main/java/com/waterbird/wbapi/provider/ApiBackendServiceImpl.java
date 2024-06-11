package com.waterbird.wbapi.provider;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.waterbird.wbapi.mapper.InterfaceInfoMapper;
import com.waterbird.wbapi.mapper.UserMapper;
import com.waterbird.wbapi.model.dto.userinterfaceinfo.UpdateUserInterfaceInfoDTO;
import com.waterbird.wbapi.service.InterfaceChargingService;
import com.waterbird.wbapi.service.UserInterfaceInfoService;
import com.waterbird.wbapicommon.common.ErrorCode;
import com.waterbird.wbapicommon.model.entity.InterfaceCharging;
import com.waterbird.wbapicommon.model.entity.InterfaceInfo;
import com.waterbird.wbapicommon.model.entity.User;
import com.waterbird.wbapicommon.exception.BusinessException;
import com.waterbird.wbapicommon.service.ApiBackendService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;



import javax.annotation.Resource;

/**
 * 作为服务提供方，提供远程调用接口
 */
@DubboService
public class ApiBackendServiceImpl implements ApiBackendService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;


    @Resource
    private InterfaceChargingService interfaceChargingService;

    /**
     * 获取调用的用户
     * @param accessKey
     * @return
     */
    @Override
    public User getInvokeUser(String accessKey) {

        if (StringUtils.isBlank(accessKey)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey",accessKey);
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 获取接口信息
     *
     * @param url
     * @param method
     * @return
     */
    @Override
    public InterfaceInfo getInterFaceInfo(String url, String method) {
        if (StringUtils.isBlank(url) || StringUtils.isBlank(method)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url",url);
        queryWrapper.eq("method",method);
        return interfaceInfoMapper.selectOne(queryWrapper);
    }

    /**
     * 调用接口计数
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    @Override
    public boolean invokeCount(long userId, long interfaceInfoId) {
        return userInterfaceInfoService.invokeCount(userId,interfaceInfoId);
    }

    /**
     * 获取用户对该接口剩余调用次数
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    @Override
    public int getLeftInvokeCount(long userId, long interfaceInfoId) {
        return userInterfaceInfoService.getLeftInvokeCount(userId,interfaceInfoId);
    }

    /**
     * 根据id获取接口
     *
     * @param interfaceId
     * @return
     */
    @Override
    public InterfaceInfo getInterfaceById(long interfaceId) {
        return interfaceInfoMapper.selectById(interfaceId);
    }

    /**
     * 根据id获取接口剩余调用次数（库存）
     * @param interfaceId
     * @return
     */
    @Override
    public int getInterfaceStockById(long interfaceId) {
        QueryWrapper<InterfaceCharging> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("interfaceId",interfaceId);
        InterfaceCharging interfaceCharging = interfaceChargingService.getOne(queryWrapper);
        if (interfaceCharging == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口不存在");
        }
        return Integer.parseInt(interfaceCharging.getAvailableCalls());
    }

    /**
     * 更新接口剩余调用次数（库存）
     * @param interfaceId
     * @param num
     * @return
     */
    @Override
    public boolean updateInterfaceStock(long interfaceId,Integer num) {
        UpdateWrapper<InterfaceCharging> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("availableCalls = availableCalls - "+num)
                .eq("interfaceId",interfaceId).gt("availableCalls",num);

        return interfaceChargingService.update(updateWrapper);
    }

    /**
     * 恢复接口库存
     * @param interfaceId
     * @param num
     * @return
     */
    @Override
    public boolean recoverInterfaceStock(long interfaceId, Integer num) {
        UpdateWrapper<InterfaceCharging> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("availableCalls = availableCalls + "+num)
                .eq("interfaceId",interfaceId);
        return interfaceChargingService.update(updateWrapper);
    }

    /**
     * 更新用户调用接口次数
     * @param userId
     * @param interfaceId
     * @param num
     * @return
     */
    @Override
    public boolean updateUserInterfaceInvokeCount(long userId, long interfaceId, int num) {
        UpdateUserInterfaceInfoDTO userInterfaceInfoDTO = new UpdateUserInterfaceInfoDTO();
        userInterfaceInfoDTO.setUserId(userId);
        userInterfaceInfoDTO.setInterfaceId(interfaceId);
        userInterfaceInfoDTO.setLockNum((long)num);
        return userInterfaceInfoService.updateUserInterfaceInfo(userInterfaceInfoDTO);
    }


}