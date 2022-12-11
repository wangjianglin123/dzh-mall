package com.atguigu.gulimall.ware.feign;

import com.atguigu.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("gulimall-product")
public interface ProductFeignService {
    /**
     * 1.要让所有请求过网关，给网关所在的机器发请求
     * 2./api/product/skuinfo/info/{skuId}
     * <p>
     * 3.不过网关
     * /product/skuinfo/info/{skuId}
     *
     * @param skuId
     * @return
     */

    @RequestMapping("/product/skuinfo/info/{skuId}")
    public R info(@PathVariable("skuId") Long skuId);

}
