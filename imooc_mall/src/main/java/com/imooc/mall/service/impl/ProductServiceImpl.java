package com.imooc.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.query.ProductListQuery;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.model.request.ProductListReq;
import com.imooc.mall.model.vo.CategoryVO;
import com.imooc.mall.service.CategoryService;
import com.imooc.mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryService categoryService;

    @Override
    public void add(AddProductReq addProductReq){
        Product product = new Product();
        BeanUtils.copyProperties(addProductReq,product);
        Product productOld = productMapper.selectByName(addProductReq.getName());
        //如果商品重名不允许
        if (productOld!=null){
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.insertSelective(product);
        if (count==0){
            throw new ImoocMallException(ImoocMallExceptionEnum.PRODUCT_ADD_FAILED);
        }
    }


    @Override
    public void update(Product updateProduct){

        Product productOld = productMapper.selectByName(updateProduct.getName());
        //同名且不同的ID 不能继续修改
        //找到一个之前存在的记录,这次的ID和这个记录不一样，这样做下去，会将另外一条ID记录对应的数据
        //修改成同名商品
        if (productOld!=null && !productOld.getId().equals(updateProduct.getId())){
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.updateByPrimaryKeySelective(updateProduct);
        if (count==0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void delete(Integer id){
        Product productOld = productMapper.selectByPrimaryKey(id);
        if (productOld==null){
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
        int count = productMapper.deleteByPrimaryKey(id);
        if (count==0){
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
    }


    @Override
    public void batchUpdateSellStatus(Integer[] ids,Integer sellStatus){
        productMapper.batchUpdateSellStatus(ids,sellStatus);
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Product> products = productMapper.selectListForAdmin();
        PageInfo pageInfo = new PageInfo(products);
        return pageInfo;
    }

    @Override
    public Product detail(Integer id){
        Product product = productMapper.selectByPrimaryKey(id);
        if (product==null){
            throw new ImoocMallException(ImoocMallExceptionEnum.PRODUCT_NOT_EXISTED);
        }
        return product;
    }

    @Override
    public PageInfo list(ProductListReq productListReq){
        //构建Query对象
        ProductListQuery productListQuery = new ProductListQuery();
        //搜索处理
        if (!StringUtils.isEmpty(productListReq.getKeyword())){
            String keyword = new StringBuilder().append("%").append(productListReq.getKeyword()).append("%").toString();
            productListQuery.setKeyword(keyword);
        }
        //目录处理 子目录
        if (productListReq.getCategoryId()!=null){
            List<CategoryVO> categoryVOS = categoryService.listCategoryForCustomer(productListReq.getCategoryId());
            ArrayList<Integer> categoryIds=new ArrayList<>();
            categoryIds.add(productListReq.getCategoryId());
            getCategoryIds(categoryVOS,categoryIds);
            //找出自身ID 以及自身的所有子ID
            productListQuery.setCategoryIds(categoryIds);
        }


        //前端排序字符串
        String orderBy = productListReq.getOrderBy();

        if (Constant.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
            //PageHelper 实现分页和排序
            PageHelper.startPage(productListReq.getPageNum(),
                    productListReq.getPageSize(),orderBy);
        }else {
            PageHelper.startPage(productListReq.getPageNum(),
                    productListReq.getPageSize());
        }

        List<Product> products = productMapper.selectList(productListQuery);
        PageInfo pageInfo = new PageInfo(products);
        return pageInfo;
    }

    private void getCategoryIds(List<CategoryVO> categoryVOS, ArrayList<Integer> categoryIds){
        for (int i=0;i<categoryVOS.size();i++){
            CategoryVO categoryVO = categoryVOS.get(i);
            if(categoryVO!=null){
                 categoryIds.add(categoryVO.getId());
                 getCategoryIds(categoryVO.getChildCategory(),categoryIds);
            }
        }

    }
}
