package com.waterbird.wbapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.waterbird.wbapi.model.dto.userinterfaceinfo.UpdateUserInterfaceInfoDTO;
import com.waterbird.wbapi.model.vo.InterfaceInfoVO;
import com.waterbird.wbapi.model.vo.UserInterfaceInfoVO;
import com.waterbird.wbapicommon.model.entity.UserInterfaceInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


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
    /**
     * 回滚接口调用次数
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    boolean recoverInvokeCount(long userId, long interfaceInfoId);

    /**
     * 获取接口的剩余调用次数
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    int getLeftInvokeCount(long userId, long interfaceInfoId);


    /**
     *更新用户接口信息
     * @param updateUserInterfaceInfoDTO
     * @return
     */
    boolean updateUserInterfaceInfo(UpdateUserInterfaceInfoDTO updateUserInterfaceInfoDTO);

    /**
     * 获取用户所拥有的接口列表
     * @param userId
     * @param request
     * @return
     */
    List<UserInterfaceInfoVO> getInterfaceInfoByUserId(Long userId, HttpServletRequest request);

    /**
     * 获取调用次数前limit的接口信息
     * @param limit
     * @return
     */
    List<InterfaceInfoVO> interfaceInvokeTopAnalysis(int limit);
}
