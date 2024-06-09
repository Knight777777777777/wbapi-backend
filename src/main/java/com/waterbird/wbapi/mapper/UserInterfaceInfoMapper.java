package com.waterbird.wbapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waterbird.wbapi.model.vo.UserInterfaceInfoAnalysisVO;
import com.waterbird.wbapicommon.entity.UserInterfaceInfo;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * @author WaterBird
 * @description 针对表【user_interface_info】的数据库操作Mapper
 * @createDate 2024-06-09 23:46:16
 * @Entity generator.domain.UserInterfaceInfo
 */
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfoAnalysisVO> listTopInterfaceInfo(@Param("size") int size);

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);

}



