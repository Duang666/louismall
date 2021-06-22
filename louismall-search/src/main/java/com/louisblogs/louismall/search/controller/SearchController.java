package com.louisblogs.louismall.search.controller;

import com.louisblogs.louismall.search.service.MallSearchService;
import com.louisblogs.louismall.search.vo.SearchParam;
import com.louisblogs.louismall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/16 22:05
 */

@Controller
public class SearchController {

	@Autowired
	MallSearchService mallSearchService;

	/**
	 * 自动将页面提交过来的所有请求查询参数封装成指定的对象
	 * @param param
	 * @return
	 */
	@GetMapping("/list.html")
	public String listPage(SearchParam param, Model model, HttpServletRequest request) {

		param.set_queryString(request.getQueryString());
		//1、根据传递来的页面的查询参数，去es中检索商品
		SearchResult result = mallSearchService.search(param);
		model.addAttribute("result",result);

		return "list";
	}

}
