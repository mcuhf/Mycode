package com.imooc.mall.service;

import com.imooc.mall.model.vo.CartVO;

import java.util.List;

public interface CartService {
    List<CartVO> list(Integer userid);

    //添加后直接返回List展现最新的购物车状态,加速前端的响应
    List<CartVO> add(Integer userId, Integer productId, Integer count);

    //更新购物车某个商品的数量
    List<CartVO> updateCart(Integer userId, Integer productId, Integer count);

    List<CartVO> delete(Integer userId, Integer productId);

    List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selectedState);

    List<CartVO> selectAllOrNot(Integer userId, Integer selectedState);
}
