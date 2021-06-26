package com.louisblogs.louismall.cart.controller;

import com.louisblogs.common.constant.AuthServerConstant;
import com.louisblogs.louismall.cart.interceptor.CartInterceptor;
import com.louisblogs.louismall.cart.service.CartService;
import com.louisblogs.louismall.cart.vo.Cart;
import com.louisblogs.louismall.cart.vo.CartItem;
import com.louisblogs.louismall.cart.vo.UserInfoTo;
import feign.codec.StringDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/25 20:50
 */

@Controller
public class CartController {

	@Autowired
	CartService cartService;

	/**
	 * 给远程gulimall-order调用
	 */
	@ResponseBody
	@GetMapping("/currentUserItems")
	public List<CartItem> currentUserItems() {
		List<CartItem> items = cartService.getUserCartItems();
		return items;
	}

	/**
	 * 删除购物车里的某一项
	 */
	@GetMapping("/deleteItem")
	public String deleteItem(@RequestParam("skuId") Long skuId){
		cartService.deleteItem(skuId);
		return "redirect:http://cart.louismall.com/cart.html";
	}

	/**
	 * 修改购物项目的数量
	 */
	@GetMapping("/countItem")
	public String countItem(@RequestParam("skuId") Long skuId,
	                        @RequestParam("num") Integer num){
		cartService.changeItemCount(skuId,num);
		return "redirect:http://cart.louismall.com/cart.html";
	}

	/**
	 * 勾选购物项目
	 */
	@GetMapping("/checkItem")
	public String checkItem(@RequestParam("skuId") Long skuId,
	                        @RequestParam("check") Integer check){
		cartService.checkItem(skuId,check);
		return "redirect:http://cart.louismall.com/cart.html";
	}

	/**
	 * 浏览器有一个cookie；user-key;表示用户身份，一个月后过期；
	 * 如果第一次使用jd的购物车功能，都会给一个临时的用户身份
	 * 浏览器以后保存，每次访问都会带上这个cookie
	 * <p>
	 * 登录：session有
	 * 没登录：按照cookie里面带来user-key来做
	 * 第一次：如果没有临时用户，帮忙创建一个临时用户
	 *
	 * @return
	 */
	@GetMapping("/cart.html")
	public String cartListPage(Model model) throws ExecutionException, InterruptedException {
		//1、快速得到用户信息，id，user-key
//		UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
//		System.out.println(userInfoTo);
		Cart cart = cartService.getCart();
		model.addAttribute("cart",cart);
		return "cartList";
	}

	/**
	 * 添加商品到购物车
	 *
	 * RedirectAttributes ra
	 * ra. addFlashAttribute();将数据放在session里面可以在页面取出，但是只能取一次
	 * ra. addAttribute("skuId", skuId);将数据放在url后面
	 *
	 * @return
	 */
	@GetMapping("/addToCart")
	public String addToCart(@RequestParam("skuId") Long skuId,
	                        @RequestParam("num") Integer num,
	                        RedirectAttributes ra) throws ExecutionException, InterruptedException {
		cartService.addToCart(skuId,num);
//		model.addAttribute("skuId",skuId);
		ra.addAttribute("skuId",skuId);
		return "redirect:http://cart.louismall.com/addToCartSuccess.html";
	}

	/**
	 * 添加到成功页
	 * @param skuId
	 * @param model
	 * @return
	 */
	@GetMapping("/addToCartSuccess.html")
	public String addToCartSuccessPage(@RequestParam("skuId") Long skuId, Model model){
		//重定向到成功页面。再次查询购物车数据即可
		CartItem item = cartService.getCartItem(skuId);
		model.addAttribute("item",item);
		return "success";
	}

}











