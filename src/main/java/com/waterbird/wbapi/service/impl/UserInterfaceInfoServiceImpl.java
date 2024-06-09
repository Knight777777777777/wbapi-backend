package com.waterbird.wbapi.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.waterbird.wbapi.common.ErrorCode;
import com.waterbird.wbapi.constant.UserConstant;
import com.waterbird.wbapi.exception.BusinessException;
import com.waterbird.wbapi.mapper.UserInterfaceInfoMapper;
import com.waterbird.wbapi.model.dto.userinterfaceinfo.UpdateUserInterfaceInfoDTO;
import com.waterbird.wbapi.model.vo.InterfaceInfoVO;
import com.waterbird.wbapi.model.vo.UserInterfaceInfoVO;
import com.waterbird.wbapi.service.InterfaceInfoService;
import com.waterbird.wbapi.service.UserInterfaceInfoService;

import com.waterbird.wbapi.service.UserService;
import com.waterbird.wbapicommon.entity.InterfaceInfo;
import com.waterbird.wbapicommon.entity.User;
import com.waterbird.wbapicommon.entity.UserInterfaceInfo;;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lcccccccc
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
 * @createDate 2023-12-16 18:42:23
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private UserService userService;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    /**
     * 校验用户调用接口信息
     * @param userInterfaceInfo
     * @param add
     */
    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 创建时，参数不能为空
        if (add) {
            if (userInterfaceInfo.getUserId() == null || userInterfaceInfo.getInterfaceInfoId() == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或用户不存在");
            }

            if (userInterfaceInfo.getTotalNum() < 0 || userInterfaceInfo.getLeftNum() < 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口分配次数或者剩余次数不能小于o");
            }

        }
    }

    /**
     * 接口计数
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    @Transactional
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        //  校验用户id，接口id是否合理
        if (interfaceInfoId < 0 || userId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户或接口不存在");
        }
        //查询调用接口详情，包括剩余次数和调用版本号
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        queryWrapper.eq("interfaceInfoId", interfaceInfoId);
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoMapper.selectOne(queryWrapper);
        Integer version = userInterfaceInfo.getVersion();

        // 校验剩余调用次数
        Integer leftNum = userInterfaceInfo.getLeftNum();
        if (leftNum <= 0) {
            log.error("接口剩余调用次数不足");
            return false;
        }

        // 使用 UpdateWrapper 对象来构建更新条件
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        // 在 updateWrapper 中设置了两个条件：interfaceInfoId 等于给定的 interfaceInfoId 和 userId 等于给定的 userId。
        updateWrapper.eq("interfaceInfoId", interfaceInfoId);
        updateWrapper.eq("userId", userId);
        updateWrapper.eq("version", version);
        updateWrapper.gt("leftNum", 0);
        // setSql 方法用于设置要更新的 SQL 语句。这里通过 SQL 表达式实现了两个字段的更新操作：
        updateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
        // 最后，调用update方法执行更新操作，并返回更新是否成功的结果
        return this.update(updateWrapper);

    }

    /**
     * 恢复用户接口调用次数，保证数据一致性和业务逻辑正确性
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    @Override
    public boolean recoverInvokeCount(long userId, long interfaceInfoId) {
        if (userId<0 || interfaceInfoId<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户或接口不存在");
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userId",userId);
        updateWrapper.eq("interfaceInfoId",interfaceInfoId);
        updateWrapper.gt("leftNum",0);
        updateWrapper.setSql("totalNum = totalNum -1,leftNum = leftNum+1,version = version+1");
        return this.update(updateWrapper);
    }

    /**
     * 获取用户剩余的接口调用次数
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    @Override
    public int getLeftInvokeCount(long userId, long interfaceInfoId) {
        //1.根据用户id和接口id获取用户接口关系详情对象
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        queryWrapper.eq("interfaceInfoId",interfaceInfoId);
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoMapper.selectOne(queryWrapper);
        //2.从用户接口关系详情对象中获取剩余调用次数
        return userInterfaceInfo.getLeftNum();
    }

    /**
     * 更新用户的接口调用信息
     * @param updateUserInterfaceInfoDTO
     * @return
     */
    @Override
    public boolean updateUserInterfaceInfo(UpdateUserInterfaceInfoDTO updateUserInterfaceInfoDTO) {
        Long userId = updateUserInterfaceInfoDTO.getUserId();
        Long interfaceId = updateUserInterfaceInfoDTO.getInterfaceId();
        Long lockNum = updateUserInterfaceInfoDTO.getLockNum();

        if(interfaceId == null || userId == null || lockNum == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        UserInterfaceInfo one = this.getOne(
                new QueryWrapper<UserInterfaceInfo>()
                        .eq("userId", userId)
                        .eq("interfaceInfoId", interfaceId)
        );

        if (one != null) {
            // 说明是增加数量
            return this.update(
                    new UpdateWrapper<UserInterfaceInfo>()
                            .eq("userId", userId)
                            .eq("interfaceInfoId", interfaceId)
                            .setSql("leftNum = leftNum + " + lockNum)
            );
        } else {
            // 说明是第一次购买
            UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
            userInterfaceInfo.setUserId(userId);
            userInterfaceInfo.setInterfaceInfoId(interfaceId);
            userInterfaceInfo.setLeftNum(Math.toIntExact(lockNum));
            return this.save(userInterfaceInfo);
        }

    }

    /**
     * 根据用户ID获取用户可以调用的接口信息列表
     * @param userId
     * @param request
     * @return
     */
    @Override
    public List<UserInterfaceInfoVO> getInterfaceInfoByUserId(Long userId, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        // 判断用户是否有权限
        if(!loginUser.getId().equals(userId) && !loginUser.getUserRole().equals(UserConstant.ADMIN_ROLE)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 获取用户可调用接口列表
        QueryWrapper<UserInterfaceInfo> userInterfaceInfoQueryWrapper= new QueryWrapper<>();
        userInterfaceInfoQueryWrapper.eq("userId",loginUser.getId());
        List<UserInterfaceInfo> userInterfaceInfoList = this.list(userInterfaceInfoQueryWrapper);

        Map<Long, List<UserInterfaceInfo>> interfaceIdUserInterfaceInfoMap = userInterfaceInfoList.stream().collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        Set<Long> interfaceIds = interfaceIdUserInterfaceInfoMap.keySet();
        QueryWrapper<InterfaceInfo> interfaceInfoQueryWrapper = new QueryWrapper<>();
        if(CollectionUtil.isEmpty(interfaceIds)){
            return new ArrayList<>();
        }
        interfaceInfoQueryWrapper.in("id",interfaceIds);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(interfaceInfoQueryWrapper);
        List<UserInterfaceInfoVO> userInterfaceInfoVOList = interfaceInfoList.stream().map(interfaceInfo -> {
            UserInterfaceInfoVO userInterfaceInfoVO = new UserInterfaceInfoVO();
            // 复制接口信息
            BeanUtils.copyProperties(interfaceInfo, userInterfaceInfoVO);
            userInterfaceInfoVO.setInterfaceStatus(Integer.valueOf(interfaceInfo.getStatus()));

            // 复制用户调用接口信息
            List<UserInterfaceInfo> userInterfaceInfos = interfaceIdUserInterfaceInfoMap.get(interfaceInfo.getId());
            UserInterfaceInfo userInterfaceInfo = userInterfaceInfos.get(0);
            BeanUtils.copyProperties(userInterfaceInfo, userInterfaceInfoVO);
            return userInterfaceInfoVO;
        }).collect(Collectors.toList());
        return userInterfaceInfoVOList;
    }

    /**
     * 分析调用次数最多的接口
     * @param limit 指定返回前多少个调用次数最多的接口。
     * @return 调用次数最多的前 limit 个接口的信息。
     */
    @Override
    public List<InterfaceInfoVO> interfaceInvokeTopAnalysis(int limit) {
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(limit);
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", interfaceInfoIdObjMap.keySet());
        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        List<InterfaceInfoVO> InterfaceInfoVOList = list.stream().map(interfaceInfo -> {
            InterfaceInfoVO InterfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, InterfaceInfoVO);
            int totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            InterfaceInfoVO.setTotalNum(totalNum);
            return InterfaceInfoVO;
        }).collect(Collectors.toList());
        return InterfaceInfoVOList;
    }

}




