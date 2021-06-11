package com.louisblogs.louismall.search.service;

import com.louisblogs.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/10 15:24
 */

public interface ProductSaveService {

	boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;

}
