package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author dzh
 * @email 1264292841@qq.com
 * @date 2022-11-24 18:47:56
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
