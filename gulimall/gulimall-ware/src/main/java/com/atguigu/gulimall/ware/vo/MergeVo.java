package com.atguigu.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

@Data
public class MergeVo {
    /**
     * items : [1, 2]
     * purchaseId: 1
     */
    private Long purchaseId;
    private List<Long> items;
}
