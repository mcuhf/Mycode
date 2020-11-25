package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.model.vo.CategoryVO;

import java.util.List;

/**
 * 目录模块的接口
 */
public interface CategoryService {

    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategory);

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);


    //    @Cacheable(value = "listCategoryForCustomer")
    List<CategoryVO> listCategoryForCustomer(Integer parentId);
}
