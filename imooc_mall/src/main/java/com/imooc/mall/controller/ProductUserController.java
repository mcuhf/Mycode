package com.imooc.mall.controller;


import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.ProductListReq;
import com.imooc.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户商品Controller
 */
@RestController
public class ProductUserController {

    @Autowired
    ProductService productService;

    @ApiOperation("商品详细信息")
    @GetMapping("/product/detail")
    public ApiRestResponse detail(@RequestParam Integer id){
        Product product = productService.detail(id);
        return ApiRestResponse.success(product);
    }

    @ApiOperation("商品搜索查询,支持模糊查询，排序，ID")
    @GetMapping("/product/list")
    public ApiRestResponse list(ProductListReq productListReq){
        PageInfo pageInfo = productService.list(productListReq);
        return ApiRestResponse.success(pageInfo);
    }
}
