package com.atguigu.gulimall.search.service;

import com.atguigu.gulimall.common.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

public interface ProductSaveService {


    boolean productStatusUp(List<SkuEsModel> skuEsModelList) throws IOException;
}
