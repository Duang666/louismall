package com.louisblogs.louismall.search.service;

import com.louisblogs.louismall.search.vo.SearchParam;
import com.louisblogs.louismall.search.vo.SearchResult;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/17 14:22
 */

public interface MallSearchService {

	/**
	 * @param param 检索的所有参数
	 * @return  返回检索的结果,里面包含页面需要的所有信息
	 */
	SearchResult search(SearchParam param);

}
