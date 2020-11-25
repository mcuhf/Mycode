package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.filter.UserFilter;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 购物车模块Controller
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @PostMapping("/add")
    @ApiOperation("添加商品到购物车")
    public ApiRestResponse add(@RequestParam Integer productId,
                               @RequestParam Integer count){
        List<CartVO> cartVOS = cartService.add(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success(cartVOS);
    }

    /**
     * userId 信息不通过用户传入，而是通过session拿到
     * UserFilter 拿到用户id 防止横向越权
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("购物车列表")
    public ApiRestResponse list(){
        List<CartVO> list = cartService.list(UserFilter.currentUser.getId());
        return ApiRestResponse.success(list);
    }


    @PostMapping("/update")
    @ApiOperation("更新购物车某个商品的数量")
    public ApiRestResponse update(@RequestParam Integer productId, @RequestParam Integer count){
        List<CartVO> cartVOS = cartService.updateCart(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success(cartVOS);
    }

    @PostMapping("/delete")
    @ApiOperation("删除购物车的某个商品")
    public ApiRestResponse delete(@RequestParam Integer productId){
        //隐藏userId CartId
        //利用userId productId 定位数据库表中的记录
        List<CartVO> cartVOList = cartService.delete(UserFilter.currentUser.getId(), productId);
        //删除后返回用户的购物车
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/select")
    @ApiOperation("选中/不选中某件商品")
    public ApiRestResponse select(@RequestParam Integer productId,
                                  @RequestParam Integer selectedState){
        List<CartVO> cartVOS = cartService.selectOrNot(UserFilter.currentUser.getId(), productId, selectedState);
        return ApiRestResponse.success(cartVOS);
    }

    @PostMapping("/selectAll")
    @ApiOperation("选中/不选中所有商品")
    public ApiRestResponse selectAll(@RequestParam Integer selectedState){
        List<CartVO> cartVOS = cartService.selectAllOrNot(UserFilter.currentUser.getId(),selectedState);
        return ApiRestResponse.success(cartVOS);
    }
}
