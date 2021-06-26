package com.louisblogs.louismall.cart.service;

import com.louisblogs.louismall.cart.vo.Cart;
import com.louisblogs.louismall.cart.vo.CartItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/25 20:39
 */

public interface CartService {

	/**
	 * 将商品添加到购物车
	 * @param skuId
	 * @param num
	 * @return
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

	/**
	 * 获取购物车中某个购物项
	 * @param skuId
	 * @return
	 */
	CartItem getCartItem(Long skuId);

	/**
	 * 获取整个购物车
	 * @return
	 */
	Cart getCart() throws ExecutionException, InterruptedException;

	/**
	 * 清空购物车数据
	 * @param cartKey
	 */
	void clearCart(String cartKey);

	/**
	 * 勾选购物项
	 * @param skuId
	 * @param check
	 */
	void checkItem(Long skuId, Integer check);

	/**
	 * 修改购物项目的数量
	 */
	void changeItemCount(Long skuId, Integer num);


	/**
	 * 删除购物车里的某一项
	 */
	void deleteItem(Long skuId);


	/**
	 * 给远程gulimall-order调用
	 */
	List<CartItem> getUserCartItems();
}
