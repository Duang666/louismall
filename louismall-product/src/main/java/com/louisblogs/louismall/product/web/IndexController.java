package com.louisblogs.louismall.product.web;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.louisblogs.louismall.product.entity.CategoryEntity;
import com.louisblogs.louismall.product.service.CategoryService;
import com.louisblogs.louismall.product.vo.front.Catelog2Vo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/13 13:27
 */

@Controller
public class IndexController {

	@Autowired
	CategoryService categoryService;

	//渲染首页数据
	@GetMapping({"/", "/index.html"})
	public String indexPage(Model model) {
		// TODO 1、查出所有的1级分类
		List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();

		// 视图解析器进行拼串
		// classpath:/templates/ + 返回值 + .html
		model.addAttribute("categorys", categoryEntities);
		return "index";
	}

	// index/catalog.json
	@ResponseBody
	@GetMapping("/index/catalog.json")
	public Map<String, List<Catelog2Vo>> getCatalogJson() {
		Map<String, List<Catelog2Vo>> catalogJson = categoryService.getCatalogJson();
		return catalogJson;
	}
}
