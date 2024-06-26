package com.waterbird.wbapi.model.vo;

import com.waterbird.wbapicommon.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 接口信息封装视图
 *
 */
@Data
public class InterfaceInfoVO extends InterfaceInfo {

    /**
     * 统计每个接口被用户调用的总数
     */
    private Integer totalNum;


    /**
     * 计费规则（元/条）
     */
    private Double charging;

    /**
     * 计费Id
     */
    private Long chargingId;

    /**
     * 接口剩余可调用次数
     */
    private String availableCalls;
}