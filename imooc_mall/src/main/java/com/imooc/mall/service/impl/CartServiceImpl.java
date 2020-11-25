package com.imooc.mall.service.impl;

import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.CartMapper;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Cart;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 购物车实现类
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    CartMapper cartMapper;

    @Override
    public List<CartVO> list(Integer userid) {
        List<CartVO> cartVOS = cartMapper.selectList(userid);
        for (CartVO cartVO : cartVOS) {
            cartVO.setTotalPrice(cartVO.getPrice() * cartVO.getQuantity());
        }
        return cartVOS;
    }

    //添加后直接返回List展现最新的购物车状态,加速前端的响应
    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count) {
        //验证存量判断添加是否合法
        validProduct(productId, count);
        //利用userId与productId判断将要被添加的商品是否之前就存在于购物车内
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //新增记录
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setSelected(Constant.CartProductStatus.CHECKED);
            cartMapper.insertSelective(cart);
        } else {
            //商品之前已经加入购物车，此处将商品数量进行增加
            //在查询到的老购物车的基础上进行修改
            Cart cart1 = new Cart();
            cart1.setId(cart.getId());
            cart1.setSelected(Constant.CartProductStatus.CHECKED);
            cart1.setQuantity(cart.getQuantity() + count);
            cart1.setUserId(cart.getUserId());
            cart1.setProductId(cart.getProductId());
            cartMapper.updateByPrimaryKeySelective(cart1);
        }
        return this.list(userId);
    }

    private void validProduct(Integer productId, Integer count) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {
            throw new ImoocMallException(ImoocMallExceptionEnum.PRODUCT_STATE_ERROR);
        }
        if (count > product.getStock()) {
            throw new ImoocMallException(ImoocMallExceptionEnum.PRODUCT_NOT_ENOUGH);
        }
    }

    @Override
    public List<CartVO> updateCart(Integer userId, Integer productId, Integer count) {
        validProduct(productId, count);
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        } else {
            Cart cart1 = new Cart();
            cart1.setId(cart.getId());
            cart1.setSelected(Constant.CartProductStatus.CHECKED);
            cart1.setQuantity(count);
            cart1.setUserId(cart.getUserId());
            cart1.setProductId(cart.getProductId());
            cartMapper.updateByPrimaryKeySelective(cart1);
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> delete(Integer userId, Integer productId) {
        //某用户购物车类的对应商品
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        } else {
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return this.list(userId);
    }


    @Override
    public List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selectedState){
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        } else {
            cartMapper.selectOrNot(userId,productId,selectedState);
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> selectAllOrNot(Integer userId,Integer selectedState){
        cartMapper.selectOrNot(userId,null,selectedState);
        return this.list(userId);
    }


}
